package com.example.chatapi.Repository;

import com.example.chatapi.Entity.Chat.ChatLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatLogRepository extends JpaRepository<ChatLogEntity, Long> {

    List<ChatLogEntity> findAllByChatRoomId_RoomNameOrderByRegDate(String roomName);
}
