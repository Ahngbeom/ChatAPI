package com.example.chatapi.Repository.Chat;

import com.example.chatapi.Entity.Chat.ChatUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatUserRepository extends JpaRepository<ChatUserEntity, Long> {

    List<ChatUserEntity> findAllByChatRoom_Id(Long roomId);

    List<ChatUserEntity> findAllByUserName_Username(String userName);

    Optional<ChatUserEntity> findByChatRoom_RoomNameAndUserName_Username(String roomName, String userName);

    Optional<ChatUserEntity> findByChatRoom_IdAndUserName_Username(Long roomId, String userName);

    boolean existsByChatRoom_IdAndUserName_Username(Long roomId, String userName_username);

}
