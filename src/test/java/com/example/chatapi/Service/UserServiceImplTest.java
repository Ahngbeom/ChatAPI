package com.example.chatapi.Service;

import com.example.chatapi.DTO.AuthorityDTO;
import com.example.chatapi.DTO.UserDTO;
import com.example.chatapi.Entity.UserEntity;
import com.example.chatapi.Repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
//@ExtendWith(SpringExtension.class)
//@DataJpaTest
//@TestPropertySource("classpath:application.yml")
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserServiceImplTest {


    @InjectMocks
    @Autowired
    private UserServiceImpl userService;

    @Mock
    @Autowired
    private UserRepository userRepository;
//
    @Mock
    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
    }

    @Test
    @Transactional
    void signUp() {
        try {
            Set<AuthorityDTO> authorities = new HashSet<>();
            authorities.add(AuthorityDTO.builder().authorityName("ROLE_ADMIN").build());

            UserDTO user = UserDTO.builder()
                    .username("admin")
                    .password(passwordEncoder.encode("admin"))
                    .nickname("ADMIN")
                    .authorities(authorities)
                    .build();
            log.info("Create USER: " + (userService.signUp(user) ? "SUCCESS" : "FALSE"));
            getUserInfo(user.getUsername());
//            createdUserEntity.getAuthorities().forEach(userAuthorityJoinEntity -> log.info(userAuthorityJoinEntity.getAuthority().getAuthorityName()));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void getUserInfo(String username) {
        log.info(userService.getUserInfo(username).toString());
        userService.getUserInfo(username).getAuthorities().forEach(authorityDTO -> log.info(authorityDTO.getAuthorityName()));
    }

    @Test
    void getUserList() {
    }

    @Test
    void addMbti() {
    }

    @Test
    void getMbtiList() {
    }

    @Test
    void nicknameValidation() {
    }
}