package com.example.chatapi.DTO;

import com.example.chatapi.Entity.Chat.ChatLogEntity;
import com.example.chatapi.Entity.Chat.ChatMBTIEntity;
import com.example.chatapi.Entity.Chat.ChatRoomEntity;
import com.example.chatapi.Entity.Chat.ChatUserEntity;
import com.example.chatapi.Entity.MBTI.MBTIInfoEntity;
import com.example.chatapi.Entity.User.UserEntity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.*;

@Getter
//@Builder
@ToString
//@AllArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoomDTO {

    @ToString.Exclude
    private String origRoomName; // For Modify | Update
    private String roomName;

    private String description;

    private String founder;

    private long concurrentUsers;

    private String createDate;

    //    @ToString.Exclude
    private List<String> permitMBTICode = new ArrayList<>();

    //    @ToString.Exclude
    private List<ChatLogDTO> log;

    public ChatRoomDTO(String roomName, String description, Set<String> permitMBTICode) {
        this.roomName = roomName;
        this.description = description;
        this.permitMBTICode.addAll(permitMBTICode);
    }

    public ChatRoomDTO(String roomName, String description, String founder, Set<String> permitMBTICode) {
        this.roomName = roomName;
        this.description = description;
        this.founder = founder;
        this.permitMBTICode.addAll(permitMBTICode);
    }

    public ChatRoomDTO(ChatRoomEntity entity) {
        this.roomName = entity.getRoomName();
        this.description = entity.getDescription();
        this.founder = entity.getFounder().getUsername();
        this.setCreateDate(entity.getCreateDate());
    }

    public ChatRoomDTO(ChatUserEntity entity) {
        this.roomName = entity.getChatRoom().getRoomName();
        this.description = entity.getChatRoom().getDescription();
        this.founder = entity.getChatRoom().getFounder().getUsername();
        this.setCreateDate(entity.getChatRoom().getCreateDate());
    }

    public void setOrigRoomName(String origRoomName) {
        this.origRoomName = origRoomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setFounder(String founder) {
        this.founder = founder;
    }

    public void setCreateDate(LocalDateTime localDateTime) {
        this.createDate = localDateTime.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG));
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public void setPermitMBTICode(List<String> permitMBTICode) {
        this.permitMBTICode = permitMBTICode;
    }

    public void addPermitMBTICode(String permitMBTICode) {
        this.permitMBTICode.add(permitMBTICode);
    }

    public void addPermitMBTICode(ChatMBTIEntity chatMBTIEntity) {
        this.permitMBTICode.add(chatMBTIEntity.getPermitMBTI().getCode());
    }

    public void addAllChatMBTIEntityList(List<ChatMBTIEntity> chatMBTIEntities) {
        for (ChatMBTIEntity entity : chatMBTIEntities) {
            this.permitMBTICode.add(entity.getPermitMBTI().getCode());
        }
    }

    public void setConcurrentUsers(long concurrentUsers) {
        this.concurrentUsers = concurrentUsers;
    }

    public void setLog(List<ChatLogDTO> log) {
        this.log = log;
    }

}
