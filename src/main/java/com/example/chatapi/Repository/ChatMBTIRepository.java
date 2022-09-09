package com.example.chatapi.Repository;

import com.example.chatapi.Entity.Chat.ChatMBTIJoinEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMBTIRepository extends JpaRepository<ChatMBTIJoinEntity, Long> {

    boolean existsByChatRooms_RoomName(String roomName);

    List<ChatMBTIJoinEntity> findAllByChatRooms_RoomName(String roomName);

    List<ChatMBTIJoinEntity> findAllByMbtiInfoEntities_Code(String mbtiCode);
}
