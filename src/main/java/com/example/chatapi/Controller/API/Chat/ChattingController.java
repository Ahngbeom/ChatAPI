package com.example.chatapi.Controller.API.Chat;

import com.example.chatapi.STOMP.Greeting;
import com.example.chatapi.STOMP.Message;
import com.example.chatapi.Service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ChattingController {

	private final ChatService chatService;

	@MessageMapping("/mbti-chat/join-room/{roomName}")
	@SendTo("/topic/mbti-chat/{roomName}")
	public Greeting joinUser(@DestinationVariable("roomName") String roomName, Principal principal) throws InterruptedException {
		if (chatService.checkAlreadyJoined(roomName, principal.getName())) {
			return null;
		} else {
			Thread.sleep(1000);
			return new Greeting("Welcome, " + principal.getName());
		}
	}

	@MessageMapping("/mbti-chat/send-message/{roomName}")
	@SendTo("/topic/mbti-chat/{roomName}")
	public Message sendMessage(@DestinationVariable("roomName") String roomName, Principal principal, String message) throws InterruptedException {
		log.info(roomName);
		Thread.sleep(1000);
		return new Message(principal.getName(), message);
	}

}
