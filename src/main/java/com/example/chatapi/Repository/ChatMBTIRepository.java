package com.example.chatapi.Repository;

import com.example.chatapi.Entity.Chat.ChatMBTIJoinEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMBTIRepository extends JpaRepository<ChatMBTIJoinEntity, Long> {

    boolean existsByChatRoom(String roomName);

    List<ChatMBTIJoinEntity> findAllByChatRoom_Founder(String username);
    List<ChatMBTIJoinEntity> findAllByChatRoom_RoomName(String roomName);

    List<ChatMBTIJoinEntity> findAllByPermitMBTI_Code(String mbtiCode);
}
