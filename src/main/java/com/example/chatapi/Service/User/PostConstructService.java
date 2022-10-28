package com.example.chatapi.Service.User;

import com.example.chatapi.DTO.AuthorityDTO;
import com.example.chatapi.DTO.UserDTO;
import com.example.chatapi.Repository.User.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.Collections;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostConstructService {

    private final UserService userService;
    private final UserRepository userRepository;

    @Transactional
    @PostConstruct
    public void setAdmin() throws RuntimeException {
        /* Spring Bean LifeCycle CallBack - @PostConstruct
            빈 생명주기 콜백: 스프링 빈이 생성된 후 의존관계 주입이 완료되거나 죽기 직전에 스프링 빈 안에 존재하는 메소드를 호출해주는 기능
            초기화 콜백 함수 setAdmin 함수를 추가하여 H2 데이터베이스에 Admin 계정을 등록한다. */
        try {
            if (userRepository.findByUsername("admin").isPresent())
                throw new RuntimeException("setAdmin(): EXIST [admin] ACCOUNT");

            UserDTO adminDTO = UserDTO.builder()
                    .username("admin")
                    .password("admin")
                    .nickname("ADMIN")
                    .authorities(Collections.singleton("ROLE_ADMIN"))
                    .mbtiList(null)
                    .build();
            userService.signUp(adminDTO);
        } catch (RuntimeException e) {
//            e.printStackTrace();
            log.error(e.getMessage());
        }
    }

    @Transactional
    @PostConstruct
    public void setManger() throws RuntimeException {
        /* Spring Bean LifeCycle CallBack - @PostConstruct
            빈 생명주기 콜백: 스프링 빈이 생성된 후 의존관계 주입이 완료되거나 죽기 직전에 스프링 빈 안에 존재하는 메소드를 호출해주는 기능
            초기화 콜백 함수 setAdmin 함수를 추가하여 H2 데이터베이스에 Admin 계정을 등록한다. */
        try {
            if (userRepository.findByUsername("manager").isPresent())
                throw new RuntimeException("setManger(): EXIST [manager] ACCOUNT");

            UserDTO adminDTO = UserDTO.builder()
                    .username("manager")
                    .password("1234")
                    .nickname("MANAGER")
                    .authorities(Collections.singleton("ROLE_MANAGER"))
                    .mbtiList(null)
                    .build();
            userService.signUp(adminDTO);
        } catch (RuntimeException e) {
//            e.printStackTrace();
            log.error(e.getMessage());
        }
    }
}
