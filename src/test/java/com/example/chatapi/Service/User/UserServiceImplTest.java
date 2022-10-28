package com.example.chatapi.Service.User;

import com.example.chatapi.DTO.AuthorityDTO;
import com.example.chatapi.DTO.UserDTO;
import com.example.chatapi.Repository.User.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

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
            UserDTO user = UserDTO.builder()
                    .username("admin")
                    .password(passwordEncoder.encode("admin"))
                    .nickname("ADMIN")
                    .authorities(Collections.singleton("ROLE_ADMIN"))
                    .build();
            userService.signUp(user);
            log.info("Created USER: " + userService.getUserInfo("admin"));
            getUserInfo(user.getUsername());
//            createdUserEntity.getAuthorities().forEach(userAuthorityJoinEntity -> log.info(userAuthorityJoinEntity.getAuthority().getAuthorityName()));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void getUserInfo(String username) {
        log.info(userService.getUserInfo(username).toString());
        userService.getUserInfo(username).getAuthorities().forEach(auth ->
                log.info(auth));
    }

    @Test
    void getUserList() {
        userService.getUserList().forEach(userDTO -> {
            log.info(userDTO.toString());
            getUserAuthorities(userDTO.getUsername());
        });
    }

    @Test
    void getUserAuthorities(String username) {
        userService.getUserAuthorities(username).forEach(auth -> {
            log.info(auth.getAuthorityName());
        });
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