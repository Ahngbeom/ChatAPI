package com.example.chatapi.Repository;

import com.example.chatapi.Entity.Chat.ChatRoomEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ChatRoomRepositoryTest {

    private static final Logger log = LoggerFactory.getLogger(UserRepositoryTest.class);

    @Autowired
    private ChatRoomRepository chatRoomRepository;

    @Autowired
    private ChatMBTIRepository chatMBTIRepository;

    @Autowired
    private MbtiRepository mbtiRepository;


    @Test
    public void findAll() {
        createChatRoom("TEST ROOM", "INFP-A / INFP-T");
        chatRoomRepository.findAll().forEach(entity -> {
            log.info(entity.toString());
            log.info(entity.toString());
        });
    }

    @Test
    void findByRoomName() {
        chatRoomRepository.findAllByFounder_Username("admin").map(chatRoomEntities -> {
            chatRoomEntities.forEach(chatRoomEntity -> {
                log.info(chatRoomEntity.getRoomName());
            });
            return chatRoomEntities;
        }).orElseThrow(RuntimeException::new);
    }

    @Test
    void findAllByFounder() {
    }

    @Test
    void getListOfAllChatRoomsUserHasJoined() {
    }

    public void createChatRoom(String roomName, String permitMBTICode) throws RuntimeException {
        chatRoomRepository.save(
                ChatRoomEntity.builder()
                        .roomName(roomName)
                        .build()
        );
    }


}