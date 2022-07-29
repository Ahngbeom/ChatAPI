package com.example.chatapi.Service;

import com.example.chatapi.DTO.UserDTO;
import com.example.chatapi.Entity.AuthorityEntity;
import com.example.chatapi.Entity.UserEntity;
import com.example.chatapi.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public boolean signUp(UserDTO userDTO) {
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

        return userRepository.save(userEntity).getClass().equals(UserEntity.class);
    }
}
