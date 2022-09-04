package com.example.chatapi.Service;

import com.example.chatapi.DTO.MbtiDTO;
import com.example.chatapi.DTO.UserDTO;
import com.example.chatapi.Entity.MBTI.MBTIInfoEntity;
import com.example.chatapi.Entity.User.UserEntity;
import com.example.chatapi.Entity.MBTI.UserMbtiJoinEntity;
import com.example.chatapi.Repository.MbtiRepository;
import com.example.chatapi.Repository.UserMbtiRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MbtiServiceImpl implements MbtiService {

    private final UserMbtiRepository userMbtiRepository;

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

    @Override
    public List<MBTIInfoEntity> getList() {
        return mbtiRepository.findAll();
    }

    @Override
    public boolean addMbti(UserDTO userDTO, MbtiDTO mbtiDTO) throws RuntimeException {
//		UserEntity userEntity = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("Not found UserEntity"));

//        userEntity.setMbtiList(Collections.singleton(mbtiInfoEntity));

        MBTIInfoEntity mbtiInfoEntity = this.register(mbtiDTO);

        return userMbtiRepository.save(UserMbtiJoinEntity.builder()
                .id(userDTO.getId())
                .user(UserEntity.convertToUserEntity(userDTO))
                .mbti(mbtiInfoEntity)
                .build()).getClass().equals(UserMbtiJoinEntity.class);

    }

    @Override
    public List<MbtiDTO> getAllMbtiList() throws RuntimeException {
//        mbtiRepository.findAll().forEach(mbtiInfoEntity -> {
//            mbtiInfoEntity
//        });
        return null;
    }

    @Override
    public List<MbtiDTO> getUserMbtiList(Long userNo) throws RuntimeException {
//        UserEntity userEntity = userRepository.findOneWithAuthoritiesByUsername(username).orElseThrow(() -> new RuntimeException("Not found UserEntity"));
        List<MbtiDTO> mbtiList = new ArrayList<>();
        userMbtiRepository.findDistinctByUser_Id(userNo).forEach(userMbtiJoinEntity -> {
            mbtiList.add(MbtiDTO.convertToMbtiDTO(userMbtiJoinEntity.getMbti()));
        });
        return mbtiList;
    }
}
