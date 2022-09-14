package com.example.chatapi.Service;

import com.example.chatapi.DTO.ChatRoomDTO;
import com.example.chatapi.Entity.Chat.ChatMBTIJoinEntity;
import com.example.chatapi.Entity.Chat.ChatRoomEntity;
import com.example.chatapi.Repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final UserRepository userRepository;

    private final MbtiRepository mbtiRepository;
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

                ChatRoomEntity savedChatRoomEntity = chatRoomRepository.save(
                        ChatRoomEntity.builder()
                                .roomName(chatRoomDTO.getRoomName())
                                .description(chatRoomDTO.getDescription())
                                .founder(userRepository.findByUsername(username).orElse(null))
                                .build()
                );

//                Set<ChatMBTIJoinEntity> chatMBTIJoinEntities = new HashSet<>();

                mbtiRepository.findAll().forEach(mbtiInfoEntity -> {
                    log.info(String.valueOf(mbtiInfoEntity.getCode().matches(chatRoomDTO.getPermitMBTICode().getCode())));
                    if (mbtiInfoEntity.getCode().matches(chatRoomDTO.getPermitMBTICode().getCode())) {
                        chatMBTIRepository.save(
                                ChatMBTIJoinEntity.builder()
                                        .chatRoomName(savedChatRoomEntity)
                                        .mbtiCode(mbtiInfoEntity)
                                        .build()
                        );
                    }
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
