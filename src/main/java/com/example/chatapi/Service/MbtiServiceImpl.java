package com.example.chatapi.Service;

import com.example.chatapi.DTO.MBTICode;
import com.example.chatapi.DTO.MbtiDTO;
import com.example.chatapi.DTO.UserDTO;
import com.example.chatapi.Entity.MBTI.MBTIInfoEntity;
import com.example.chatapi.Entity.User.UserEntity;
import com.example.chatapi.Entity.MBTI.UserMbtiJoinEntity;
import com.example.chatapi.Repository.MbtiRepository;
import com.example.chatapi.Repository.UserMbtiRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
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

        MBTIInfoEntity mbtiInfoEntity = mbtiRepository.findById(mbtiDTO.getCode()).orElse(null);

        assert mbtiInfoEntity != null;
        if (userMbtiRepository.existsByMbti_CodeAndUser_Username(mbtiInfoEntity.getCode(), userDTO.getUsername())) {
            UserMbtiJoinEntity userMbtiJoinEntity = userMbtiRepository.findByMbti_CodeAndUser_Username(mbtiInfoEntity.getCode(), userDTO.getUsername()).orElseThrow(RuntimeException::new);
            userMbtiJoinEntity.increaseNumberOfTimes();
            return userMbtiRepository.save(userMbtiJoinEntity).getClass().equals(UserMbtiJoinEntity.class);
        } else {
            return userMbtiRepository.save(UserMbtiJoinEntity.builder()
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
    public List<MbtiDTO> getUserMbtiList(String username) throws RuntimeException {
//        UserEntity userEntity = userRepository.findOneWithAuthoritiesByUsername(username).orElseThrow(() -> new RuntimeException("Not found UserEntity"));
        List<MbtiDTO> mbtiList = new ArrayList<>();
        userMbtiRepository.findAllByUser_Username(username).forEach(userMbtiJoinEntity -> {
//            MbtiDTO mbtiDTO = MbtiDTO.convertMbtiEntityToMbtiDTO(userMbtiJoinEntity.getMbti());
//            mbtiDTO.setNumberOfTimes(userMbtiJoinEntity.getNumberOfTimes());
            MbtiDTO mbtiDTO = MbtiDTO.convertUserMbtiEntityToMbtiDTO(userMbtiJoinEntity);
            mbtiList.add(mbtiDTO);
        });
        return mbtiList;
    }

    @Override
    public MbtiDTO getRepresentMBTI(String username) {
        return MbtiDTO.convertUserMbtiEntityToMbtiDTO(Objects.requireNonNull(userMbtiRepository.findByUser_UsernameAndRepresentIsTrue(username).orElse(null)));
    }

    @Override
    public void assignRepresentMBTI(String username, String mbtiCode) {
        releaseRepresentMBTI(username);
        UserMbtiJoinEntity entity = userMbtiRepository.findByMbti_CodeAndUser_Username(mbtiCode, username).orElseThrow(RuntimeException::new);
        entity.setRepresent(true);
        userMbtiRepository.save(entity);
    }

    @Override
    public void releaseRepresentMBTI(String username) {
        for (UserMbtiJoinEntity entity : userMbtiRepository.findAllByUser_Username(username)) {
            if (entity.isRepresent()) {
                entity.setRepresent(false);
                userMbtiRepository.save(entity);
                break;
            }
        }
    }

    @Override
    public MbtiDTO getInfo(String code) {
        return MbtiDTO.convertMbtiEntityToMbtiDTO(mbtiRepository.findByCode(code).orElseThrow(RuntimeException::new));
    }

    @Transactional
    @PostConstruct
    public void mbtiInfoInit() {
        String[][] mbtiInfoStrArr = {
                {"ENFJ", "A Protagonist (ENFJ) is a person with the Extraverted, Intuitive, Feeling, and Judging personality traits. These warm, forthright types love helping others, and they tend to have strong ideas and values. They back their perspective with the creative energy to achieve their goals.", "Protagonist", "https://static.neris-assets.com/images/personality-types/headers/diplomats_Protagonist_ENFJ_personality_header.svg"},
                {"ENFP", "A Campaigner (ENFP) is someone with the Extraverted, Intuitive, Feeling, and Prospecting personality traits. These people tend to embrace big ideas and actions that reflect their sense of hope and goodwill toward others. Their vibrant energy can flow in many directions.", "Campaigner", "https://static.neris-assets.com/images/personality-types/headers/diplomats_Campaigner_ENFP_personality_header.svg"},
                {"ENTJ", "A Commander (ENTJ) is someone with the Extraverted, Intuitive, Thinking, and Judging personality traits. They are decisive people who love momentum and accomplishment. They gather information to construct their creative visions but rarely hesitate for long before acting on them.", "Commander", "https://static.neris-assets.com/images/personality-types/headers/analysts_Commander_ENTJ_personality_header.svg"},
                {"ENTP", "A Debater (ENTP) is a person with the Extraverted, Intuitive, Thinking, and Prospecting personality traits. They tend to be bold and creative, deconstructing and rebuilding ideas with great mental agility. They pursue their goals vigorously despite any resistance they might encounter.", "Debater", "https://static.neris-assets.com/images/personality-types/headers/analysts_Debater_ENTP_personality_header.svg"},
                {"ESFJ", "A Consul (ESFJ) is a person with the Extraverted, Observant, Feeling, and Judging personality traits. They are attentive and people-focused, and they enjoy taking part in their social community. Their achievements are guided by decisive values, and they willingly offer guidance to others.", "Consul", "https://static.neris-assets.com/images/personality-types/headers/sentinels_Consul_ESFJ_personality_header.svg"},
                {"ESFP", "An Entertainer (ESFP) is a person with the Extraverted, Observant, Feeling, and Prospecting personality traits. These people love vibrant experiences, engaging in life eagerly and taking pleasure in discovering the unknown. They can be very social, often encouraging others into shared activities.", "Entertainer", "https://static.neris-assets.com/images/personality-types/headers/explorers_Entertainer_ESFP_personality_header.svg"},
                {"ESTJ", "An Executive (ESTJ) is someone with the Extraverted, Observant, Thinking, and Judging personality traits. They possess great fortitude, emphatically following their own sensible judgment. They often serve as a stabilizing force among others, able to offer solid direction amid adversity.", "Executive", "https://static.neris-assets.com/images/personality-types/headers/sentinels_Executive_ESTJ_personality_header.svg"},
                {"ESTP", "An Entrepreneur (ESTP) is someone with the Extraverted, Observant, Thinking, and Prospecting personality traits. They tend to be energetic and action-oriented, deftly navigating whatever is in front of them. They love uncovering life’s opportunities, whether socializing with others or in more personal pursuits.", "Entrepreneur", "https://static.neris-assets.com/images/personality-types/headers/explorers_Entrepreneur_ESTP_personality_header.svg"},
                {"INFJ", "An Advocate (INFJ) is someone with the Introverted, Intuitive, Feeling, and Judging personality traits. They tend to approach life with deep thoughtfulness and imagination. Their inner vision, personal values, and a quiet, principled version of humanism guide them in all things.", "Advocate", "https://static.neris-assets.com/images/personality-types/headers/diplomats_Advocate_INFJ_personality_header.svg"},
                {"INFP", "A Mediator (INFP) is someone who possesses the Introverted, Intuitive, Feeling, and Prospecting personality traits. These rare personality types tend to be quiet, open-minded, and imaginative, and they apply a caring and creative approach to everything they do.", "Mediator", "https://static.neris-assets.com/images/personality-types/headers/diplomats_Mediator_INFP_personality_header.svg"},
                {"INTJ", "An Architect (INTJ) is a person with the Introverted, Intuitive, Thinking, and Judging personality traits. These thoughtful tacticians love perfecting the details of life, applying creativity and rationality to everything they do. Their inner world is often a private, complex one.", "Architect", "https://static.neris-assets.com/images/personality-types/headers/analysts_Architect_INTJ_personality_header.svg"},
                {"INTP", "A Logician (INTP) is someone with the Introverted, Intuitive, Thinking, and Prospecting personality traits. These flexible thinkers enjoy taking an unconventional approach to many aspects of life. They often seek out unlikely paths, mixing willingness to experiment with personal creativity.", "Logician", "https://static.neris-assets.com/images/personality-types/headers/analysts_Logician_INTP_personality_header.svg"},
                {"ISFJ", "A Defender (ISFJ) is someone with the Introverted, Observant, Feeling, and Judging personality traits. These people tend to be warm and unassuming in their own steady way. They’re efficient and responsible, giving careful attention to practical details in their daily lives.", "Defender", "https://static.neris-assets.com/images/personality-types/headers/sentinels_Defender_ISFJ_personality_header.svg"},
                {"ISFP", "An Adventurer (ISFP) is a person with the Introverted, Observant, Feeling, and Prospecting personality traits. They tend to have open minds, approaching life, new experiences, and people with grounded warmth. Their ability to stay in the moment helps them uncover exciting potentials.", "Adventurer", "https://static.neris-assets.com/images/personality-types/headers/explorers_Adventurer_ISFP_personality_header.svg"},
                {"ISTJ", "A Logistician (ISTJ) is someone with the Introverted, Observant, Thinking, and Judging personality traits. These people tend to be reserved yet willful, with a rational outlook on life. They compose their actions carefully and carry them out with methodical purpose.", "Logistician", "https://static.neris-assets.com/images/personality-types/headers/sentinels_Logistician_ISTJ_personality_header.svg"},
                {"ISTP", "A Virtuoso (ISTP) is someone with the Introverted, Observant, Thinking, and Prospecting personality traits. They tend to have an individualistic mindset, pursuing goals without needing much external connection. They engage in life with inquisitiveness and personal skill, varying their approach as needed.", "Virtuoso", "https://static.neris-assets.com/images/personality-types/headers/explorers_Virtuoso_ISTP_personality_header.svg"}
        };

        for (int i = 0; i < 16; i++) {
            mbtiRepository.save(
                    MBTIInfoEntity.builder()
                            .code(mbtiInfoStrArr[i][0])
                            .introduction(mbtiInfoStrArr[i][1])
                            .personality(mbtiInfoStrArr[i][2])
                            .imgSrc(mbtiInfoStrArr[i][3])
                            .build()
            );
        }
    }
}
