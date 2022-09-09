package com.example.chatapi.Controller.Chat;

import com.example.chatapi.STOMP.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
public class ChattingController {

	@MessageMapping("/message")
	@SendTo("/topic/chat")
	public Message sendMessage(Principal principal, String message) throws InterruptedException {
		Thread.sleep(1000);
		return new Message(principal.getName(), message);
	}

	@MessageMapping("/ENTJ")
	@SendTo("/topic/ENTJ")
	public Message entj(Principal principal, String message) throws InterruptedException {
		Thread.sleep(1000);
		return new Message(principal.getName(), message);
	}


}
