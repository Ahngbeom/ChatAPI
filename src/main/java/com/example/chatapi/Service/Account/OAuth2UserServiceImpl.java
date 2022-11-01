package com.example.chatapi.Service.Account;

import com.example.chatapi.DTO.AuthorityDTO;
import com.example.chatapi.DTO.UserDTO;
import com.example.chatapi.Entity.User.OAuth2UserEntity;
import com.example.chatapi.Repository.MbtiRepository;
import com.example.chatapi.Repository.User.AuthorityRepository;
import com.example.chatapi.Repository.User.OAuth2UserRepository;
import com.example.chatapi.Repository.User.UserAuthorityRepository;
import com.example.chatapi.Repository.User.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class OAuth2UserServiceImpl extends AccountService implements OAuth2UserService {

    public OAuth2UserServiceImpl(UserRepository userRepository, OAuth2UserRepository oAuth2UserRepository, AuthorityRepository authorityRepository, UserAuthorityRepository userAuthorityRepository, MbtiRepository mbtiRepository, PasswordEncoder passwordEncoder) {
        super(userRepository, oAuth2UserRepository, authorityRepository, userAuthorityRepository, mbtiRepository, passwordEncoder);
    }

    @Override
    public UserDTO getOAuth2UserInfo(String id) throws UsernameNotFoundException {
        OAuth2UserEntity oAuth2UserEntity = oAuth2UserRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("Not Found OAuth2UserEntity in DataBase"));
        UserDTO userDTO = UserDTO.oauth2UserEntityToDTO(oAuth2UserEntity);

        assert userDTO != null;
        userDTO.setAuthorities(Collections.singleton(AuthorityDTO.USER));
        userDTO.setOauth2List(Collections.singleton(oAuth2UserEntity.getOauth2Type().getName()));
        return userDTO;
    }
}
