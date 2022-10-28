package com.example.chatapi.Service.User;

import com.example.chatapi.DTO.AuthorityDTO;
import com.example.chatapi.DTO.MbtiDTO;
import com.example.chatapi.DTO.OAuth2DTO;
import com.example.chatapi.DTO.UserDTO;
import com.example.chatapi.Entity.Authority.AuthorityEntity;
import com.example.chatapi.Entity.Authority.UserAuthorityJoinEntity;
import com.example.chatapi.Entity.OAuth2UserEntity;
import com.example.chatapi.Entity.User.UserEntity;
import com.example.chatapi.Repository.User.AuthorityRepository;
import com.example.chatapi.Repository.MbtiRepository;
import com.example.chatapi.Repository.User.UserAuthorityRepository;
import com.example.chatapi.Repository.User.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;
    private final UserAuthorityRepository userAuthorityRepository;

    private final MbtiRepository mbtiRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    @Transactional
    public void signUp(UserDTO userDTO) throws RuntimeException {
        if (userRepository.findByUsername(userDTO.getUsername()).isPresent())
            throw new RuntimeException("Exist Username");

        // Storing requested User's Information in user table
        UserEntity userEntity = userRepository.save(
                UserEntity.builder()
                        .username(userDTO.getUsername())
                        .password(passwordEncoder.encode(userDTO.getPassword()))
                        .nickname(userDTO.getNickname())
                        .activate(true)
                        .build()
        );

        addAuthority(userEntity.getUsername(), userDTO.getAuthorities());

//        // Returns whether the result of the above three processes
//        userDTO = UserDTO.convertToUserDTO(userEntity);
//        assert userDTO != null;
//        userDTO.setAuthorities(userAuthorityRepository.findAllByUser_Username(userEntity.getUsername()).stream().map(userAuthorityJoinEntity ->
//                AuthorityDTO.builder().authorityName(userAuthorityJoinEntity.getAuthority().getAuthorityName()).build()).collect(Collectors.toSet())
//        );
//        return userDTO;
    }

    @Override
    public void addAuthority(String username, Collection<String> authorities) {

        // Storing UserEntity and AuthorityEntity object variables in user_authority table
        for (String auth : authorities) {
            userAuthorityRepository.save(UserAuthorityJoinEntity.builder()
                    .user(userRepository.findByUsername(username).orElseThrow(RuntimeException::new))
                    .authority(authorityRepository.getReferenceById(auth))
                    .build());
        }
    }

    @Override
    @Transactional
    public UserDTO getUserInfo(String username) throws UsernameNotFoundException {
        UserEntity entity = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Not Found UserEntity in DataBase"));

        return UserDTO.builder()
                .username(entity.getUsername())
                .password(entity.getPassword())
                .nickname(entity.getNickname())
                .activate(entity.isActivate())
                .regDate(entity.getRegDate())
                .updateDate(entity.getUpdateDate())
                .authorities(entity.getAuthorities().stream().map(userAuthorityJoinEntity -> userAuthorityJoinEntity.getAuthority().getAuthorityName()).collect(Collectors.toSet()))
                .mbtiList(entity.getMbtiList().stream().map(userMbtiJoinEntity -> userMbtiJoinEntity.getMbti().getCode()).collect(Collectors.toSet()))
                .oauth2List(entity.getOauth2Type().stream().map(oauth2UserEntity -> oauth2UserEntity.getOauth2Type().getName()).collect(Collectors.toSet()))
                .build();
    }

    @Override
    public List<UserDTO> getUserList() {
        try {
            List<UserDTO> userDTOList = new ArrayList<>();

            userRepository.findAll().forEach(entity -> {
                log.info(entity.toString());
                userDTOList.add(UserDTO.convertToUserDTO(entity));


//                UserDTO user = UserDTO.convertToUserDTO(entity.getUser());
//                userAuthorityRepository.findAllByUser_Id(entity.getUser().getId()).forEach(userAuthEntity -> {
//                    log.info(userAuthEntity.getUser().toString());
//                    log.info(userAuthEntity.getAuthority().toString());
//                    assert user != null;
//                    user.getAuthorities().add(AuthorityDTO.convertToAuthorityDTO(userAuthEntity.getAuthority()));
//                });
//                assert user != null;
//                log.warn(user.toString());
//                userDTOList.add(user);
            });

            return userDTOList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<AuthorityDTO> getUserAuthorities(String username) {
        return userAuthorityRepository.findAllByUser_Username(username)
                .stream()
                .map(userAuthorityJoinEntity ->
                        AuthorityDTO.AuthorityEntityToAuthorityDTO(userAuthorityJoinEntity.getAuthority()))
                .collect(Collectors.toList());
    }

    @Override
    public Boolean nicknameValidation(String nickname) {
        return !userRepository.findByNickname(nickname).isPresent();
    }

}
