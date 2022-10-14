package com.example.chatapi.Service;

import com.example.chatapi.DTO.ChatRoomDTO;
import com.example.chatapi.Repository.ChatMBTIRepository;
import com.example.chatapi.Repository.ChatRoomRepository;
import com.example.chatapi.Repository.MbtiRepository;
import com.example.chatapi.Service.Chat.ChatServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

@Slf4j
@SpringBootTest
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
    void getListOfAllChatRoomsUserHasJoined() {
        chatService.getListOfAllChatRoomsUserHasJoined("admin").forEach(chatRoomDTO -> {
            log.info(chatRoomDTO.toString());
        });
    }

    @Test
//    @Transactional
    void createChatRoom() {
        try {
            ChatRoomDTO newRoom = ChatRoomDTO.builder()
                    .roomName("TEST ROOM")
                    .description("THIS ROOM IS FOR TESTING")
                    .permitMBTICode(Arrays.asList(
                            "ENFJ",
                            "ENFP",
                            "ENTJ",
                            "ENTP",
                            "ESFJ",
                            "ESFP",
                            "ESTJ",
                            "ESTP",
                            "INFJ",
                            "INFP",
                            "INTJ",
                            "INTP",
                            "ISFJ",
                            "ISFP",
                            "ISTJ",
                            "ISTP"
                    ))
                    .build();
            log.info(String.valueOf(chatService.createChatRoom("manager", newRoom)));
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