package com.example.chatapi.DTO;

import com.example.chatapi.Entity.MBTI.MBTIInfoEntity;
import com.example.chatapi.Entity.MBTI.UserMbtiJoinEntity;
import lombok.*;

import java.util.Calendar;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MbtiDTO {

	private String code;

	private String personality;

	private String introduction;

	private String imgSrc;

	private int numberOfTimes; // Number of times this MBTI Result

	private Calendar regDate;

	public static MbtiDTO convertMbtiEntityToMbtiDTO(MBTIInfoEntity entity) {
		return MbtiDTO.builder()
				.code(entity.getCode())
				.personality(entity.getPersonality())
				.introduction(entity.getIntroduction())
				.build();
	}

	public static MbtiDTO convertUserMbtiEntityToMbtiDTO(UserMbtiJoinEntity entity) {
		return MbtiDTO.builder()
				.code(entity.getMbti().getCode())
				.personality(entity.getMbti().getPersonality())
				.introduction(entity.getMbti().getIntroduction())
				.imgSrc(entity.getMbti().getImgSrc())
				.numberOfTimes(entity.getNumberOfTimes())
				.regDate(entity.getRegDate())
				.build();
	}

}
