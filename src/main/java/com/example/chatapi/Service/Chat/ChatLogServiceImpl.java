package com.example.chatapi.Service.Chat;

import com.example.chatapi.Entity.Chat.ChatLogEntity;
import com.example.chatapi.Repository.ChatLogRepository;
import com.example.chatapi.Repository.ChatRoomRepository;
import com.example.chatapi.STOMP.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatLogServiceImpl implements ChatLogService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatLogRepository chatLogRepository;

    @Override
    public void saveMessage(Long roomId, Message message) {
        chatLogRepository.save(ChatLogEntity.builder()
                .chatRoomId(chatRoomRepository.findById(roomId).orElseThrow(RuntimeException::new))
                .message(message.getMessage())
                .fromUsername(message.getFrom())
                .type(message.getStatus())
                .build());
    }

    @Override
    public List<Message> getChatRoomLog(Long roomId) {
        return chatLogRepository.findAllByChatRoomId_IdOrderByRegDate(roomId).stream()
                .map(chatLogEntity -> Message.builder()
                        .from(chatLogEntity.getFromUsername())
                        .message(chatLogEntity.getMessage())
                        .status(chatLogEntity.getType())
                        .build())
                .collect(Collectors.toList());
    }
}
