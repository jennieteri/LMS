package kr.or.ddit.yguniv.projectchatmessage;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import com.fasterxml.jackson.databind.ObjectMapper;

//@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
	
	@Autowired
	ObjectMapper mapper;
	
	@Override
	public void configureMessageBroker(MessageBrokerRegistry config) {
		// 클라이언트에서 구독할 수 있는 경로 설정
        config.enableSimpleBroker("/topic");
        
        // 클라이언트에서 보낸 메세지를 받을 곳
        config.setApplicationDestinationPrefixes("/app");
	}	
	
	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		// WebSocket 엔드포인트 설정
		registry.addEndpoint("/ws-chat").setAllowedOriginPatterns("*").withSockJS();
		
	}
		@Override
		public boolean configureMessageConverters(List<MessageConverter> messageConverters) {
			MappingJackson2MessageConverter res = new MappingJackson2MessageConverter();
			res.setObjectMapper(mapper);
			messageConverters.add(res);
			
			return true;
		}
}
