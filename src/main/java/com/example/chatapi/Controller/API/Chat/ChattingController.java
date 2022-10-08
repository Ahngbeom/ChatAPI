package com.example.chatapi.Controller.API.Chat;

import com.example.chatapi.STOMP.Message;
import com.example.chatapi.Service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ChattingController {

    private final ChatService chatService;

    @MessageMapping("/mbti-chat/join-room/{roomName}")
    @SendTo("/topic/mbti-chat/{roomName}")
    public Message joinChatRoom(@DestinationVariable("roomName") String roomName, Principal principal) throws InterruptedException {
        if (!chatService.checkAlreadyJoined(roomName, principal.getName())) {
            if (chatService.joinChatRoom(roomName, principal.getName())) {
                Thread.sleep(1000);
                return new Message(Message.COMMON, Message.SERVER, "Welcome, " + principal.getName());
            }
        }
        return null;
    }

    @MessageMapping("/mbti-chat/send-message/{roomName}")
    @SendTo("/topic/mbti-chat/{roomName}")
    public Message sendMessage(@DestinationVariable("roomName") String roomName, Principal principal, String message) throws InterruptedException {
        Thread.sleep(1000);
        return new Message(Message.COMMON, principal.getName(), message);
    }

    @MessageMapping("/mbti-chat/leave-room/{roomName}")
    @SendTo("/topic/mbti-chat/{roomName}")
    public Message leaveChatRoom(@DestinationVariable("roomName") String roomName, Principal principal) throws InterruptedException {
        Thread.sleep(1000);
        return new Message(Message.COMMON, Message.SERVER, "[" + principal.getName() + "] left.");
    }

}
