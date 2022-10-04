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

    private String founder;

    private long concurrentUsers;

    private String createDate;

//    @ToString.Exclude
    private List<String> permitMBTICode;

//    @ToString.Exclude
    private List<ChatLogDTO> log;

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setFounder(String founder) {
        this.founder = founder;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public void setPermitMBTICode(List<String> permitMBTICode) {
        this.permitMBTICode = permitMBTICode;
    }

    public void setLog(List<ChatLogDTO> log) {
        this.log = log;
    }
}
