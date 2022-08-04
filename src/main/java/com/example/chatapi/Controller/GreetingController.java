package com.example.chatapi.Controller;

import com.example.chatapi.STOMP.Greeting;
import com.example.chatapi.STOMP.HelloMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
public class GreetingController {

    @MessageMapping("/hello") // 클라이언트에서 보내는 메시지를 매핑. 클라이언트가 /app/hello URL로 매시지를 담아 요청할 경우 greeting 메소드로 매핑
    @SendTo("/topic/greetings") //
    public Greeting greeting(HelloMessage message) throws Exception {
        Thread.sleep(1000);
        return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getName()));
    }
}
