package com.example.chatapi.Repository.User;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("local")
class OAuth2UserRepositoryTest {

    private static final Logger log = LoggerFactory.getLogger(OAuth2UserRepository.class);

    @Autowired
    private OAuth2UserRepository oAuth2UserRepository;

    @Test
    void existsByUser_UsernameAndOauth2Type_Type() {
        oAuth2UserRepository.existsByUser_UsernameAndOauth2Type_Name("Ahngbeom", "GitHub");
    }
}