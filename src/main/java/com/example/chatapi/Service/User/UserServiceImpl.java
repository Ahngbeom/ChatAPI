package com.example.chatapi.Service.User;

import com.example.chatapi.DTO.AuthorityDTO;
import com.example.chatapi.DTO.UserDTO;
import com.example.chatapi.Entity.Authority.AuthorityEntity;
import com.example.chatapi.Entity.Authority.UserAuthorityJoinEntity;
import com.example.chatapi.Entity.User.UserEntity;
import com.example.chatapi.Repository.AuthorityRepository;
import com.example.chatapi.Repository.MbtiRepository;
import com.example.chatapi.Repository.UserAuthorityRepository;
import com.example.chatapi.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
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
    public UserDTO signUp(UserDTO userDTO) throws RuntimeException {
//        AtomicBoolean result = new AtomicBoolean(true);

        if (userRepository.findByUsername(userDTO.getUsername()).isPresent())
            throw new RuntimeException("Exist Username");

        // Storing requested User's Authorities in HashSet and authority table
        Set<AuthorityEntity> authorityEntityHashSet = new HashSet<>();
        userDTO.getAuthorities().forEach(authorityDTO -> {
            AuthorityEntity authority = AuthorityEntity.builder()
                    .authorityName(authorityDTO.getAuthorityName())
                    .build();
            authorityEntityHashSet.add(authority);
//            result.set(result.get() & authorityRepository.save(authority).getClass().equals(AuthorityEntity.class));
        });

        // Storing requested User's Information in user table
        UserEntity userEntity = userRepository.save(
                UserEntity.builder()
                        .username(userDTO.getUsername())
                        .password(passwordEncoder.encode(userDTO.getPassword()))
                        .nickname(userDTO.getNickname())
                        .activate(true)
                        .build()
        );
//        result.set(result.get() | userRepository.save(userEntity).getClass().equals(UserEntity.class));

        // Storing UserEntity and AuthorityEntity object variables in user_authority table
        authorityEntityHashSet.forEach(authorityEntity ->
                userAuthorityRepository.save(UserAuthorityJoinEntity.builder()
                        .user(userEntity)
                        .authority(authorityEntity)
                        .build()));

        // Returns whether the result of the above three processes
        userDTO = UserDTO.convertToUserDTO(userEntity);
        assert userDTO != null;
        userDTO.setAuthorities(userAuthorityRepository.findAllByUser_Username(userEntity.getUsername()).stream().map(userAuthorityJoinEntity ->
                AuthorityDTO.builder().authorityName(userAuthorityJoinEntity.getAuthority().getAuthorityName()).build()).collect(Collectors.toSet())
        );
        return userDTO;
    }

    @Override
    public UserDTO getUserInfo(String username) throws UsernameNotFoundException {
//        UserEntity entity = userRepository.findOneWithAuthoritiesByUsername(username).orElseThrow(() -> new RuntimeException("Not Found UserEntity in DataBase"));
        UserEntity entity = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Not Found UserEntity in DataBase"));

        List<UserAuthorityJoinEntity> userAuthorityEntity = userAuthorityRepository.findAllByUser_Username(entity.getUsername());
//        userAuthorityEntity = userAuthorityRepository.findAll();
        Set<AuthorityDTO> authorityDTOHashSet = new HashSet<>();
        userAuthorityEntity.forEach(userAuthorityJoinEntity -> authorityDTOHashSet.add(AuthorityDTO.builder()
                .authorityName(userAuthorityJoinEntity.getAuthority().getAuthorityName()).build()));

//        log.info(authorityDTOHashSet.toString());
        return UserDTO.builder()
//                .id(entity.getId())
                .username(entity.getUsername())
                .password(entity.getPassword())
                .nickname(entity.getNickname())
                .activate(entity.isActivate())
                .regDate(entity.getRegDate())
                .updateDate(entity.getUpdateDate())
                .authorities(authorityDTOHashSet)
//                    .mbtiInfoList(entity.getMbtiList())
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
                        AuthorityDTO.convertToAuthorityDTO(userAuthorityJoinEntity.getAuthority()))
                .collect(Collectors.toList());
    }

    @Override
    public Boolean nicknameValidation(String nickname) {
        return !userRepository.findByNickname(nickname).isPresent();
    }

}
