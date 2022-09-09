package com.example.chatapi.Entity.Chat;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "CHAT_ROOM")
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoomEntity {

	@Id
	@Column(name = "room_name", unique = true, length = 100)
	private String roomName;

	@Column(name = "create_date")
	@CreationTimestamp
	private LocalDateTime createDate;

//	@ToString.Exclude
//	@OneToMany(mappedBy = "chatRooms", fetch = FetchType.LAZY)
//	private Set<MBTIInfoEntity> permitMBTICode;

	@ToString.Exclude
	@OneToMany(mappedBy = "chatRooms", fetch = FetchType.LAZY)
	private Set<ChatMBTIJoinEntity> permitMBTICodes;
//
//	@ToString.Exclude
//	@OneToMany(mappedBy = "chatRoomId", fetch = FetchType.LAZY)
//	private Set<ChatLogEntity> log;
}
