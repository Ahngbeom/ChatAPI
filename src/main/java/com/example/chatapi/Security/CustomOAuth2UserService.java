package com.example.chatapi.Security;

import com.example.chatapi.Entity.Authority.AuthorityEntity;
import com.example.chatapi.Entity.Authority.UserAuthorityJoinEntity;
import com.example.chatapi.Entity.User.OAuth2Entity;
import com.example.chatapi.Entity.User.OAuth2UserEntity;
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
import java.util.Objects;


/*
Reference:
    - https://blog.naver.com/PostView.nhn?blogId=qjawnswkd&logNo=222293370027
    - https://velog.io/@shawnhansh/Spring-Security와-OAuth23-구글-로그인-연동하기
*/

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;
    private final UserAuthorityRepository userAuthorityRepository;

    private final OAuth2Repository oAuth2Repository;
    private final OAuth2UserRepository oAuth2UserRepository;
    private final HttpSession session;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        DefaultOAuth2UserService defaultOAuth2UserService = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = defaultOAuth2UserService.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        saveOrUpdate(registrationId, oAuth2User, userNameAttributeName);

        session.setAttribute("oAuthToken", userRequest.getAccessToken().getTokenValue());

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")),
                oAuth2User.getAttributes(), userNameAttributeName);
    }

    @Transactional
    public void saveOrUpdate(String registrationId, OAuth2User oAuth2User, String idAttributeName) {
        log.info("Registration ID: " + registrationId);
        log.info("Attribute Name for ID: " + idAttributeName);
        log.info(oAuth2User.getAttributes().toString());
        log.info("ID: " + oAuth2User.getAttribute(idAttributeName) + "(" + Objects.requireNonNull(oAuth2User.getAttribute(idAttributeName)).getClass().getName() + ")");
        OAuth2UserEntity oAuth2UserEntity = null;

        OAuth2Entity oAuth2Entity = oAuth2Repository.save(OAuth2Entity.builder().name(registrationId).build());
        switch (registrationId) {
            case "github": {
//                if (!oAuth2UserRepository.existsById(Long.valueOf(Objects.requireNonNull(oAuth2User.getAttribute(idAttributeName)).toString())))
                oAuth2UserEntity = oAuth2UserRepository.save(
                        OAuth2UserEntity.builder()
                                .id(Objects.requireNonNull(oAuth2User.getAttribute(idAttributeName)).toString())
                                .oauth2Type(oAuth2Entity)
                                .email(oAuth2User.getAttribute("email"))
                                .nickname(oAuth2User.getAttribute("login"))
                                .build());
//                else
//                    oAuth2UserEntity = oAuth2UserRepository.getReferenceById(Long.valueOf(Objects.requireNonNull(oAuth2User.getAttribute(idAttributeName)).toString()));
                break;
            }
            case "google": {
//                if (!oAuth2UserRepository.existsById(Long.valueOf(Objects.requireNonNull(oAuth2User.getAttribute(idAttributeName)).toString())))
                oAuth2UserEntity = oAuth2UserRepository.save(
                        OAuth2UserEntity.builder()
                                .id(Objects.requireNonNull(oAuth2User.getAttribute(idAttributeName)).toString())
                                .oauth2Type(oAuth2Entity)
                                .email(oAuth2User.getAttribute("email"))
                                .nickname(oAuth2User.getAttribute("name"))
                                .build());
//                else
//                    oAuth2UserEntity = oAuth2UserRepository.getReferenceById(Long.valueOf(Objects.requireNonNull(oAuth2User.getAttribute(idAttributeName)).toString()));
                break;
            }
            default:
                break;
        }
        assert oAuth2UserEntity != null;
        log.info(oAuth2UserEntity.toString());
    }

//    @Transactional
//    public void grantAuthorityToOAuth2User(OAuth2UserEntity oAuth2UserEntity) {
//        if (!userAuthorityRepository.existsByUser_UsernameAndAuthority_AuthorityName(oAuth2UserEntity.getUsername(), "ROLE_USER"))
//            userAuthorityRepository.save(
//                    UserAuthorityJoinEntity.builder()
//                            .user(oAuth2UserEntity)
//                            .authority(AuthorityEntity.builder().authorityName("ROLE_USER").build())
//                            .build()
//            );
//    }

}
