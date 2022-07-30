package com.example.chatapi.Service;

import com.example.chatapi.DTO.UserDTO;
import com.example.chatapi.Entity.AuthorityEntity;
import com.example.chatapi.Entity.UserEntity;
import com.example.chatapi.Repository.AuthorityRepository;
import com.example.chatapi.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @PostConstruct
    public void setAdmin() throws RuntimeException {
        /*
            Spring Bean LifeCycle CallBack - @PostConstruct
            빈 생명주기 콜백: 스프링 빈이 생성된 후 의존관계 주입이 완료되거나 죽기 직전에 스프링 빈 안에 존재하는 메소드를 호출해주는 기능
            초기화 콜백 함수 setAdmin 함수를 추가하여 H2 데이터베이스에 Admin 계정을 등록한다.
         */
        try {
            if (userRepository.findOneWithAuthoritiesByUsername("admin").isPresent())
                throw new RuntimeException("EXIST ADMIN ACCOUNT");

            AuthorityEntity authority = AuthorityEntity.builder()
                    .authorityName("ROLE_ADMIN")
                    .build();
            if (authorityRepository.save(authority).getClass() != AuthorityEntity.class)
                throw new RuntimeException("ERROR SAVED ADMIN AUTHORITY ON AUTHORITY TABLE");

            authority = AuthorityEntity.builder()
                    .authorityName("ROLE_USER")
                    .build();
            if (authorityRepository.save(authority).getClass() != AuthorityEntity.class)
                throw new RuntimeException("ERROR SAVED USER AUTHORITY SAVE ON AUTHORITY TABLE");

            log.info("SUCCESS SAVE ON AUTHORITY TABLE");

            Set<AuthorityEntity> adminAuthorities = new HashSet<>();
            adminAuthorities.add(AuthorityEntity.builder().authorityName("ROLE_ADMIN").build());
            adminAuthorities.add(AuthorityEntity.builder().authorityName("ROLE_USER").build());

            UserEntity user = UserEntity.builder()
                    .username("admin")
                    .password(passwordEncoder.encode("admin"))
                    .nickname("ADMIN")
                    .authorities(adminAuthorities)
                    .activate(true)
                    .build();

            if (userRepository.save(user).getClass() != UserEntity.class)
                throw new RuntimeException("ERROR SAVE ON USER TABLE");

            log.info("SUCCESS SAVE ON USERS TABLE");
        } catch (RuntimeException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public UserEntity signUp(UserDTO userDTO) throws RuntimeException {
        if (userRepository.findOneWithAuthoritiesByUsername(userDTO.getUsername()).isPresent())
            throw new RuntimeException("Exist User");
        UserEntity userEntity =
                UserEntity.builder()
                        .username(userDTO.getUsername())
                        .password(passwordEncoder.encode(userDTO.getPassword()))
                        .nickname(userDTO.getNickname())
                        .authorities(Collections.singleton(AuthorityEntity.builder().authorityName("ROLE_USER").build()))
                        .activate(true)
                        .build();

        return userRepository.save(userEntity);
    }
}
