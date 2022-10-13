package com.example.chatapi.Repository;

import com.example.chatapi.Entity.Chat.ChatMBTIEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMBTIRepository extends JpaRepository<ChatMBTIEntity, Long> {

    boolean existsByChatRoom(String roomName);

    List<ChatMBTIEntity> findAllByChatRoom_Founder(String username);

    List<ChatMBTIEntity> findAllByChatRoom_RoomName(String roomName);

    List<ChatMBTIEntity> findAllByPermitMBTI_Code(String mbtiCode);
}
