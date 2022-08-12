package com.example.chatapi.Entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

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

}
