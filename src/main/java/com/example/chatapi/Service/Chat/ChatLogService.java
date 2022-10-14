package com.example.chatapi.Service.Chat;

import com.example.chatapi.DTO.ChatLogDTO;
import com.example.chatapi.STOMP.Message;

import java.util.List;

public interface ChatLogService {

    void saveMessage(String ChatRoomName, Message message);

    List<Message> getChatRoomLog(String roomName);
}
