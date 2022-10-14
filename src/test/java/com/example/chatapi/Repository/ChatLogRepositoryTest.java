package com.example.chatapi.Repository;

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
class ChatLogRepositoryTest {

    private static final Logger log = LoggerFactory.getLogger(UserRepositoryTest.class);

    @Autowired
    private ChatLogRepository chatLogRepository;

    @Test
    void findAllByChatRoomId() {

        chatLogRepository.findAllByChatRoomId_RoomNameOrderByRegDate("TEST ROOM").forEach(chatLogEntity -> log.info(chatLogEntity.getFromUsername() + ": " + chatLogEntity.getMessage()));

    }
}