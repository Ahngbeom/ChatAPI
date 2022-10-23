package com.example.chatapi.Controller.API.Chat;

import com.example.chatapi.STOMP.Message;
import com.example.chatapi.Service.Chat.ChatLogService;
import com.example.chatapi.Service.Chat.ChatService;
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

    private final ChatLogService chatLogService;

    @MessageMapping("/mbti-chat/join-room/{roomId}")
    @SendTo("/topic/mbti-chat/{roomId}")
    public Message joinChatRoom(@DestinationVariable("roomName") Long roomId, Principal principal) throws InterruptedException {
        if (!chatService.checkAlreadyJoined(roomId, principal.getName())) {
            if (chatService.joinChatRoom(roomId, principal.getName())) {
//                Thread.sleep(1000);
                return new Message(Message.COMMON, Message.SERVER, "Welcome, " + principal.getName());
            }
        }
        return null;
    }

    @MessageMapping("/mbti-chat/send-message/{roomName}")
    @SendTo("/topic/mbti-chat/{roomName}")
    public Message sendMessage(@DestinationVariable("roomName") String roomName, Principal principal, String message) throws InterruptedException {
//        Thread.sleep(1000);
        Message msg = new Message(Message.COMMON, principal.getName(), message);
        chatLogService.saveMessage(roomName, msg);
        return msg;
    }

    @MessageMapping("/mbti-chat/leave-room/{roomName}")
    @SendTo("/topic/mbti-chat/{roomName}")
    public Message leaveChatRoom(@DestinationVariable("roomName") String roomName, Principal principal) throws InterruptedException {
//        Thread.sleep(1000);
        return new Message(Message.COMMON, Message.SERVER, "[" + principal.getName() + "] left.");
    }

}
