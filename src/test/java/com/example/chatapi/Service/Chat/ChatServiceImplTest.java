package com.example.chatapi.Service.Chat;

import com.example.chatapi.DTO.ChatRoomDTO;
import com.example.chatapi.DTO.MBTICode;
import com.example.chatapi.Repository.ChatMBTIRepository;
import com.example.chatapi.Repository.ChatRoomRepository;
import com.example.chatapi.Repository.MbtiRepository;
import com.example.chatapi.Service.MBTI.MbtiServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Collections;

@Slf4j
@SpringBootTest
@ActiveProfiles("local")
class ChatServiceImplTest {

    @InjectMocks
    @Autowired
    private ChatServiceImpl chatService;

    @Mock
    @Autowired
    private ChatRoomRepository chatRoomRepository;

    @Mock
    @Autowired
    private ChatMBTIRepository chatMBTIRepository;


    @InjectMocks
    @Autowired
    private MbtiServiceImpl mbtiService;

    @Mock
    @Autowired
    private MbtiRepository mbtiRepository;

    @Test
    void getChatRoomList() {
        chatService.getListOfAllChatRooms().forEach(chatRoomDTO -> {
            log.info(chatRoomDTO.toString());
        });
    }

    @Test
    void getListOfAllChatRooms() {
    }

    @Test
    void getListAllChatRoomsByFounder() {
        chatService.getListAllChatRoomsByFounder("admin").forEach(chatRoomDTO -> {
            log.info(chatRoomDTO.toString());
        });
    }

    @Test
    void getListOfAllChatRoomsUserBelongs() {
        chatService.getListOfAllChatRoomsUserBelongs("admin").forEach(chatRoomDTO -> {
            log.info(chatRoomDTO.toString());
        });
    }

    @Test
    void createChatRoom() {
        try {
            ChatRoomDTO newRoom = new ChatRoomDTO("JUNIT TEST ROOM", "Junit", MBTICode.matchCode("IS.."));
            log.info(String.valueOf(chatService.createChatRoom("admin", newRoom)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void regularExpression() {
        mbtiRepository.findAll().forEach(mbtiInfoEntity -> {
            log.info(mbtiInfoEntity.getCode());
            log.info(String.valueOf(mbtiInfoEntity.getCode().matches("....")));
        });
    }


}