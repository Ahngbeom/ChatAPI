package com.example.chatapi.Entity.MBTI;

import com.example.chatapi.DTO.MbtiDTO;
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
	@Column(name = "mbti", length = 20)
	private String mbti;

	@Column(name = "personality", length = 50)
	private String personality;

	@Column(name = "introduction", length = 1000)
	private String introduction;

//	@Column(name = "number_of_times")
//	@GeneratedValue(strategy = )
//	private int numberOfTimes; // Number of times this MBTI Result

	@ToString.Exclude
	@OneToMany(mappedBy = "mbti")
//	@JoinColumn(name = "USER_ID")
	private Set<UserMbtiJoinEntity> userMbti;

	public static MBTIInfoEntity convertToMbtiEntity(MbtiDTO dto) {
		return MBTIInfoEntity.builder()
				.mbti(dto.getMbti())
				.personality(dto.getPersonality())
				.introduction(dto.getIntroduction())
//				.numberOfTimes(dto.getNumberOfTimes())
				.build();
	}

}
