package com.example.chatapi.DTO;

import com.example.chatapi.Entity.Chat.ChatLogEntity;
import com.example.chatapi.Entity.MBTI.MBTIInfoEntity;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoomDTO {

    private String roomName;

    private LocalDateTime createDate;

    @ToString.Exclude
    private List<MbtiDTO> permitMBTICode;

    @ToString.Exclude
    private List<ChatLogDTO> log;

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public void setPermitMBTICode(List<MbtiDTO> permitMBTICode) {
        this.permitMBTICode = permitMBTICode;
    }

    public void setLog(List<ChatLogDTO> log) {
        this.log = log;
    }
}
