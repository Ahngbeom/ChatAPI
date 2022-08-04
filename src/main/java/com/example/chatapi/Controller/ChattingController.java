package com.example.chatapi.Controller;

import com.example.chatapi.STOMP.Greeting;
import com.example.chatapi.STOMP.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

import java.security.Principal;

@Controller
public class ChattingController {

	@MessageMapping("/send-message")
	@SendTo("/topic/chat")
	public Message sendMessage(Principal principal, String message) throws InterruptedException {
		Thread.sleep(1000);
		return new Message(principal.getName(), message);
	}
}
