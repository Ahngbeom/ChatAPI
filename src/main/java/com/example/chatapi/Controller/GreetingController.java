package com.example.chatapi.Controller;

import com.example.chatapi.STOMP.Greeting;
import com.example.chatapi.STOMP.HelloMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
public class GreetingController {

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greeting(HelloMessage message) throws Exception {
        Thread.sleep(1000);
        return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getName()));
    }
}
