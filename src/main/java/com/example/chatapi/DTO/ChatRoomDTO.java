package com.example.chatapi.DTO;

import com.example.chatapi.Entity.Chat.ChatLogEntity;
import com.example.chatapi.Entity.MBTI.MBTIInfoEntity;
import com.example.chatapi.Entity.User.UserEntity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
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

    private String description;

    private String createDate;

    private UserEntity founder;

    @ToString.Exclude
    private List<MbtiDTO> permitMBTICode;

    @ToString.Exclude
    private List<ChatLogDTO> log;

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

//    public void setCreateDate(LocalDateTime createDate) {
//        this.createDate = createDate;
//    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public void setFounder(UserEntity founder) {
        this.founder = founder;
    }

    public void setPermitMBTICode(List<MbtiDTO> permitMBTICode) {
        this.permitMBTICode = permitMBTICode;
    }

    public void setLog(List<ChatLogDTO> log) {
        this.log = log;
    }
}
