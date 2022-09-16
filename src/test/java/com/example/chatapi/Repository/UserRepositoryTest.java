package com.example.chatapi.Repository;

import com.example.chatapi.Entity.User.UserEntity;
import org.junit.jupiter.api.BeforeEach;
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
class UserRepositoryTest {

    private static final Logger log = LoggerFactory.getLogger(UserRepositoryTest.class);
    @Autowired
    private UserRepository userRepository;

    @Test
    void save() {
        userRepository.save(UserEntity.builder()
                        .username("admin")
                        .nickname("ADMIN")
                        .password("admin")
                .build());
    }

    @Test
    void findByUsername() {
//        UserEntity entity = userRepository.findOneWithAuthoritiesByUsername("admin").orElseThrow(RuntimeException::new);
        UserEntity entity = userRepository.findByUsername("admin").orElseThrow(RuntimeException::new);
        log.info(entity.toString());
        entity.getAuthorities().forEach(userAuthorityJoinEntity -> log.info(userAuthorityJoinEntity.getAuthority().getAuthorityName()));
        entity.getMbtiList().forEach(userMbtiJoinEntity -> log.info(userMbtiJoinEntity.getMbti().getCode()));
    }

    @Test
    void findByNickname() {
//        UserEntity entity = userRepository.findOneWithAuthoritiesByUsername("admin").orElseThrow(RuntimeException::new);
        UserEntity entity = userRepository.findByNickname("ADMIN").orElseThrow(RuntimeException::new);
        log.info(entity.toString());
        entity.getAuthorities().forEach(userAuthorityJoinEntity -> log.info(userAuthorityJoinEntity.getAuthority().getAuthorityName()));
        entity.getMbtiList().forEach(userMbtiJoinEntity -> log.info(userMbtiJoinEntity.getMbti().getCode()));
    }

    @Test
    void findAllWithAuthoritiesByUsername() {
        UserEntity entity = userRepository.findAllWithAuthoritiesByUsername("admin").orElseThrow(RuntimeException::new);
        log.info(entity.toString());
//        entity.getAuthorities().forEach(userAuthorityJoinEntity -> log.info(userAuthorityJoinEntity.getAuthority().getAuthorityName()));
//        entity.getMbtiList().forEach(userMbtiJoinEntity -> log.info(userMbtiJoinEntity.getMbti().getCode()));
    }

    @Test
    void findAllWithMbtiListByUsername() {
    }

    @Test
    void findAllWithAuthoritiesAndMbtiListByUsername() {
    }

//    @Test
//    void findAllWithAuthoritiesAndMBTIList() {
//        userRepository.findAllWithAuthoritiesAndMBTIList();
//    }
}