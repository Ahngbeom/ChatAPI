package com.example.chatapi.Service;

import com.example.chatapi.DTO.ChatRoomDTO;
import com.example.chatapi.DTO.MbtiDTO;
import com.example.chatapi.Entity.Chat.ChatMBTIJoinEntity;
import com.example.chatapi.Entity.Chat.ChatRoomEntity;
import com.example.chatapi.Entity.MBTI.MBTIInfoEntity;
import com.example.chatapi.Repository.ChatMBTIRepository;
import com.example.chatapi.Repository.ChatRoomRepository;
import com.example.chatapi.Repository.MbtiRepository;
import com.example.chatapi.Repository.UserMbtiRepository;
import com.example.chatapi.Security.CustomUserDetailService;
import com.example.chatapi.Security.LoginSuccessHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatMBTIRepository chatMBTIRepository;

    @Override
    public List<ChatRoomDTO> getChatRoomList() {
        List<ChatRoomDTO> chatRoomDTOList = new ArrayList<>();

        chatRoomRepository.findAll().forEach(entity -> {
            chatRoomDTOList.add(
                    ChatRoomDTO.builder()
                            .roomName(entity.getRoomName())
                            .createDate(entity.getCreateDate().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG)))
                            .build()
            );
        });
        return chatRoomDTOList;
    }

    @Override
    public boolean createChatRoom(String username, ChatRoomDTO chatRoomDTO) throws RuntimeException {
        try {
            if (!chatRoomRepository.findByRoomName(chatRoomDTO.getRoomName()).isPresent()) {
                ChatRoomEntity.builder()
                        .roomName(chatRoomDTO.getRoomName())
                        .build();

                ChatRoomEntity chatRoomEntity = chatRoomRepository.save(
                        ChatRoomEntity.builder()
                                .roomName(chatRoomDTO.getRoomName())
                                .build()
                );

                chatRoomDTO.getPermitMBTICode().forEach(mbtiDTO -> {
                    chatMBTIRepository.save(
                            ChatMBTIJoinEntity.builder()
                                    .chatRoomName(chatRoomEntity)
                                    .mbtiCode(MBTIInfoEntity.convertToMbtiEntity(mbtiDTO))
                                    .build()
                    );
                });
            } else
                throw new RuntimeException("Exist Chatting Room Name");
        } catch (RuntimeException e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return false;
        }
        return true;
    }
}
