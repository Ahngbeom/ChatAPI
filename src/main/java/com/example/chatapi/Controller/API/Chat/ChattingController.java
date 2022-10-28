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
    public Message joinChatRoom(@DestinationVariable("roomId") Long roomId, Principal principal) throws InterruptedException {
        if (!chatService.checkAlreadyJoined(roomId, principal.getName())) {
            if (chatService.joinChatRoomAvailability(roomId, principal.getName())) {
//                Thread.sleep(1000);
                chatService.joinChatRoom(roomId, principal.getName());
                Message msg = new Message(Message.SERVER, Message.SERVER, "Welcome, " + principal.getName());
                chatLogService.saveMessage(roomId, msg);
                return msg;
            }
        }
        return null;
    }

    @MessageMapping("/mbti-chat/send-message/{roomId}")
    @SendTo("/topic/mbti-chat/{roomId}")
    public Message sendMessage(@DestinationVariable("roomId") Long roomId, Principal principal, String message) throws InterruptedException {
//        Thread.sleep(1000);
        Message msg = new Message(Message.COMMON, principal.getName(), message);
        chatLogService.saveMessage(roomId, msg);
        return msg;
    }

    @MessageMapping("/mbti-chat/leave-room/{roomId}")
    @SendTo("/topic/mbti-chat/{roomId}")
    public Message leaveChatRoom(@DestinationVariable("roomId") Long roomId, Principal principal) throws InterruptedException {
//        Thread.sleep(1000);
        return new Message(Message.SERVER, Message.SERVER, "[" + principal.getName() + "] left.");
    }

}
