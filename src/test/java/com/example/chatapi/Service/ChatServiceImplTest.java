package com.example.chatapi.Service;

import com.example.chatapi.DTO.ChatRoomDTO;
import com.example.chatapi.DTO.MbtiDTO;
import com.example.chatapi.Entity.MBTI.MBTIInfoEntity;
import com.example.chatapi.Repository.ChatMBTIRepository;
import com.example.chatapi.Repository.ChatRoomRepository;
import com.example.chatapi.Repository.MbtiRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

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
        chatService.getChatRoomList().forEach(chatRoomDTO -> {
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
                    .permitMBTICode(MbtiDTO.builder().code("....").build())
                    .build();
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