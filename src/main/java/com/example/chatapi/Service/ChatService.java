package com.example.chatapi.Service;

import com.example.chatapi.DTO.ChatRoomDTO;

import java.util.List;

public interface ChatService {

    List<ChatRoomDTO> getListOfAllChatRooms();

    List<ChatRoomDTO> getListOfAllChatRoomsUserHasJoined(String username) throws RuntimeException;

    boolean createChatRoom(String username, ChatRoomDTO chatRoomDTO) throws RuntimeException;
}
