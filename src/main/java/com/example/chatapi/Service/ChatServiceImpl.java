package com.example.chatapi.Service;

import com.example.chatapi.DTO.ChatRoomDTO;
import com.example.chatapi.Entity.Chat.ChatMBTIJoinEntity;
import com.example.chatapi.Entity.Chat.ChatRoomEntity;
import com.example.chatapi.Entity.Chat.ChatUserEntity;
import com.example.chatapi.Repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final UserRepository userRepository;

    private final MbtiRepository mbtiRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatMBTIRepository chatMBTIRepository;

    private final ChatUserRepository chatUserRepository;

    @Override
    public List<ChatRoomDTO> getListOfAllChatRooms() {
        List<ChatRoomDTO> chatRoomDTOList = new ArrayList<>();

        chatRoomRepository.findAll().forEach(entity -> {
            List<ChatMBTIJoinEntity> chatMBTIJoinEntities = chatMBTIRepository.findAllByChatRoom_RoomName(entity.getRoomName());
            List<String> permitMBTICodes = new ArrayList<>();

            chatMBTIJoinEntities.forEach(chatMBTIJoinEntity -> {
                permitMBTICodes.add(chatMBTIJoinEntity.getPermitMBTI().getCode());
            });

            chatRoomDTOList.add(
                    ChatRoomDTO.builder()
                            .roomName(entity.getRoomName())
                            .founder(entity.getFounder().getUsername())
                            .concurrentUsers(countConcurrentUsers(entity.getRoomName()))
                            .description(entity.getDescription())
                            .createDate(entity.getCreateDate().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG)))
                            .permitMBTICode(permitMBTICodes)
                            .build()
            );
        });

        return chatRoomDTOList;
    }

    @Override
    public List<ChatRoomDTO> getListOfAllChatRoomsUserHasJoined(String username) throws RuntimeException {

        return chatRoomRepository.findAllByFounder_Username(username).map(chatRoomEntities -> {

            List<ChatRoomDTO> chatRoomDTOList = new ArrayList<>();

            chatRoomEntities.forEach(chatRoomEntity -> {

//                log.warn(String.valueOf(chatRoomEntity));
                List<ChatMBTIJoinEntity> chatMBTIJoinEntities = new ArrayList<>(chatMBTIRepository.findAllByChatRoom_RoomName(chatRoomEntity.getRoomName()));

                List<String> permitCodeList = new ArrayList<>();

                chatMBTIJoinEntities.forEach(chatMBTIJoinEntity -> {
                    permitCodeList.add(chatMBTIJoinEntity.getPermitMBTI().getCode());
                });

                chatRoomDTOList.add(
                        ChatRoomDTO.builder()
                                .roomName(chatRoomEntity.getRoomName())
                                .description(chatRoomEntity.getDescription())
                                .founder(chatRoomEntity.getFounder().getUsername())
                                .createDate(chatRoomEntity.getCreateDate().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG)))
                                .permitMBTICode(permitCodeList)
                                .build()
                );
            });
            return chatRoomDTOList;
        }).orElseThrow(RuntimeException::new);
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

                chatUserRepository.save(
                        ChatUserEntity.builder()
                                .chatRoom(savedChatRoomEntity)
                                .userName(savedChatRoomEntity.getFounder())
                                .build()
                );

                chatRoomDTO.getPermitMBTICode().forEach(code -> {
                    chatMBTIRepository.save(
                            ChatMBTIJoinEntity.builder()
                                    .chatRoom(savedChatRoomEntity)
                                    .permitMBTI(mbtiRepository.findById(code).orElseThrow(RuntimeException::new))
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

    @Override
    public boolean checkAlreadyJoined(String chatRoomName, String username) {
        return chatUserRepository.existsByChatRoom_RoomNameAndUserName_Username(chatRoomName, username);
    }

    public long countConcurrentUsers(String roomName) {
        List<ChatUserEntity> chatUserEntityList = chatUserRepository.findAllByChatRoom_RoomName(roomName).orElse(null);
        if (chatUserEntityList == null)
            return 0L;
        else
            return chatUserEntityList.size();
    }
}
