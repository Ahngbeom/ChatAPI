package com.example.chatapi.Service;

import com.example.chatapi.DTO.MbtiDTO;
import com.example.chatapi.Entity.MBTIInfoEntity;
import com.example.chatapi.Repository.MbtiRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MbtiServiceImpl implements MbtiService {

	private final MbtiRepository mbtiRepository;

	@Override
	public MBTIInfoEntity register(MbtiDTO mbtiDTO) {
		MBTIInfoEntity entity = MBTIInfoEntity.builder()
				.mbti(mbtiDTO.getMbti())
				.personality(mbtiDTO.getPersonality())
				.introduction(mbtiDTO.getIntroduction())
				.build();
		return mbtiRepository.save(entity);
	}
}
