package com.example.chatapi.Entity.Chat;

import com.example.chatapi.Entity.MBTI.MBTIInfoEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "CHAT_MBTI")
@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ChatMBTIEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "chat_room_name")
    private ChatRoomEntity chatRoom;

    @ManyToOne
    @JoinColumn(name = "permit_mbti_code")
    private MBTIInfoEntity permitMBTI;

    public void setChatRoom(ChatRoomEntity chatRoom) {
        this.chatRoom = chatRoom;
    }

    public void setPermitMBTI(MBTIInfoEntity permitMBTI) {
        this.permitMBTI = permitMBTI;
    }
}
