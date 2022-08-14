package com.example.chatapi.Entity;

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

	@Column(name = "room_name", length = 4)
	private String roomName;

	@Column(name = "from_username", length = 50)
	private String fromUsername;

	@Column(name = "message")
	private String message;

	public void setFromUsername(String from) {
		this.fromUsername = from;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
