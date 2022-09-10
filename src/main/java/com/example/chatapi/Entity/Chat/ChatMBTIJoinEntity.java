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
public class ChatMBTIJoinEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "chat_room_name")
    private ChatRoomEntity chatRoomName;

    @ManyToOne
    @JoinColumn(name = "mbti_code")
    private MBTIInfoEntity mbtiCode;

}
