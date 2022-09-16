package com.example.chatapi.Entity.MBTI;

import com.example.chatapi.DTO.MbtiDTO;
import com.example.chatapi.Entity.Chat.ChatMBTIJoinEntity;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "MBTI_INFO")
@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MBTIInfoEntity {

	@Id
	@Column(name = "code", length = 20)
	private String code;

	@Column(name = "personality", length = 50)
	private String personality;

	@Column(name = "introduction", length = 1000)
	private String introduction;

	@Column(name = "img_src")
	private String imgSrc;

	@ToString.Exclude
	@OneToMany(mappedBy = "mbti")
//	@JoinColumn(name = "USER_ID")
	private Set<UserMbtiJoinEntity> userMbti;

//	@ToString.Exclude
//	@OneToMany
//	@JoinColumn(name = "room_name")
//	private Set<ChatRoomEntity> ChatRooms;

	@ToString.Exclude
	@OneToMany(mappedBy = "permitMBTI")
	private Set<ChatMBTIJoinEntity> ChatMBTI;

	public static MBTIInfoEntity convertToMbtiEntity(MbtiDTO dto) {
		return MBTIInfoEntity.builder()
				.code(dto.getCode())
				.personality(dto.getPersonality())
				.introduction(dto.getIntroduction())
				.imgSrc(dto.getImgSrc())
//				.numberOfTimes(dto.getNumberOfTimes())
				.build();
	}

}
