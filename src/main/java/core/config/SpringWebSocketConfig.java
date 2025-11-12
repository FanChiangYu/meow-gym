package core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import web.chat.controller.ChatWebSocketHandler;
import web.chat.interceptor.HttpSessionInterceptor;

@Configuration
@EnableWebSocket
public class SpringWebSocketConfig implements WebSocketConfigurer {

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry
			.addHandler(chatWebSocketHandler(), "/chat")
			.addInterceptors(new HttpSessionInterceptor());
	}
	
	@Bean
	public ChatWebSocketHandler chatWebSocketHandler() {
		return new ChatWebSocketHandler();
	}
}
