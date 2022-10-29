package com.example.chatapi.Service.Account;

import com.example.chatapi.DTO.UserDTO;
import com.example.chatapi.Entity.Authority.UserAuthorityJoinEntity;
import com.example.chatapi.Entity.User.UserEntity;
import com.example.chatapi.Repository.User.AuthorityRepository;
import com.example.chatapi.Repository.MbtiRepository;
import com.example.chatapi.Repository.User.UserAuthorityRepository;
import com.example.chatapi.Repository.User.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl extends AccountService implements UserService {

    public UserServiceImpl(UserRepository userRepository, AuthorityRepository authorityRepository, UserAuthorityRepository userAuthorityRepository, MbtiRepository mbtiRepository, PasswordEncoder passwordEncoder) {
        super(userRepository, authorityRepository, userAuthorityRepository, mbtiRepository, passwordEncoder);
    }

    @Override
    @Transactional
    public void signUp(UserDTO userDTO) throws RuntimeException {
        if (userRepository.findByUsername(userDTO.getUsername()).isPresent())
            throw new RuntimeException("Exist Username");

        // Storing requested User's Information in user table
        userRepository.save(
                UserEntity.builder()
                        .username(userDTO.getUsername())
                        .password(passwordEncoder.encode(userDTO.getPassword()))
                        .nickname(userDTO.getNickname())
                        .activate(true)
                        .build()
        );


//        // Returns whether the result of the above three processes
//        userDTO = UserDTO.convertToUserDTO(userEntity);
//        assert userDTO != null;
//        userDTO.setAuthorities(userAuthorityRepository.findAllByUser_Username(userEntity.getUsername()).stream().map(userAuthorityJoinEntity ->
//                AuthorityDTO.builder().authorityName(userAuthorityJoinEntity.getAuthority().getAuthorityName()).build()).collect(Collectors.toSet())
//        );
//        return userDTO;
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
            userRepository.findAll().forEach(entity -> userDTOList.add(UserDTO.convertToUserDTO(entity)));
            return userDTOList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void removeUser(String username) {
        userAuthorityRepository.deleteAll(userAuthorityRepository.findAllByUser_Username(username));
        userRepository.deleteById(username);
    }

    @Override
    public Boolean nicknameValidation(String nickname) {
        return !userRepository.findByNickname(nickname).isPresent();
    }

}
