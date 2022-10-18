package com.example.chatapi.Service.Chat;

import com.example.chatapi.DTO.ChatRoomDTO;

import java.util.List;

public interface ChatService {

    boolean createChatRoom(String username, ChatRoomDTO chatRoomDTO) throws RuntimeException;

    List<ChatRoomDTO> getListOfAllChatRooms();

    List<ChatRoomDTO> getListAllChatRoomsByFounder(String username) throws RuntimeException;

    List<ChatRoomDTO> getListOfAllChatRoomsUserBelongs(String username);

    ChatRoomDTO getInfoChatRoom(String roomName);

    void updateChatRoom(ChatRoomDTO chatRoomDTO);

    void removeChatRoom(String roomName);

    boolean joinChatRoomAvailability(String roomName, String userName);

    boolean joinChatRoom(String roomName, String userName) throws RuntimeException;

    boolean checkAlreadyJoined(String chatRoomName, String username);

    boolean leaveChatRoom(String roomName, String userName);
}
