package com.example.chatapi.Service.Account;

import com.example.chatapi.DTO.UserDTO;
import com.example.chatapi.Entity.Authority.UserAuthorityJoinEntity;
import com.example.chatapi.Entity.User.UserEntity;
import com.example.chatapi.Repository.User.AuthorityRepository;
import com.example.chatapi.Repository.MbtiRepository;
import com.example.chatapi.Repository.User.OAuth2UserRepository;
import com.example.chatapi.Repository.User.UserAuthorityRepository;
import com.example.chatapi.Repository.User.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl extends AccountService implements UserService {

    public UserServiceImpl(UserRepository userRepository, OAuth2UserRepository oAuth2UserRepository, AuthorityRepository authorityRepository, UserAuthorityRepository userAuthorityRepository, MbtiRepository mbtiRepository, PasswordEncoder passwordEncoder) {
        super(userRepository, oAuth2UserRepository, authorityRepository, userAuthorityRepository, mbtiRepository, passwordEncoder);
    }

    @Override
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
    }

    @Override
    public UserDTO getUserInfo(String username) throws UsernameNotFoundException {
        UserEntity entity = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Not Found UserEntity in DataBase"));

        UserDTO userDTO = UserDTO.UserEntityToDTO(entity);
        assert userDTO != null;
        userDTO.setAuthorities(entity.getAuthorities().stream().map(userAuthorityJoinEntity -> userAuthorityJoinEntity.getAuthority().getAuthorityName()).collect(Collectors.toSet()));
        userDTO.setMbtiList(entity.getMbtiList().stream().map(userMbtiJoinEntity -> userMbtiJoinEntity.getMbti().getCode()).collect(Collectors.toSet()));
        return userDTO;
    }

    @Override
    public List<UserDTO> getUserList() {
        try {
            List<UserDTO> userDTOList = new ArrayList<>();
            userRepository.findAll().forEach(entity -> userDTOList.add(UserDTO.UserEntityToDTO(entity)));
            return userDTOList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void updateUser(UserDTO userDTO) {
        UserEntity updateUserEntity = userRepository.findByUsername(userDTO.getUsername()).orElseThrow(RuntimeException::new);
        updateUserEntity.setEmail(userDTO.getEmail());
        updateUserEntity.setNickname(userDTO.getNickname());
        userRepository.save(updateUserEntity);
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

    @Override
    public Boolean passwordMatchChecker(String username, String password) {
        UserEntity userEntity = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Not Found User."));
        return passwordEncoder.matches(password, userEntity.getPassword());
    }

    @Override
    public void changePassword(String username, String password) {
        UserEntity userEntity = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Not Found User."));
        userEntity.setPassword(passwordEncoder.encode(password));
        userRepository.save(userEntity);
    }
}
