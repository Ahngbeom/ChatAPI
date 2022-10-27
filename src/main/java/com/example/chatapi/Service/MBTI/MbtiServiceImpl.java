package com.example.chatapi.Service.MBTI;

import com.example.chatapi.DTO.MBTICode;
import com.example.chatapi.DTO.MbtiDTO;
import com.example.chatapi.DTO.UserDTO;
import com.example.chatapi.Entity.MBTI.MBTIInfoEntity;
import com.example.chatapi.Entity.User.UserEntity;
import com.example.chatapi.Entity.MBTI.UserMbtiJoinEntity;
import com.example.chatapi.Repository.MbtiRepository;
import com.example.chatapi.Repository.User.UserMbtiRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.*;

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


    @Override
    @Transactional
    @PostConstruct
    public void extractMBTIInfo() {
        String url = "https://www.16personalities.com/ko/%EC%84%B1%EA%B2%A9-%EC%9C%A0%ED%98%95";
        Connection conn = Jsoup.connect(url);
        Document document;
        try {
            document = conn.get();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Elements elements = document.select(".type");
        for (Element element : elements) {

            String code = element.select("h5").html();
            code = code.substring(0, code.indexOf('-'));
            MBTICode.getCodesMap().put(code, code);
            if (mbtiRepository.existsById(code))
                continue;
            String personality = element.select("h4").html();
            String introduction = element.select(".snippet").html();

            conn = Jsoup.connect(element.attr("href"));
            try {
                document = conn.get();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            String imgSrc = document.select("header img").attr("src");
            MbtiDTO mbtiDTO = MbtiDTO.builder()
                    .code(code)
                    .personality(personality)
                    .introduction(introduction)
                    .imgSrc(imgSrc)
                    .build();
            log.info(mbtiDTO.toString());

            mbtiRepository.save(MBTIInfoEntity.convertToMbtiEntity(mbtiDTO));
        }

    }

}
