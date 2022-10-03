package com.example.chatapi.STOMP;

//import com.example.chatapi.DTO.UserDTO;
//import lombok.Getter;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.web.server.LocalServerPort;
//import org.springframework.messaging.converter.MappingJackson2MessageConverter;
//import org.springframework.messaging.simp.stomp.StompFrameHandler;
//import org.springframework.messaging.simp.stomp.StompHeaders;
//import org.springframework.messaging.simp.stomp.StompSession;
//import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.util.Assert;
//import org.springframework.web.socket.client.standard.StandardWebSocketClient;
//import org.springframework.web.socket.messaging.WebSocketStompClient;
//import org.springframework.web.socket.sockjs.client.SockJsClient;
//import org.springframework.web.socket.sockjs.client.Transport;
//import org.springframework.web.socket.sockjs.client.WebSocketTransport;
//
//import java.lang.reflect.Type;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//import java.util.concurrent.CompletableFuture;
//import java.util.concurrent.ExecutionException;
//import java.util.concurrent.TimeUnit;
//import java.util.concurrent.TimeoutException;
//
//import static com.sun.xml.internal.ws.api.ComponentFeature.Target.ENDPOINT;

//@ExtendWith(SpringExtension.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//public class StompTest {
//    protected StompSession stompSession;
//
//    @LocalServerPort
//    private int port;
//
//    private final String url;
//
//    private final WebSocketStompClient webSocketStompClient;
//
//    public StompTest() {
//        this.url = "ws://localhost:";
//        this.webSocketStompClient = new WebSocketStompClient(new SockJsClient(createTransport()));
//        this.webSocketStompClient.setMessageConverter(new MappingJackson2MessageConverter());
//    }
//
//    @BeforeEach
//    public void connect() throws ExecutionException, InterruptedException, TimeoutException {
//        this.stompSession = this.webSocketStompClient.connect(url + port + ENDPOINT, new StompSessionHandlerAdapter() {}).get(3, TimeUnit.SECONDS);
//    }
//
//    @AfterEach
//    public void disconnect() {
//        if (this.stompSession.isConnected())
//            this.stompSession.disconnect();
//    }
//
//    private List<Transport> createTransport() {
//        List<Transport> transports = new ArrayList<>(1);
//        transports.add(new WebSocketTransport(new StandardWebSocketClient()));
//        return transports;
//    }
//
//    @Test
//    public void findUsers() throws ExecutionException, InterruptedException, TimeoutException {
//        MessageFrameHandler<UserDTO[]> handler = new MessageFrameHandler<>(UserDTO[].class);
//        this.stompSession.subscribe("/topic/greetings", handler);
//
//        this.stompSession.send("/send/hello", "");
//
//        List<UserDTO> userDTOList = Arrays.asList(handler.getCompletableFuture().get(3, TimeUnit.SECONDS));
//
//    }
//
//    public class MessageFrameHandler<T> implements StompFrameHandler {
//
//        @Getter
//        private final CompletableFuture<T> completableFuture = new CompletableFuture<>();
//
//        private final Class<T> tClass;
//
//        public MessageFrameHandler(Class<T> tClass) {
//            this.tClass = tClass;
//        }
//
//        /**
//         * Invoked before {@link #handleFrame(StompHeaders, Object)} to determine the
//         * type of Object the payload should be converted to.
//         *
//         * @param headers the headers of a message
//         */
//        @Override
//        public Type getPayloadType(StompHeaders headers) {
//            return this.tClass;
//        }
//
//        /**
//         * Handle a STOMP frame with the payload converted to the target type returned
//         * from {@link #getPayloadType(StompHeaders)}.
//         *
//         * @param headers the headers of the frame
//         * @param payload the payload, or {@code null} if there was no payload
//         */
//        @Override
//        public void handleFrame(StompHeaders headers, Object payload) {
//            completableFuture.complete((T) payload);
//        }
//    }
//
//}

