package com.example.chatapi.DTO;

import com.example.chatapi.Entity.Chat.ChatRoomEntity;
import lombok.*;

@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ChatLogDTO {

    private Long id;

    private String fromUsername;

    private String message;

    private ChatRoomEntity chatRoomId;

    public void setId(Long id) {
        this.id = id;
    }

    public void setFromUsername(String fromUsername) {
        this.fromUsername = fromUsername;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setChatRoomId(ChatRoomEntity chatRoomId) {
        this.chatRoomId = chatRoomId;
    }
}
