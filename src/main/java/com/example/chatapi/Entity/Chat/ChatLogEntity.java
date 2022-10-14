package com.example.chatapi.Entity.Chat;

import com.example.chatapi.Entity.BaseTimeEntity;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "CHAT_LOG")
@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ChatLogEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "chat_room_id")
    private ChatRoomEntity chatRoomId;

    @Column(name = "from_username", length = 50)
    private String fromUsername;

    @Column(name = "message", length = 1000)
    private String message;

    @Column(name = "type", length = 10)
    private String type;

    @Column(name = "reg_date")
    @CreationTimestamp
    private LocalDateTime regDate;

    @Column(name = "update_date")
    @UpdateTimestamp
    private LocalDateTime updateDate;

    public void setFromUsername(String from) {
        this.fromUsername = from;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setChatRoomId(ChatRoomEntity chatRoomId) {
		this.chatRoomId = chatRoomId;
	}
}
