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
        MBTIInfoEntity entity = MBTIInfoEntity.convertToMbtiEntity(mbtiDTO);
        return mbtiRepository.existsById(entity.getCode()) ? entity : mbtiRepository.save(entity);
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

        if (userMbtiRepository.existsByMbti_CodeAndUser_id(mbtiInfoEntity.getCode(), userDTO.getId())) {
            UserMbtiJoinEntity userMbtiJoinEntity = userMbtiRepository.findByMbti_CodeAndUser_id(mbtiInfoEntity.getCode(), userDTO.getId());
            userMbtiJoinEntity.increaseNumberOfTimes();
            return userMbtiRepository.save(userMbtiJoinEntity).getClass().equals(UserMbtiJoinEntity.class);
        } else {
            return userMbtiRepository.save(UserMbtiJoinEntity.builder()
                    .id(userDTO.getId())
                    .user(UserEntity.convertToUserEntity(userDTO))
                    .mbti(mbtiInfoEntity)
                    .numberOfTimes(1)
                    .build()).getClass().equals(UserMbtiJoinEntity.class);
        }


    }

    @Override
    public List<MbtiDTO> getAllMbtiList() throws RuntimeException {
        List<MbtiDTO> mbtiDTOList = new ArrayList<>();
        mbtiRepository.findAll().forEach(mbtiInfoEntity -> {
            mbtiDTOList.add(MbtiDTO.convertMbtiEntityToMbtiDTO(mbtiInfoEntity));
        });
        return mbtiDTOList;
    }

    @Override
    public List<MbtiDTO> getUserMbtiList(Long userNo) throws RuntimeException {
//        UserEntity userEntity = userRepository.findOneWithAuthoritiesByUsername(username).orElseThrow(() -> new RuntimeException("Not found UserEntity"));
        List<MbtiDTO> mbtiList = new ArrayList<>();
        userMbtiRepository.findAllByUser_id(userNo).forEach(userMbtiJoinEntity -> {
//            MbtiDTO mbtiDTO = MbtiDTO.convertMbtiEntityToMbtiDTO(userMbtiJoinEntity.getMbti());
//            mbtiDTO.setNumberOfTimes(userMbtiJoinEntity.getNumberOfTimes());
            MbtiDTO mbtiDTO = MbtiDTO.convertUserMbtiEntityToMbtiDTO(userMbtiJoinEntity);
            mbtiList.add(mbtiDTO);
        });
        return mbtiList;
    }
}
