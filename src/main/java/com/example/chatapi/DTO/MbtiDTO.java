package com.example.chatapi.DTO;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MbtiDTO {

	private String mbti;

	private String personality;

	private String introduction;

	public void setMbti(String mbti) {
		this.mbti = mbti;
	}

	public void setPersonality(String personality) {
		this.personality = personality;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
}
