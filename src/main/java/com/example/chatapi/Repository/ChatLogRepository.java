package com.example.chatapi.Repository;

import com.example.chatapi.Entity.Chat.ChatLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatLogRepository extends JpaRepository<ChatLogEntity, Long> {

    List<ChatLogEntity> findAllByChatRoomId_IdOrderByRegDate(Long roomId);
}
