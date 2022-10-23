package com.example.chatapi.STOMP;

import com.example.chatapi.ChatApplicationIntegrationTests;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.Assert;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import javax.transaction.Transactional;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;

//@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@SpringBootTest
@ActiveProfiles("local")
@Transactional // 클래스 내부의 각각의 테스트 메소드가 실행될 때마다, DB 롤백.
public class StompTest {

    static final String WEBSOCKET_URI = "ws://localhost:8080/ws/mbti-chat";
    static final String WEBSOCKET_TOPIC = "/topic/greetings";
    BlockingQueue<String> blockingQueue;
    WebSocketStompClient stompClient;
    List<StompSession> socketSession;
    @LocalServerPort
    private Integer PORT;

    @BeforeEach
    public void setup() {
        blockingQueue = new LinkedBlockingDeque<>();
        stompClient =
                new WebSocketStompClient(
                        new SockJsClient(
                                Collections.singletonList(new WebSocketTransport(new StandardWebSocketClient()))));

        stompClient.setMessageConverter(new MappingJackson2MessageConverter());
        socketSession = new ArrayList<>(500);
    }

    @Test
    public void shouldReceiveAMessageFromTheServer() throws Exception {
        StompSession session = stompClient
                .connect("ws://localhost:" + PORT + "/ws/mbti-chat", new StompSessionHandlerAdapter() {
                })
//                .connect(WEBSOCKET_URI, new StompSessionHandlerAdapter() {})
                .get(1, TimeUnit.SECONDS);
        session.subscribe(WEBSOCKET_TOPIC, new DefaultStompFrameHandler());

        String message = "MESSAGE TEST";
        session.subscribe(WEBSOCKET_TOPIC, new DefaultStompFrameHandler());
        session.send("/hello", "Ahng".getBytes(StandardCharsets.UTF_8));
        socketSession.add(session);
        assert message.equals(blockingQueue.poll(1, TimeUnit.SECONDS));
    }

    class DefaultStompFrameHandler implements StompFrameHandler {
        @Override
        public Type getPayloadType(StompHeaders stompHeaders) {
            return byte[].class;
        }

        @Override
        public void handleFrame(StompHeaders stompHeaders, Object o) {
//            blockingQueue.offer(new String((byte[]) o));
            assert o != null;
            blockingQueue.add(o.toString());
        }
    }

}

