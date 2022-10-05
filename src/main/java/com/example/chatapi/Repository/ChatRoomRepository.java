package com.example.chatapi.Repository;

import com.example.chatapi.Entity.Chat.ChatRoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoomEntity, Long> {

    Optional<ChatRoomEntity> findByRoomName(String roomName);

    List<ChatRoomEntity> findAllByFounder_Username(String username);

}
