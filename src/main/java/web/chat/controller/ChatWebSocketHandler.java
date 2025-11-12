package web.chat.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import web.chat.dao.ChatDao;
import web.chat.pojo.ChatDTO;
import web.chat.pojo.Chats;
import web.chat.pojo.TempIncomingMessage;
import web.chat.service.ChatService;
import web.user.pojo.User;

public class ChatWebSocketHandler extends TextWebSocketHandler {

	private static final Logger logger = LogManager.getLogger(ChatWebSocketHandler.class);
	// Set避免重複,Collections.synchronizedSet(...) 讓它在多執行緒下安全（因為多個人會同時連線）
	// 在線中的 WebSocket 連線 取名SESSION_SET為常數做存放

	// 每個課程一個房間：courseId -> Set<Session>(保證學生不重複)
	private static final Map<Integer, Set<WebSocketSession>> ROOMS = new ConcurrentHashMap<>();

	// 查 session 所在的房間：session -> courseId
	private static final Map<WebSocketSession, Integer> SESSION_ROOM = new ConcurrentHashMap<>();

	@Autowired
	private ChatDao chatDao;

	@Autowired
	private ChatService chatservice;

	private static final Gson GSON = new Gson();

	@Override
	public void afterConnectionEstablished(WebSocketSession wsSession) throws Exception {
		// 1. 取得登入者
		final User user = getLoginUser(wsSession);
		
		//1-1. 呼叫 httpsession 的 courseId
//		HttpSession session = getHttpSession(wsSession); 
//		Integer courseIdNew = (Integer)session.getAttribute("courseId");
//		System.out.println("fan courseId" + courseIdNew); //send to frontend

		if (user == null) {
			wsSession.close();
			return;
		}

		// 2. 從 Query 取 courseId
		final Integer courseId = getIntQueryParam(wsSession, "courseId");
		System.out.println("New Spring websocket courseId" + courseId);
		
//		final Integer courseIdNew =wsSession.get
				
		// 3. 權限驗證：是否擁有該課程 >> 可省略
		System.out.println("user.getUserId()" + user.getUserId());
		System.out.println("user.getUserName()" + user.getName());

		// 4. 放進房間
		Set<WebSocketSession> room = ROOMS.get(courseId);
		if (room == null) {
			room = new HashSet<>();
			ROOMS.put(courseId, room);
		}

		room.add(wsSession);
		SESSION_ROOM.put(wsSession, courseId);
		System.out.println("room" + room.toString());
		System.out.println("SESSION_ROOM" + SESSION_ROOM.toString());

		// 4.推送"歷史訊息",非即時訊息。用這一份為主，不要理getChat.java
		List<ChatDTO> history = chatDao.selectCourseChatsWithUser(courseId);

		System.out.println("history" + history); // 這裡也抓的到訊息了

		if (history != null) {
			List<JsonObject> arr = new ArrayList<JsonObject>();
			for (ChatDTO record : history) {
				JsonObject respbody = new JsonObject();
				// 直接傳給前端，由前端處理歷史訊息顯示不就好了
				respbody.addProperty("type", "history");
				respbody.addProperty("user", record.getUserId().toString());
				respbody.addProperty("text", record.getContent());
				respbody.addProperty("time", record.getCreatedAt().toString());
				respbody.addProperty("courseId", record.getCourseId().toString());
				respbody.addProperty("name", record.getName());
				arr.add(respbody);
			}
			TextMessage message = new TextMessage(GSON.toJson(arr));
			wsSession.sendMessage(message);
		}

	}

	@Override
	protected void handleTextMessage(WebSocketSession wsSession, TextMessage message) throws Exception {
		final Integer courseId = SESSION_ROOM.get(wsSession); // 從房間拿 courseId
		System.out.println("servlet onMessage courseId" + courseId);

		final User user = getLoginUser(wsSession); // 從 HttpSession 拿登入者
		System.out.println("servlet onMessage user" + user.getName());

		// 5.前端送來 JSON：{type:'chat', text:'...'}
		String text = null;
		try {
			TempIncomingMessage incoming = GSON.fromJson(message.getPayload(), TempIncomingMessage.class);
			if (incoming != null) {
				text = incoming.getText();
				System.out.println("incoming text" + text);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 6.寫入 DB >> chats
		// 6-1.先查該課程的coachId
		Integer coachId = null;
		try {
			coachId = chatDao.selectCoachIdByCourse(courseId);
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}

		// 6-2.利用insert方法塞進DB

		// 6. 寫入 DB 並取回完整 entity
		Chats chat1 = new Chats();
		chat1.setCourseId(courseId);
		chat1.setUserId(user.getUserId());
		chat1.setCoachId(coachId);
		chat1.setContent(text);

		// 移到Service
		Chats saved = chatservice.saveAndLoad(chat1);
		System.out.println("saved" + saved);

		// 7. 送訊息後廣播給同房
		JsonObject resp = new JsonObject();
		resp.addProperty("type", "chat");
		resp.addProperty("user", String.valueOf(user.getUserId())); // 與歷史訊息欄位一致
		resp.addProperty("name", user.getName());
//		resp.addProperty("text", text);
		resp.addProperty("text", saved.getContent());
		resp.addProperty("time", saved.getCreatedAt().toString()); // 為何不能用Chats直接抓??
		resp.addProperty("courseId", String.valueOf(courseId));

		List<JsonObject> one = new ArrayList<>();
		one.add(resp);
		broadcastToRoom(courseId, GSON.toJson(one)); // 廣播給同房每個連線

	}

	@Override
	public void afterConnectionClosed(WebSocketSession wsSession, CloseStatus status) throws Exception {
		// SESSION_SET.remove(session); // 主要清理無效連線的地方
		System.out.println("onClose session" + wsSession);
		System.out.println("Client disconnected: " + wsSession.getId());
		SESSION_ROOM.remove(wsSession);
	}

	@Override
	public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
		logger.error(exception.getMessage(), exception);
	}

	private User getLoginUser(WebSocketSession wsSession) {
		HttpSession httpSession = getHttpSession(wsSession);
		return (User) httpSession.getAttribute("user");
	}
	
	private HttpSession getHttpSession(WebSocketSession wsSession) {
		Map<String, Object> userMap = wsSession.getAttributes();
		return (HttpSession) userMap.get("HTTP_SESSION");
	}

	// 讀取 Query String 的 int 參數，例如 ?courseId=11
	// (Session session, String key)帶入什麼參數:Session session是 ws 連線物件, key是courseId
	private Integer getIntQueryParam(WebSocketSession session, String key) {
		String params = session.getUri().getQuery();
		System.out.println("getIntQueryParam params" + params);
		if (params == null) {
			return null;
		}
		return null;

	}

	private void broadcastToRoom(int courseId, String jsonPayload) throws IOException {
		Set<WebSocketSession> room = ROOMS.get(courseId);
		if (room == null)
			return;
		for (WebSocketSession s : room) {
			if (s.isOpen()) {
				TextMessage message = new TextMessage(GSON.toJson(jsonPayload));
				s.sendMessage(message);
			}
		}
	}

}


