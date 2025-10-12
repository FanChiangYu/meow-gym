package web.chat.controller;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;
import javax.websocket.CloseReason;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.server.ServerEndpoint;

import web.user.pojo.User;

import javax.websocket.Session;
import web.chat.config.HttpSessionConfigurator; //add this manually

@ServerEndpoint(value = "/chat", configurator = HttpSessionConfigurator.class)
//login connect to here
public class ChatEndpoint {
	// 加上從登入servlet取回來的session
	// Set避免重複,Collections.synchronizedSet(...) 讓它在多執行緒下安全（因為多個人會同時連線）
	// 在線中的 WebSocket 連線 取名SESSION_SET為常數做存放
	// synchronizedSet為Collections工具類別的一方法，裡面包Set型態，匿名內部類別來呈現，new完即丟
	private static final Set<Session> SESSION_SET = Collections.synchronizedSet(new HashSet<>());
	private EndpointConfig config;

	@OnOpen
	public void onOpen(Session session, EndpointConfig config) {
		System.out.println("open session" + session);
		System.out.println("Client connected: " + session.getId());
		this.config = config;
		SESSION_SET.add(session); // A會員登入，add。B會員登入，add
		System.out.println("SESSION_SET" + SESSION_SET);
		System.out.println(getUsername() + "已連線");

	}

	@OnMessage
	public void onMessage(String message, Session session) throws IOException {
		System.out.println("onMessage session" + session);
		System.out.println("Received message: " + message);
		// session.getBasicRemote().sendText("Echo: " + message);
		//session.getAsyncRemote().sendText("Echo: " + message);

		for (Session session1 : SESSION_SET) {
			if (session1.isOpen()) {
				session1.getAsyncRemote().sendText(getUsername()+":" + message);
			} else {
				SESSION_SET.remove(session1); //自動清理無效連線(次要)
			}
		}

	}

	// 額外定義一個方法
	private String getUsername() {
		// 從握手設定裡拿出 HttpSession
		Map<String, Object> userMap = config.getUserProperties();
		HttpSession httpSession = (HttpSession) userMap.get("httpSession");
		User user = (User) httpSession.getAttribute("user");
		return user.getName(); // 取出購物袋的方法
	}

	@OnClose
	public void onClose(Session session, CloseReason closeReason) {
		SESSION_SET.remove(session); //主要清理無效連線的地方
		System.out.println("onClose session" + session);
		System.out.println("Client disconnected: " + session.getId());
	}
	
	@OnError
	public void onError(Session session, Throwable throwable) {
		System.out.println(throwable.getMessage() + throwable);
	}
	

}
