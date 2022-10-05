package com.example.chatapi.Controller.API.Chat;

import com.example.chatapi.STOMP.Message;
import com.example.chatapi.Service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ChattingController {

    private final ChatService chatService;

    @MessageMapping("/mbti-chat/join-room/{roomName}")
    @SendTo("/topic/mbti-chat/{roomName}")
    public Message joinUser(@DestinationVariable("roomName") String roomName, Principal principal) throws InterruptedException {
        if (!chatService.checkAlreadyJoined(roomName, principal.getName())) {
            if (chatService.joinChatRoomAvailability(roomName, principal.getName())) {
                Thread.sleep(1000);
                return new Message(Message.COMMON, Message.SERVER, "Welcome, " + principal.getName());
            } else {
                return sendMessageOnlyOneUser(
                        new Message(Message.ERROR, Message.SERVER, "Your MBTI code does not allow access to this chat room.")
                );
            }
        }
        return null;
    }

    @MessageMapping("/mbti-chat/send-message/{roomName}")
    @SendTo("/topic/mbti-chat/{roomName}")
    public Message sendMessage(@DestinationVariable("roomName") String roomName, Principal principal, String message) throws InterruptedException {
        log.info(roomName);
        Thread.sleep(1000);
        return new Message(Message.COMMON, principal.getName(), message);
    }

    @MessageExceptionHandler
    @SendToUser("/unicast")
    public Message sendMessageOnlyOneUser(Message message) {
        return message;
    }

}
