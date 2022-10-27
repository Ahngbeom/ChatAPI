package com.example.chatapi.Repository.Chat;

import com.example.chatapi.Entity.Chat.ChatRoomEntity;
import com.example.chatapi.Entity.User.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoomEntity, Long> {

    Optional<ChatRoomEntity> findByRoomName(String roomName);

    List<ChatRoomEntity> findAllByFounder_Username(String username);

    boolean existsByRoomName(String roomName);

}
