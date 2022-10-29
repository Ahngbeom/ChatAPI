package com.example.chatapi.Service.Account;

import com.example.chatapi.DTO.AuthorityDTO;
import com.example.chatapi.Entity.Authority.UserAuthorityJoinEntity;
import com.example.chatapi.Entity.User.UserEntity;
import com.example.chatapi.Repository.MbtiRepository;
import com.example.chatapi.Repository.User.AuthorityRepository;
import com.example.chatapi.Repository.User.UserAuthorityRepository;
import com.example.chatapi.Repository.User.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorityServiceImpl extends AccountService implements AuthorityService {

    public AuthorityServiceImpl(UserRepository userRepository, AuthorityRepository authorityRepository, UserAuthorityRepository userAuthorityRepository, MbtiRepository mbtiRepository, PasswordEncoder passwordEncoder) {
        super(userRepository, authorityRepository, userAuthorityRepository, mbtiRepository, passwordEncoder);
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
    public void addAuthority(String username, Collection<String> authorities) {
        List<UserAuthorityJoinEntity> userAuthorityJoinEntities = new ArrayList<>();
        UserEntity userEntity = userRepository.findByUsername(username).orElseThrow(RuntimeException::new);

        // Storing UserEntity and AuthorityEntity object variables in user_authority table
        for (String auth : authorities) {
            userAuthorityJoinEntities.add(UserAuthorityJoinEntity.builder().user(userEntity).authority(authorityRepository.getReferenceById(auth)).build());
        }
        userAuthorityRepository.saveAll(userAuthorityJoinEntities);
    }

    @Override
    public void updateAuthority(String username, Collection<String> authorities) {
        for (UserAuthorityJoinEntity entity : userAuthorityRepository.findAllByUser_Username(username)) {
            authorities.remove(entity.getAuthority().getAuthorityName());
        }
        addAuthority(username, authorities);
    }

}
