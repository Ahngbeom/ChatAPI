package com.example.chatapi.Service;

import com.example.chatapi.DTO.ChatRoomDTO;
import com.example.chatapi.DTO.MbtiDTO;
import com.example.chatapi.Entity.Chat.ChatMBTIJoinEntity;
import com.example.chatapi.Entity.Chat.ChatRoomEntity;
import com.example.chatapi.Entity.Chat.ChatUserEntity;
import com.example.chatapi.Repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final UserRepository userRepository;

    private final MbtiRepository mbtiRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatMBTIRepository chatMBTIRepository;

    private final ChatUserRepository chatUserRepository;

    private final MbtiService mbtiService;

    @Override
    public boolean createChatRoom(String username, ChatRoomDTO chatRoomDTO) throws RuntimeException {
        try {
            if (!chatRoomRepository.findByRoomName(chatRoomDTO.getRoomName()).isPresent()) {

                ChatRoomEntity savedChatRoomEntity = chatRoomRepository.save(ChatRoomEntity.builder().roomName(chatRoomDTO.getRoomName()).description(chatRoomDTO.getDescription()).founder(userRepository.findByUsername(username).orElse(null)).build());

                chatUserRepository.save(ChatUserEntity.builder().chatRoom(savedChatRoomEntity).userName(savedChatRoomEntity.getFounder()).build());

                chatRoomDTO.getPermitMBTICode().forEach(code -> {
                    chatMBTIRepository.save(ChatMBTIJoinEntity.builder().chatRoom(savedChatRoomEntity).permitMBTI(mbtiRepository.findById(code).orElseThrow(RuntimeException::new)).build());
                });

            } else throw new RuntimeException("Exist Chatting Room Name");
        } catch (RuntimeException e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public List<ChatRoomDTO> getListOfAllChatRooms() {
        return chatRoomRepository.findAll().stream().map(entity -> ChatRoomDTO.builder().roomName(entity.getRoomName()).founder(entity.getFounder().getUsername()).concurrentUsers(countConcurrentUsers(entity.getRoomName())).description(entity.getDescription()).createDate(entity.getCreateDate().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG))).permitMBTICode(permitMBTICodesByChatRoom(entity.getRoomName())).build()).collect(Collectors.toList());
    }

    @Override
    public List<ChatRoomDTO> getListOfAllChatRoomsUserHasJoined(String username) throws RuntimeException {
        return chatRoomRepository.findAllByFounder_Username(username).stream().map(entity -> ChatRoomDTO.builder().roomName(entity.getRoomName()).description(entity.getDescription()).founder(entity.getFounder().getUsername()).concurrentUsers(countConcurrentUsers(entity.getRoomName())).createDate(entity.getCreateDate().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG))).permitMBTICode(permitMBTICodesByChatRoom(entity.getRoomName())).build()).collect(Collectors.toList());
    }

    @Override
    public List<ChatRoomDTO> getListOfAllChatRoomsUserBelongs(String username) {
        return chatUserRepository.findAllByUserName_Username(username).stream().map(entity -> ChatRoomDTO.builder().roomName(entity.getChatRoom().getRoomName()).description(entity.getChatRoom().getDescription()).founder(entity.getChatRoom().getFounder().getUsername()).concurrentUsers(countConcurrentUsers(entity.getChatRoom().getRoomName())).createDate(entity.getChatRoom().getCreateDate().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG))).permitMBTICode(permitMBTICodesByChatRoom(entity.getChatRoom().getRoomName())).build()).collect(Collectors.toList());
    }

    @Override
    public ChatRoomDTO getInfoChatRoom(String roomName) {
        ChatRoomEntity entity = chatRoomRepository.findByRoomName(roomName).orElseThrow(RuntimeException::new);
        return ChatRoomDTO.builder()
                .roomName(entity.getRoomName())
                .founder(entity.getFounder().getUsername())
                .concurrentUsers(countConcurrentUsers(entity.getRoomName()))
                .description(entity.getDescription())
                .createDate(entity.getCreateDate().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG)))
                .permitMBTICode(permitMBTICodesByChatRoom(entity.getRoomName()))
                .build();
    }

    @Override
    public void updateChatRoom(ChatRoomDTO chatRoomDTO) {
        ChatRoomEntity updatedChatRoomEntity = chatRoomRepository.save(ChatRoomEntity.builder()
                .roomName(chatRoomDTO.getRoomName())
                .description(chatRoomDTO.getDescription())
//              .founder()
                .build());
    }

    @Override
    public void removeChatRoom(String roomName) {
        chatUserRepository.deleteAll(chatUserRepository.findAllByChatRoom_RoomName(roomName));
        chatMBTIRepository.deleteAll(chatMBTIRepository.findAllByChatRoom_RoomName(roomName));
        chatRoomRepository.delete(chatRoomRepository.findByRoomName(roomName).orElseThrow(RuntimeException::new));
    }

    @Override
    public boolean joinChatRoomAvailability(String roomName, String userName) {
        List<String> permitMBTICodeList = permitMBTICodesByChatRoom(roomName);
        for (MbtiDTO mbtiDTO : mbtiService.getUserMbtiList(userName)) {
            if (permitMBTICodeList.contains(mbtiDTO.getCode())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean joinChatRoom(String roomName, String userName) throws RuntimeException {
        if (!joinChatRoomAvailability(roomName, userName))
            return false;
        chatUserRepository.save(
                ChatUserEntity.builder()
                        .chatRoom(chatRoomRepository.findByRoomName(roomName)
                                .orElseThrow(RuntimeException::new))
                        .userName(userRepository.findByUsername(userName)
                                .orElseThrow(RuntimeException::new))
                        .build());
        return true;
    }

    @Override
    public boolean checkAlreadyJoined(String chatRoomName, String username) {
        return chatUserRepository.existsByChatRoom_RoomNameAndUserName_Username(chatRoomName, username);
    }

    @Override
    public boolean leaveChatRoom(String roomName, String userName) {
        ChatRoomEntity chatRoomEntity = chatRoomRepository.findByRoomName(roomName).orElseThrow(RuntimeException::new);
        if (chatRoomEntity.getFounder().getUsername().equals(userName))
            return false;
//        return chatUserRepository.deleteByChatRoom_RoomNameAndUserName_Username(roomName, userName) != 0;
        chatUserRepository.delete(chatUserRepository.findByChatRoom_RoomNameAndUserName_Username(roomName, userName).orElseThrow(RuntimeException::new));
        return true;
    }

    public long countConcurrentUsers(String roomName) {
        return chatUserRepository.findAllByChatRoom_RoomName(roomName).size();
    }

    public List<String> permitMBTICodesByChatRoom(String chatRoomName) {
        return chatMBTIRepository.findAllByChatRoom_RoomName(chatRoomName).stream().map(chatMBTIJoinEntity -> chatMBTIJoinEntity.getPermitMBTI().getCode()).collect(Collectors.toList());
    }
}
