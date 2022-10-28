package com.example.chatapi.Service.Chat;

import com.example.chatapi.DTO.ChatLogDTO;
import com.example.chatapi.STOMP.Message;

import java.util.List;

public interface ChatLogService {

    void saveMessage(Long roomId, Message message);

    List<Message> getChatRoomLog(Long roomId);
}
