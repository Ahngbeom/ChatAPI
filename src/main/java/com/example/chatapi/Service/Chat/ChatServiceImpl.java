package com.example.chatapi.Service.Chat;

import com.example.chatapi.DTO.ChatRoomDTO;
import com.example.chatapi.DTO.MbtiDTO;
import com.example.chatapi.Entity.Chat.ChatMBTIEntity;
import com.example.chatapi.Entity.Chat.ChatRoomEntity;
import com.example.chatapi.Entity.Chat.ChatUserEntity;
import com.example.chatapi.Repository.*;
import com.example.chatapi.Service.MBTI.MbtiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
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
    public ChatRoomDTO createChatRoom(String username, ChatRoomDTO chatRoomDTO) throws RuntimeException {
        try {
            if (!chatRoomRepository.findByRoomName(chatRoomDTO.getRoomName()).isPresent()) {

                ChatRoomEntity savedChatRoomEntity = chatRoomRepository.saveAndFlush(
                        ChatRoomEntity.builder()
                                .roomName(chatRoomDTO.getRoomName())
                                .description(chatRoomDTO.getDescription())
                                .founder(userRepository.findByUsername(username).orElseThrow(RuntimeException::new))
                                .build());
                chatRoomDTO = new ChatRoomDTO(savedChatRoomEntity);

                chatUserRepository.save(ChatUserEntity.builder().chatRoom(savedChatRoomEntity).userName(savedChatRoomEntity.getFounder()).build());
                chatRoomDTO.setConcurrentUsers(countConcurrentUsers(savedChatRoomEntity.getId()));

                chatRoomDTO.addAllChatMBTIEntityList(
                        chatMBTIRepository.saveAllAndFlush(
                                chatRoomDTO.getPermitMBTICode().stream()
                                        .map(code -> ChatMBTIEntity.builder()
                                                .chatRoom(savedChatRoomEntity)
                                                .permitMBTI(mbtiRepository.findById(code).orElseThrow(RuntimeException::new))
                                                .build())
                                        .collect(Collectors.toSet())
                        )
                );

                return chatRoomDTO;
            } else
                throw new RuntimeException("Exist Chatting Room Name");
        } catch (RuntimeException e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return null;
        }
    }

    @Override
    public List<ChatRoomDTO> getListOfAllChatRooms() {
        return chatRoomRepository.findAll().stream().map(entity ->
                        new ChatRoomDTO(entity.getRoomName(), entity.getDescription(), entity.getFounder().getUsername(), permitMBTICodesByChatRoom(entity.getId())))
                .collect(Collectors.toList());
    }

    @Override
    public List<ChatRoomDTO> getListAllChatRoomsByFounder(String username) throws RuntimeException {
        return chatRoomRepository.findAllByFounder_Username(username).stream().map(ChatRoomDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<ChatRoomDTO> getListOfAllChatRoomsUserBelongs(String username) {
        return chatUserRepository.findAllByUserName_Username(username).stream().map(ChatRoomDTO::new).collect(Collectors.toList());
    }

    @Override
    public ChatRoomDTO getInfoChatRoom(Long id) {
        return chatRoomRepository.findById(id).map(ChatRoomDTO::new).orElseThrow(RuntimeException::new);
    }

    @Override
    public ChatRoomDTO getInfoChatRoom(String roomName) {
        return chatRoomRepository.findByRoomName(roomName).map(ChatRoomDTO::new).orElseThrow(RuntimeException::new);
    }

    @Override
    public ChatRoomDTO updateChatRoom(String username, ChatRoomDTO chatRoomDTO) throws RuntimeException {

        ChatRoomEntity originChatRoomEntity = chatRoomRepository.findById(chatRoomDTO.getId()).orElseThrow(RuntimeException::new);
        if (!username.equals(originChatRoomEntity.getFounder().getUsername()))
            throw new RuntimeException("Permission Denied. (" + username + ", " + originChatRoomEntity.getFounder() + ")");
        else if (chatRoomRepository.existsByRoomName(chatRoomDTO.getRoomName()))
            throw new RuntimeException("Already room name.");

        originChatRoomEntity.setRoomName(chatRoomDTO.getRoomName());
        originChatRoomEntity.setDescription(chatRoomDTO.getDescription());
        originChatRoomEntity.setFounder(userRepository.findByUsername(chatRoomDTO.getFounder()).orElseThrow(RuntimeException::new));

        ChatRoomEntity updatedChatRoomEntity = chatRoomRepository.save(originChatRoomEntity);

        Set<String> originPermitCodeList = permitMBTICodesByChatRoom(originChatRoomEntity.getId());

        for (String updatePermitCode : chatRoomDTO.getPermitMBTICode()) {
            if (!originPermitCodeList.contains(updatePermitCode)) {
                chatMBTIRepository.save(ChatMBTIEntity.builder()
                        .chatRoom(updatedChatRoomEntity)
                        .permitMBTI(mbtiRepository.getReferenceById(updatePermitCode))
                        .build());
            }
        }

        chatMBTIRepository.findAllByChatRoom_Id(originChatRoomEntity.getId()).forEach(chatMBTIEntity -> {
            if (chatRoomDTO.getPermitMBTICode().contains(chatMBTIEntity.getPermitMBTI().getCode())) {
                chatMBTIEntity.setChatRoom(updatedChatRoomEntity);
                chatMBTIRepository.save(chatMBTIEntity);
            } else {
                chatMBTIRepository.delete(chatMBTIEntity);
            }

        });

        chatUserRepository.findAllByChatRoom_Id(originChatRoomEntity.getId()).forEach(chatUserEntity -> {
            chatUserEntity.setChatRoom(updatedChatRoomEntity);
            chatUserRepository.save(chatUserEntity);
        });
        if (!originChatRoomEntity.getRoomName().equals(chatRoomDTO.getRoomName())) {
            chatRoomRepository.delete(chatRoomRepository.findByRoomName(originChatRoomEntity.getRoomName()).orElseThrow(RuntimeException::new));
        }

        return new ChatRoomDTO(updatedChatRoomEntity);
    }

    @Override
    public void removeChatRoom(Long roomId) {
        chatUserRepository.deleteAll(chatUserRepository.findAllByChatRoom_Id(roomId));
        chatMBTIRepository.deleteAll(chatMBTIRepository.findAllByChatRoom_Id(roomId));
        chatRoomRepository.delete(chatRoomRepository.findById(roomId).orElseThrow(RuntimeException::new));
    }

    @Override
    public boolean joinChatRoomAvailability(Long roomId, String userName) {
        Set<String> permitMBTICodeList = permitMBTICodesByChatRoom(roomId);
        for (MbtiDTO mbtiDTO : mbtiService.getUserMbtiList(userName)) {
            if (permitMBTICodeList.contains(mbtiDTO.getCode())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean joinChatRoom(Long roomId, String userName) throws RuntimeException {
        if (!joinChatRoomAvailability(roomId, userName))
            return false;
        chatUserRepository.save(
                ChatUserEntity.builder()
                        .chatRoom(chatRoomRepository.findById(roomId)
                                .orElseThrow(RuntimeException::new))
                        .userName(userRepository.findByUsername(userName)
                                .orElseThrow(RuntimeException::new))
                        .build());
        return true;
    }

    @Override
    public boolean checkAlreadyJoined(Long roomId, String username) {
        return chatUserRepository.existsByChatRoom_IdAndUserName_Username(roomId, username);
    }

    @Override
    public boolean leaveChatRoom(Long roomId, String userName) {
        ChatRoomEntity chatRoomEntity = chatRoomRepository.findById(roomId).orElseThrow(RuntimeException::new);
        if (chatRoomEntity.getFounder().getUsername().equals(userName))
            return false;
//        return chatUserRepository.deleteByChatRoom_RoomNameAndUserName_Username(roomName, userName) != 0;
        chatUserRepository.delete(chatUserRepository.findByChatRoom_IdAndUserName_Username(roomId, userName).orElseThrow(RuntimeException::new));
        return true;
    }

    public long countConcurrentUsers(Long roomId) {
        return chatUserRepository.findAllByChatRoom_Id(roomId).size();
    }

    public Set<String> permitMBTICodesByChatRoom(Long roomId) {
        return chatMBTIRepository.findAllByChatRoom_Id(roomId).stream().map(chatMBTIEntity -> chatMBTIEntity.getPermitMBTI().getCode()).collect(Collectors.toSet());
    }
}
