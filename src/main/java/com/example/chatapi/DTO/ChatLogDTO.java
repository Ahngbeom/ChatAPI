package com.example.chatapi.DTO;

import com.example.chatapi.Entity.Chat.ChatRoomEntity;
import lombok.*;

@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ChatLogDTO {

    private String fromUsername;

    private String message;

    private String type;

    public void setFromUsername(String fromUsername) {
        this.fromUsername = fromUsername;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
