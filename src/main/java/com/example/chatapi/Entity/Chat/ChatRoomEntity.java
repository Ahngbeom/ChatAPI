package com.example.chatapi.Entity.Chat;

import com.example.chatapi.Entity.User.UserEntity;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "room_name", length = 50)
	private String roomName;

	@ManyToOne
	private UserEntity founder;

	@Column(name = "description", length = 200)
	private String description;

	@ToString.Exclude
	@OneToMany(mappedBy = "chatRoom", fetch = FetchType.LAZY)
	private Set<ChatMBTIEntity> permitMBTICodes;

	@Column(name = "create_date")
	@CreationTimestamp
	private LocalDateTime createDate;

	@Column(name = "update_date")
	@UpdateTimestamp
	private LocalDateTime updateDate;
	//
	@ToString.Exclude
	@OneToMany(mappedBy = "chatRoomId", fetch = FetchType.LAZY)
	private Set<ChatLogEntity> log;
}
