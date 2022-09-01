package com.example.chatapi.Service;

import com.example.chatapi.DTO.AuthorityDTO;
import com.example.chatapi.DTO.UserDTO;
import com.example.chatapi.Entity.*;
import com.example.chatapi.Repository.AuthorityRepository;
import com.example.chatapi.Repository.MbtiRepository;
import com.example.chatapi.Repository.UserAuthorityRepository;
import com.example.chatapi.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;
    private final UserAuthorityRepository userAuthorityRepository;

    private final MbtiRepository mbtiRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @PostConstruct
    public void setAdmin() throws RuntimeException {
        /*
            Spring Bean LifeCycle CallBack - @PostConstruct
            빈 생명주기 콜백: 스프링 빈이 생성된 후 의존관계 주입이 완료되거나 죽기 직전에 스프링 빈 안에 존재하는 메소드를 호출해주는 기능
            초기화 콜백 함수 setAdmin 함수를 추가하여 H2 데이터베이스에 Admin 계정을 등록한다.
         */
        try {
            if (userRepository.findByUsername("admin").isPresent())
                throw new RuntimeException("EXIST ADMIN ACCOUNT");

            UserDTO adminDTO = UserDTO.builder()
                    .username("admin")
                    .password("admin")
                    .nickname("ADMIN")
                    .authorities(Collections.singleton(AuthorityDTO.builder().authorityName("ROLE_ADMIN").build()))
                    .mbtiInfoList(null)
                    .build();
            log.info(String.valueOf(signUp(adminDTO)));

//            AuthorityEntity authority = AuthorityEntity.builder()
//                    .authorityName("ROLE_ADMIN")
//                    .build();
//            if (authorityRepository.save(authority).getClass() != AuthorityEntity.class)
//                throw new RuntimeException("ERROR SAVED ADMIN AUTHORITY ON AUTHORITY TABLE");

//            authority = AuthorityEntity.builder()
//                    .authorityName("ROLE_USER")
//                    .build();
//            if (authorityRepository.save(authority).getClass() != AuthorityEntity.class)
//                throw new RuntimeException("ERROR SAVED USER AUTHORITY SAVE ON AUTHORITY TABLE");

//            log.info("SUCCESS SAVE ON AUTHORITY TABLE");

//            Set<AuthorityEntity> adminAuthorities = new HashSet<>();
//            adminAuthorities.add(AuthorityEntity.builder().authorityName("ROLE_ADMIN").build());
//            adminAuthorities.add(AuthorityEntity.builder().authorityName("ROLE_USER").build());
//
//            UserEntity user = UserEntity.builder()
//                    .username("admin")
//                    .password(passwordEncoder.encode("admin"))
//                    .nickname("ADMIN")
//                    .authorities(adminAuthorities)
//                    .activate(true)
//                    .build();
//
//            if (userRepository.save(user).getClass() != UserEntity.class)
//                throw new RuntimeException("ERROR SAVE ON USER TABLE");
//
//            log.info("SUCCESS SAVE ON USERS TABLE");
        } catch (RuntimeException e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
    }

    @Override
    public boolean signUp(UserDTO userDTO) throws RuntimeException {
        try {
            AtomicBoolean result = new AtomicBoolean(true);

            if (userRepository.findByUsername(userDTO.getUsername()).isPresent())
                throw new RuntimeException("Exist Username");

            // Storing requested User's Authorities in HashSet and authority table
            Set<AuthorityEntity> authorityEntityHashSet = new HashSet<>();
            userDTO.getAuthorities().forEach(authorityDTO -> {
                AuthorityEntity authority = AuthorityEntity.builder()
                        .authorityName(authorityDTO.getAuthorityName())
                        .build();
                authorityEntityHashSet.add(authority);
                result.set(result.get() & authorityRepository.save(authority).getClass().equals(AuthorityEntity.class));
            });

            // Storing requested User's Information in user table
            UserEntity userEntity =
                    UserEntity.builder()
                            .username(userDTO.getUsername())
                            .password(passwordEncoder.encode(userDTO.getPassword()))
                            .nickname(userDTO.getNickname())
                            .activate(true)
                            .build();
            result.set(result.get() | userRepository.save(userEntity).getClass().equals(UserEntity.class));

            // Storing UserEntity and AuthorityEntity object variables in user_authority table
            authorityEntityHashSet.forEach(authorityEntity -> result.set(result.get() | userAuthorityRepository.save(UserAuthorityJoinEntity.builder()
                    .user(userEntity)
                    .authority(authorityEntity)
                    .build()).getClass().equals(UserAuthorityJoinEntity.class)));

            // Returns whether the result of the above three processes
            return result.get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public UserDTO getUserInfo(String username) throws UsernameNotFoundException {
//        UserEntity entity = userRepository.findOneWithAuthoritiesByUsername(username).orElseThrow(() -> new RuntimeException("Not Found UserEntity in DataBase"));
        UserEntity entity = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Not Found UserEntity in DataBase"));

        List<UserAuthorityJoinEntity> userAuthorityEntity = userAuthorityRepository.findAllByUser_Id(entity.getId());
//        userAuthorityEntity = userAuthorityRepository.findAll();
        Set<AuthorityDTO> authorityDTOHashSet = new HashSet<>();
        userAuthorityEntity.forEach(userAuthorityJoinEntity -> authorityDTOHashSet.add(AuthorityDTO.builder()
                .authorityName(userAuthorityJoinEntity.getAuthority().getAuthorityName()).build()));

        return UserDTO.builder()
                .id(entity.getId())
                .username(entity.getUsername())
                .password(entity.getPassword())
                .nickname(entity.getNickname())
                .activate(entity.isActivate())
                .authorities(authorityDTOHashSet)
//                    .mbtiInfoList(entity.getMbtiList())
                .build();
    }

    @Override
    public List<UserDTO> getUserList() {
        try {
            List<UserDTO> userDTOList = new ArrayList<>();

            userRepository.findAll().forEach(userEntity -> {
                log.warn(userEntity.getUsername());
                userDTOList.add(userEntity.dtoConverter());
            });

            return userDTOList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public UserEntity addMbti(String username, MBTIInfoEntity mbtiInfoEntity) throws RuntimeException {
//        UserEntity userEntity = userRepository.findOneWithAuthoritiesByUsername(username).orElseThrow(() -> new RuntimeException("Not found UserEntity"));
        UserEntity userEntity = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("Not found UserEntity"));
//        userEntity.setMbtiList(Collections.singleton(mbtiInfoEntity));
        return userRepository.save(userEntity);
    }

    @Override
    public Set<UserMbtiJoinEntity> getMbtiList(String username) throws RuntimeException {
//        UserEntity userEntity = userRepository.findOneWithAuthoritiesByUsername(username).orElseThrow(() -> new RuntimeException("Not found UserEntity"));
        UserEntity userEntity = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("Not found UserEntity"));
        return userEntity.getMbtiList();
    }

    @Override
    public Boolean nicknameValidation(String nickname) {
        return !userRepository.findByNickname(nickname).isPresent();
    }
}
