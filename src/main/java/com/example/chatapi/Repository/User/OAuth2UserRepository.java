package com.example.chatapi.Repository.User;

import com.example.chatapi.Entity.OAuth2UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OAuth2UserRepository extends JpaRepository<OAuth2UserEntity, Long> {
}
