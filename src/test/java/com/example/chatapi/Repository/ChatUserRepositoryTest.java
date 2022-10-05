package com.example.chatapi.Repository;

import com.example.chatapi.Entity.Chat.ChatRoomEntity;
import com.example.chatapi.Entity.User.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ChatUserRepositoryTest {

    private static final Logger log = LoggerFactory.getLogger(UserRepositoryTest.class);

    @Autowired
    private ChatUserRepository chatUserRepository;

    @Autowired
    private ChatRoomRepository chatRoomRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void findAll() {
        chatUserRepository.findAll().forEach(entity -> {
            log.info(entity.toString());
        });
    }

    @Test
    public void joinChatRoom() {
//        UserEntity userEntity = userRepository.findByUsername("admin").orElse(null);
//        ChatRoomEntity chatRoomEntity = chatRoomRepository.findByRoomName("a").orElse(null);

        if (chatUserRepository.existsByChatRoom_RoomNameAndUserName_Username("a", "admin"))
            log.info("Already Joined");
        else
            log.info("Welcome");
    }

    @Test
    void findAllByUserName_Username() {
        chatUserRepository.findAllByUserName_Username("admin").forEach(chatUserEntity -> log.info(String.valueOf(chatUserEntity)));
    }

}