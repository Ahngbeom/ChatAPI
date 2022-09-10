package com.example.chatapi.Repository;

import com.example.chatapi.Entity.Chat.ChatMBTIJoinEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMBTIRepository extends JpaRepository<ChatMBTIJoinEntity, Long> {

    boolean existsByChatRoomName(String roomName);

    List<ChatMBTIJoinEntity> findAllByChatRoomName(String roomName);

    List<ChatMBTIJoinEntity> findAllByMbtiCode(String mbtiCode);
}
