package com.example.chatapi.Security;

import com.example.chatapi.Entity.Authority.AuthorityEntity;
import com.example.chatapi.Entity.Authority.UserAuthorityJoinEntity;
import com.example.chatapi.Entity.OAuth2Entity;
import com.example.chatapi.Entity.OAuth2UserEntity;
import com.example.chatapi.Entity.User.UserEntity;
import com.example.chatapi.Repository.User.OAuth2Repository;
import com.example.chatapi.Repository.User.OAuth2UserRepository;
import com.example.chatapi.Repository.User.UserAuthorityRepository;
import com.example.chatapi.Repository.User.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.util.Collections;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService {

    private final UserRepository userRepository;
    private final UserAuthorityRepository userAuthorityRepository;

    private final OAuth2Repository oAuth2Repository;
    private final OAuth2UserRepository oAuth2UserRepository;
    private final HttpSession session;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        DefaultOAuth2UserService defaultOAuth2UserService = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = defaultOAuth2UserService.loadUser(userRequest);

        log.info(oAuth2User.getAttributes().toString());
        log.info(saveOAuth2User(oAuth2User).toString());

        session.setAttribute("oAuthToken", userRequest.getAccessToken().getTokenValue());

        return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")), oAuth2User.getAttributes(), "login");
    }

    @Transactional
    public UserEntity saveOAuth2User(OAuth2User oAuth2User) {

        UserEntity userEntity = UserEntity.builder()
                .username(oAuth2User.getAttribute("login"))
                .nickname(oAuth2User.getAttribute("name"))
                .activate(true)
                .build();

        userEntity = userRepository.save(userEntity);

        grantAuthorityToOAuth2User(userEntity);

        // GitHub
        if (oAuth2User.getAttributes().containsKey("url") && oAuth2User.getAttributes().get("url").toString().startsWith("https://api.github.com/users/")) {
            OAuth2Entity oAuth2Entity = oAuth2Repository.saveAndFlush(OAuth2Entity.builder().type("GitHub").build());
            oAuth2UserRepository.save(OAuth2UserEntity.builder().user(userEntity).oAuth2Type(oAuth2Entity).build());
        }

        return userEntity;
    }

    @Transactional
    public UserAuthorityJoinEntity grantAuthorityToOAuth2User(UserEntity userEntity) {
        if (userAuthorityRepository.existsByUser_UsernameAndAuthority_AuthorityName(userEntity.getUsername(), "ROLE_USER"))
            return null;
        return userAuthorityRepository.save(
                UserAuthorityJoinEntity.builder()
                        .user(userEntity)
                        .authority(AuthorityEntity.builder().authorityName("ROLE_USER").build())
                        .build()
        );
    }

}
