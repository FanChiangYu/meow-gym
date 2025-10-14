package web.chat.config;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig; ////add this manually

public class HttpSessionConfigurator extends ServerEndpointConfig.Configurator {
	@Override
	public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest req, HandshakeResponse resp) {
		// EndpointConfig 是 ServerEndpointConfig 的父類別
		// 將session資料傳到ws的servlet
		Map<String, Object> userMap = sec.getUserProperties();
		System.out.println("userMap getUserProperties" + userMap);
		HttpSession httpSession = (HttpSession) req.getHttpSession();
		userMap.put("httpSession", httpSession);
		System.out.println("userMap put httpsession" + userMap);

	}
}
