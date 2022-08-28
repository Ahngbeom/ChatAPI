package com.example.chatapi.Repository;

import com.example.chatapi.Entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class UserRepositoryTest {

    private static final Logger log = LoggerFactory.getLogger(UserRepositoryTest.class);
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    void findOneWithAuthoritiesByUsername() {
        UserEntity entity = userRepository.findOneWithAuthoritiesByUsername("admin").orElseThrow(RuntimeException::new);
        log.info(entity.toString());
    }

    @Test
    void findAllWithAuthoritiesAndMBTIList() {
        userRepository.findAllWithAuthoritiesAndMBTIList();
    }
}