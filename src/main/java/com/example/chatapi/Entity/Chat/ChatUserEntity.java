package com.example.chatapi.Entity.Chat;

import com.example.chatapi.Entity.User.UserEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "CHAT_USER")
@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ChatUserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "chat_room_name")
    private ChatRoomEntity chatRoom;

    @ManyToOne
    @JoinColumn(name = "user_name")
    private UserEntity userName;

}
