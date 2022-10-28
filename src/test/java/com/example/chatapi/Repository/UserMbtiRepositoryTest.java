package com.example.chatapi.Repository;

import com.example.chatapi.Entity.MBTI.UserMbtiJoinEntity;
import com.example.chatapi.Repository.User.UserMbtiRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserMbtiRepositoryTest {

    private static final Logger log = LoggerFactory.getLogger(UserRepositoryTest.class);
    @Autowired
    private UserMbtiRepository userMbtiRepository;

    @Test
    void findAllByUser_id() {
        userMbtiRepository.findAllByUser_Username("admin").forEach(entity -> {
//            log.info(entity.getUser().toString());
            log.info(entity.getMbti().toString());
            log.info(String.valueOf(entity.getId()));
            log.info(String.valueOf(entity.getNumberOfTimes()));
        });
    }

    @Test
    void findByMbti_CodeAndUser_id() {
        UserMbtiJoinEntity entity = userMbtiRepository.findByMbti_CodeAndUser_Username("ESTJ-A / ESTJ-T", "admin").orElseThrow(RuntimeException::new);
        log.info(entity.getUser().toString());
        log.info(entity.getMbti().toString());
    }

    @Test
    void existsByMbtiAndUser_id() {
        log.info(String.valueOf(userMbtiRepository.existsById(11L)));
        log.info(String.valueOf(userMbtiRepository.existsByUser_Username("admin")));
        log.info(String.valueOf(userMbtiRepository.existsByMbti_Code("ESTJ-A / ESTJ-T")));
        log.info(String.valueOf(userMbtiRepository.existsByMbti_CodeAndUser_Username("ESTJ-A / ESTJ-T", "admin")));
//        log.info(String.valueOf(userMbtiRepository.existsByCodeAndUser_id("ESTJ-A / ESTJ-T", 31L)));
    }

}