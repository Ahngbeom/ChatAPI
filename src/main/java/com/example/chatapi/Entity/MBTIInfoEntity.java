package com.example.chatapi.Entity;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "MBTI_INFO")
@Getter
@Setter
@Builder
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

	@OneToMany(mappedBy = "mbti")
//	@JoinColumn(name = "USER_ID")
	private Set<UserMbtiJoinEntity> userMbti;

}
