package web.chat.controller;

import java.io.IOException;
import java.util.ArrayList;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.servlet.http.HttpSession;
import javax.websocket.CloseReason;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;

import javax.websocket.server.ServerEndpoint;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import web.user.pojo.User;

import javax.websocket.Session;
import web.chat.config.HttpSessionConfigurator; //add this manually
import web.chat.dao.ChatDao;
import web.chat.pojo.ChatDTO;
import web.chat.pojo.Chats;
import web.chat.service.ChatService;

//@ServerEndpoint(value = "/chat", configurator = HttpSessionConfigurator.class)

@ServerEndpoint(value = "/chat", configurator = HttpSessionConfigurator.class)
public class ChatEndpoint {

	// 加上從登入servlet取回來的session
	// Set避免重複,Collections.synchronizedSet(...) 讓它在多執行緒下安全（因為多個人會同時連線）
	// 在線中的 WebSocket 連線 取名SESSION_SET為常數做存放
	// synchronizedSet為Collections工具類別的一方法，裡面包Set型態，匿名內部類別來呈現，new完即丟
//	private static final Set<Session> SESSION_SET = Collections.synchronizedSet(new HashSet<>());
//	private EndpointConfig config;

	// 每個課程一個房間：courseId -> Set<Session>(保證學生不重複)
	private static final Map<Integer, Set<Session>> ROOMS = new ConcurrentHashMap<>();

	// 查 session 所在的房間：session -> courseId
	private static final Map<Session, Integer> SESSION_ROOM = new ConcurrentHashMap<>();

	// private static final ChatDao chatDao = new ChatDaoImpl();
	// 乾脆跟原來依樣給Tomcat託管，但絕對不可用new
	private ChatDao chatDao;
	private ChatService chatservice;

	private static final Gson GSON = new Gson();

	private EndpointConfig config;

	@OnOpen
	public void onOpen(Session session, EndpointConfig config) throws IOException {

		// add
		// 從websocket 取出 httpsession (已經從modifyHandshake方法內取得，這裡只是借來用)
		HttpSession httpSession = (HttpSession) config.getUserProperties().get("httpSession");

		// Spring(二)講義11-4方法
		ApplicationContext applicationContext = WebApplicationContextUtils
				.getWebApplicationContext(httpSession.getServletContext());
		if (chatDao == null) {
			chatDao = applicationContext.getBean(ChatDao.class);
		}

		if (chatservice == null) {
			chatservice = applicationContext.getBean(ChatService.class);
		}

		// add end

		// 先new GSON，之後會用到
		// Gson gson = new Gson();

		System.out.println("open session" + session);
		System.out.println("Client connected: " + session.getId());
		this.config = config;

		// 1. 取得登入者
		final User user = getLoginUser();

		if (user == null) {
			session.close();
			return;
		}

		// 2. 從 Query 取 courseId
		final Integer courseId = getIntQueryParam(session, "courseId");

		// 3. 權限驗證：是否擁有該課程 >> 可省略
		System.out.println("user.getUserId()" + user.getUserId());
		System.out.println("user.getUserName()" + user.getName());

		// 4. 放進房間
//		ROOMS.add(session);
//		SESSION_ROOM.put(session, courseId);
//		log("OPEN", session, courseId, "user=" + safe(user.getName()));

		Set<Session> room = ROOMS.get(courseId);
		if (room == null) {
			room = new CopyOnWriteArraySet<>(); // CopyOnWriteArraySet ????
			ROOMS.put(courseId, room);
		}
		room.add(session);
		SESSION_ROOM.put(session, courseId);
		System.out.println("room" + room.toString());
		System.out.println("SESSION_ROOM" + SESSION_ROOM.toString());

		// 負責使用ws接收http連線的東西
		// onOpen的session 跟 onMeassage的session是一樣的
//		SESSION_SET.add(session); // A會員登入，add。B會員登入，add
//		System.out.println("onOpenSession" + session);

		// 4.推送"歷史訊息",非即時訊息。用這一份為主，不要理getChat.java
		try {
			// List<Chats> history = chatDao.selectChatsByCourseId(courseId);
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
				session.getAsyncRemote().sendText(GSON.toJson(arr)); // 一次送到前端
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@OnMessage
	public void onMessage(String payload, Session session) throws IOException {
		System.out.println("onMessage session" + session);
		System.out.println("Received payload: " + payload);

		final Integer courseId = SESSION_ROOM.get(session); // 從房間拿 courseId
		System.out.println("servlet onMessage courseId" + courseId);

		final User user = getLoginUser(); // 從 HttpSession 拿登入者
		System.out.println("servlet onMessage user" + user.getName());

		// 5.前端送來 JSON：{type:'chat', text:'...'}
		String text = null;
		try {
			TempIncomingMessage incoming = GSON.fromJson(payload, TempIncomingMessage.class);
			if (incoming != null) {
				text = incoming.text;
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
		// 要加try catch嗎????? >> 不用
//		Chats chat = new Chats();
//		chat.setCourseId(courseId);
//		chat.setUserId(user.getUserId());
//		chat.setCoachId(coachId);
//		chat.setContent(text);
		// 不用加上時間>> 因為在Chats.java的createdAt 欄位，設定"掠過"的Annotation

		// 使用insert方法，加入對話進資料庫
//		int ok = chatDao.insert(chat);
//		if (ok != 1) {
//			return;
//		}

		// 6. 寫入 DB 並取回完整 entity
		// Chats saved = chatDao.saveAndLoad(courseId, user.getUserId(), coachId, text);

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

		// 要把訊息「傳給所有在線中的人」
//		for (Session session1 : SESSION_SET) {
//			if (session1.isOpen()) {
//				session1.getAsyncRemote().sendText(getUsername() + ":" + message);
//				// 加上trigger others frontend
//
//			} else {
//				SESSION_SET.remove(session1); // 自動清理無效連線
//			}
//		}

	}

	@OnClose
	public void onClose(Session session, CloseReason closeReason) {
		// SESSION_SET.remove(session); // 主要清理無效連線的地方
		System.out.println("onClose session" + session);
		System.out.println("Client disconnected: " + session.getId());
		SESSION_ROOM.remove(session);
		System.out.printf("[WS CLOSE] sid=%s reason=%s (%s)%n", session.getId(), closeReason.getReasonPhrase(),
				closeReason.getCloseCode());
	}

	@OnError
	public void onError(Session session, Throwable throwable) {
		System.out.println(throwable.getMessage() + throwable);
	}

	// ===== helpers =====

	// 額外定義一個方法
	private String getUsername() {
		// 從握手設定裡拿出 HttpSession
		Map<String, Object> userMap = config.getUserProperties();
		HttpSession httpSession = (HttpSession) userMap.get("httpSession");
		User user = (User) httpSession.getAttribute("user");
		return user.getName(); // 取出購物袋的getter方法
	}

	// 取得ws特有的User，session.getAttribute("user")在ws中不能使用
	private User getLoginUser() {
		// 1. 取得HttpSession物件， 因為不能用User loginUser =
		// (User)session.getAttribute("user");取得資料，須改ws特有方式取得
		Map<String, Object> userMap = config.getUserProperties();
		HttpSession httpSession = (HttpSession) userMap.get("httpSession");

		return (User) httpSession.getAttribute("user");
	}

	// 讀取 Query String 的 int 參數，例如 ?courseId=11
	// (Session session, String key)帶入什麼參數:Session session是 ws 連線物件, key是courseId
	private Integer getIntQueryParam(Session session, String key) {
		try {
			Map<String, List<String>> params = session.getRequestParameterMap();
			System.out.println("params" + params);
			if (params == null) {
				return null;
			}

			List<String> values = params.get(key);
			System.out.println("key" + key);
			if (values == null || values.isEmpty()) {
				return null;
			}

			String raw = values.get(0);
			System.out.println("raw" + raw);
			if (raw == null) {
				return null;
			}

			return Integer.valueOf(raw.trim());
		} catch (Exception e) {
			return null;
		}
	}

	// 前端傳入的 JSON 訊息暫存空間，跟資料庫無關
	private static class TempIncomingMessage {
		String type;
		String text;
	}

	private void broadcastToRoom(int courseId, String jsonPayload) {
		Set<Session> room = ROOMS.get(courseId);
		if (room == null)
			return;
		for (Session s : room) {
			if (s.isOpen()) {
				s.getAsyncRemote().sendText(jsonPayload);
			}
		}
	}

}
