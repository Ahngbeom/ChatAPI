package com.example.chatapi.Service;

import com.example.chatapi.DTO.ChatRoomDTO;
import com.example.chatapi.DTO.MbtiDTO;
import com.example.chatapi.Entity.Chat.ChatMBTIJoinEntity;
import com.example.chatapi.Entity.Chat.ChatRoomEntity;
import com.example.chatapi.Repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final UserRepository userRepository;

    private final MbtiRepository mbtiRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatMBTIRepository chatMBTIRepository;

    @Override
    public List<ChatRoomDTO> getListOfAllChatRooms() {
        List<ChatRoomDTO> chatRoomDTOList = new ArrayList<>();

        chatRoomRepository.findAll().forEach(entity -> {
            List<ChatMBTIJoinEntity> chatMBTIJoinEntities = new ArrayList<>(entity.getPermitMBTICodes());
            List<MbtiDTO> mbtiDTOList = new ArrayList<>();

            chatMBTIJoinEntities.forEach(chatMBTIJoinEntity -> {
                mbtiDTOList.add(MbtiDTO.convertMbtiEntityToMbtiDTO(chatMBTIJoinEntity.getPermitMBTI()));
            });

            chatRoomDTOList.add(
                    ChatRoomDTO.builder()
                            .roomName(entity.getRoomName())
                            .description(entity.getDescription())
                            .createDate(entity.getCreateDate().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG)))
                            .permitMBTICode(mbtiDTOList)
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
                List<MbtiDTO> mbtiDTOList = new ArrayList<>();
                List<ChatMBTIJoinEntity> chatMBTIJoinEntities = new ArrayList<>(chatRoomEntity.getPermitMBTICodes());

                chatMBTIJoinEntities.forEach(chatMBTIJoinEntity -> {
                    mbtiDTOList.add(MbtiDTO.convertMbtiEntityToMbtiDTO(chatMBTIJoinEntity.getPermitMBTI()));
                });

                chatRoomDTOList.add(
                        ChatRoomDTO.builder()
                                .roomName(chatRoomEntity.getRoomName())
                                .description(chatRoomEntity.getDescription())
                                .createDate(chatRoomEntity.getCreateDate().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG)))
                                .permitMBTICode(mbtiDTOList)
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

//                Set<ChatMBTIJoinEntity> chatMBTIJoinEntities = new HashSet<>();

                chatRoomDTO.getPermitMBTICode().forEach(mbtiDTO -> {
                    chatMBTIRepository.save(
                            ChatMBTIJoinEntity.builder()
                                    .chatRoom(savedChatRoomEntity)
                                    .permitMBTI(mbtiRepository.findById(mbtiDTO.getCode()).orElseThrow(RuntimeException::new))
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
