package com.example.chatapi.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.RequestUpgradeStrategy;
import org.springframework.web.socket.server.standard.TomcatRequestUpgradeStrategy;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

// Reference: https://dev-gorany.tistory.com/224

@Configuration
@EnableWebSocketMessageBroker // WebSocket Server 사용. WebSocket Message Broker 활성화
public class WebSocketMessageBrokenConfig implements WebSocketMessageBrokerConfigurer {


    /*
    특정 클라이언트가 다른 클라이언트에게 메시지를 라우팅할 때 사용하는 브로커 구성
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // /topic 으로 시작하는 주제를 가진 요청 메시지를 핸들러로 라우팅하여 해당 주제에 가입한 모든 클라이언트에게 메시지를 브로드캐스팅한다.
        registry.enableSimpleBroker("/topic");

        // /send 으로 시작하는 URL로 요청되어진 메시지만 메시지 핸들러로 라우팅한다고 정의
//        registry.setApplicationDestinationPrefixes("/send");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        RequestUpgradeStrategy upgradeStrategy = new TomcatRequestUpgradeStrategy();
        registry
                .addEndpoint("/ws/mbti-chat") // 클라이언트에서 WebSocket에 접속하는 EndPoint 등록
//                .setAllowedOriginPatterns("*") // 보안 위험, 취약
//                .setAllowedOriginPatterns("http://localhost:8080"/*, "http://*:8080", "http://*.*.*.*:8080"*/)

//                .setHandshakeHandler(new DefaultHandshakeHandler() {
//                    public boolean beforeHandshake(
//                            ServerHttpRequest request,
//                            ServerHttpResponse response,
//                            WebSocketHandler wsHandler,
//                            Map attributes) throws Exception {
//
//                        if (request instanceof ServletServerHttpRequest) {
//                            ServletServerHttpRequest servletRequest
//                                    = (ServletServerHttpRequest) request;
//                            HttpSession session = servletRequest
//                                    .getServletRequest().getSession();
//                            attributes.put("sessionId", session.getId());
//                        }
//                        return true;
//                    }
//                })
                .withSockJS(); // withSockJS를 통해 소켓을 등록. 만약 브라우저에서 WebSocket을 지원하지 않을 경우 FallBack 옵션 활성화.

        registry.addEndpoint("/ws/mbti-chat")
                .setHandshakeHandler(new DefaultHandshakeHandler(upgradeStrategy))
                .setAllowedOrigins("*");
    }



}
