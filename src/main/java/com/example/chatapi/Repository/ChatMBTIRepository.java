package com.example.chatapi.Repository;

import com.example.chatapi.Entity.Chat.ChatMBTIEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMBTIRepository extends JpaRepository<ChatMBTIEntity, Long> {

    boolean existsByChatRoom(String roomName);

    List<ChatMBTIEntity> findAllByChatRoom_Founder(String username);

    List<ChatMBTIEntity> findAllByChatRoom_RoomName(String roomName);

    List<ChatMBTIEntity> findAllByPermitMBTI_Code(String mbtiCode);
}
