package com.example.chatapi.Service;

import com.example.chatapi.DTO.AuthorityDTO;
import com.example.chatapi.DTO.UserDTO;
import com.example.chatapi.Repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.transaction.Transactional;
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