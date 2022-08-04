package com.example.chatapi.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker // WebSocker Server 사용. WebSocket Message Broker 활성화
public class WebSocketMessageBrokenConfig implements WebSocketMessageBrokerConfigurer {


    /*
    특정 클라이언트가 다른 클라이언트에게 메시지를 라우팅할 때 사용하는 브로커 구성
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // /topic 으로 시작하는 주제를 가 메시지를 핸들러로 라우팅하여 해당 주제에 가입한 모든 클라이언트에게 메시지를 브로드캐스팅한다.
        registry.enableSimpleBroker("/topic");

        // /app 으로 시작하는 URL로 요청되어진 메시지만 메시지 핸들러로 라우팅한다고 정의
        registry.setApplicationDestinationPrefixes("/app");
    }

    /*
    클라이언트에서 WebSocket에 접속하는 EndPoint 등록
    withSockJS를 통해 브라우저에서 WebSocket을 지원하지 않을 경우 FallBack 옵션 활성화
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/gs-guide-websocket").withSockJS();
    }


}
