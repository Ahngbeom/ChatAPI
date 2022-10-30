package com.example.chatapi.Repository.User;

import com.example.chatapi.Entity.User.OAuth2UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OAuth2UserRepository extends JpaRepository<OAuth2UserEntity, Long> {

    Optional<OAuth2UserEntity> findByNickname(String nickname);

//    boolean existsByUser_UsernameAndOauth2Type_Name(String username, String type);
}
