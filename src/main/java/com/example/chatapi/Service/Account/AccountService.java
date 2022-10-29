package com.example.chatapi.Service.Account;

import com.example.chatapi.Repository.MbtiRepository;
import com.example.chatapi.Repository.User.AuthorityRepository;
import com.example.chatapi.Repository.User.UserAuthorityRepository;
import com.example.chatapi.Repository.User.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService {

    protected final Logger log = LoggerFactory.getLogger(AccountService.class);

    protected final UserRepository userRepository;
    protected final AuthorityRepository authorityRepository;
    protected final UserAuthorityRepository userAuthorityRepository;

    protected final MbtiRepository mbtiRepository;
    protected final PasswordEncoder passwordEncoder;

}
