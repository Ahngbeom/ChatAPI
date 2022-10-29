package com.example.chatapi.Service.Chat;

import com.example.chatapi.DTO.ChatRoomDTO;

import java.util.List;

public interface ChatRoomService {
    ChatRoomDTO createChatRoom(String username, ChatRoomDTO chatRoomDTO) throws RuntimeException;

    List<ChatRoomDTO> getListOfAllChatRooms();

    List<ChatRoomDTO> getListAllChatRoomsByFounder(String username) throws RuntimeException;

    List<ChatRoomDTO> getListOfAllChatRoomsUserBelongs(String username);

    ChatRoomDTO getInfoChatRoom(Long id);

    ChatRoomDTO getInfoChatRoom(String roomName);

    ChatRoomDTO updateChatRoom(String username, ChatRoomDTO chatRoomDTO);

    void removeChatRoom(Long roomId);

    boolean joinChatRoomAvailability(Long roomId, String userName);

    void joinChatRoom(Long roomId, String userName) throws RuntimeException;

    boolean checkAlreadyJoined(Long roomId, String username);

    boolean leaveChatRoom(Long roomId, String userName);

    List<String> addPermitMBTICodes(Long roomId, List<String> codes);
}
