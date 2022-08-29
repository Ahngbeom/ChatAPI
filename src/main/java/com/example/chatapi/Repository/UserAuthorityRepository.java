package com.example.chatapi.Repository;

import com.example.chatapi.Entity.UserAuthorityJoinEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAuthorityRepository extends JpaRepository<UserAuthorityJoinEntity, Long> {
}
