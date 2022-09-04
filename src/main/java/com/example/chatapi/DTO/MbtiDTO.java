package com.example.chatapi.DTO;

import com.example.chatapi.Entity.MBTI.MBTIInfoEntity;
import lombok.*;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MbtiDTO {

	private String mbti;

	private String personality;

	private String introduction;

	private int numberOfTimes; // Number of times this MBTI Result

	public static MbtiDTO convertToMbtiDTO(MBTIInfoEntity entity) {
		return MbtiDTO.builder()
				.mbti(entity.getMbti())
				.personality(entity.getPersonality())
				.introduction(entity.getIntroduction())
				.build();
	}

}
