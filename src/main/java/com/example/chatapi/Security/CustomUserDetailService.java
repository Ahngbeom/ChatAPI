package com.example.chatapi.Security;

import com.example.chatapi.Entity.User.UserEntity;
import com.example.chatapi.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

//@Component("userDetailsService")
@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final Logger log = LoggerFactory.getLogger(CustomUserDetailService.class);

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findAllWithAuthoritiesByUsername(username).map(user -> {
//                    log.info(user.toString());
                    return createUser(username, user);
                })
                .orElseThrow(() -> new UsernameNotFoundException(username + " -> Not Founded in Database"));
    }

    private org.springframework.security.core.userdetails.User createUser(String username, UserEntity user) {
//        log.info("CustomUserDetailService - createUser");

        if (!user.isActivate()) // 인자로 넘어온 User 객체의 활성화 여부 검사
            throw new RuntimeException(username + " -> 활성화되어 있지 않습니다.");

        // User의 권한 정보 리스트 추출
        List<GrantedAuthority> grantedAuthorities = user.getAuthorities().stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getAuthority().getAuthorityName()))
                .collect(Collectors.toList());

        // User의 이름, 비밀번호, 권한 정보 리스트를 담은 userdetails.User 객체를 생성하여 반환
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), grantedAuthorities);
    }
}
