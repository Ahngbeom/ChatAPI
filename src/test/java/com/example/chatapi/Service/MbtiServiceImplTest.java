package com.example.chatapi.Service;

import com.example.chatapi.DTO.MbtiDTO;
import com.example.chatapi.DTO.UserDTO;
import com.example.chatapi.Repository.MbtiRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

@Slf4j
@SpringBootTest
class MbtiServiceImplTest {

    @InjectMocks
    @Autowired
    private UserServiceImpl userService;

    @InjectMocks
    @Autowired
    private MbtiServiceImpl mbtiService;

    @Mock
    @Autowired
    private MbtiRepository mbtiRepository;

    @Test
    void mbtiRegister() {
        MbtiDTO mbti = MbtiDTO.builder()
                .code("ISFJ")
                .personality("용감한 수호자")
                .introduction("용감한 수호자...")
                .build();
        log.info(mbtiService.register(mbti).toString());
    }

    @Test
    @Transactional
    void userAddedMBTI() {
        UserDTO user = userService.getUserInfo("admin");
        MbtiDTO mbti = MbtiDTO.builder()
                .code("ISFJ")
                .personality("용감한 수호자")
                .introduction("용감한 수호자...")
                .build();
        log.info(String.valueOf(mbtiService.addMbti(user, mbti)));
        getUserMbtiList(user.getId());
    }

    @Test
    void getUserMbtiList(Long userNo) {
        mbtiService.getUserMbtiList(userNo).forEach(mbti -> {
            log.info(mbti.toString());
        });
    }
}