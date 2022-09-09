package com.example.chatapi.Entity.Chat;

import com.example.chatapi.Entity.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "CHAT_LOG")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatLogEntity extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "from_username", length = 50)
	private String fromUsername; // is UserEntity's Nickname

	@Column(name = "message", length = 1000)
	private String message;

	@ManyToOne
	@JoinColumn(name = "chat_room_id")
	private ChatRoomEntity chatRoomId;

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
