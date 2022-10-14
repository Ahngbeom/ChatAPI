package com.example.chatapi.Repository;

import com.example.chatapi.Entity.Authority.UserAuthorityJoinEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserAuthorityRepository extends JpaRepository<UserAuthorityJoinEntity, Long> {

    List<UserAuthorityJoinEntity> findAllByUser_Username(String username);
}
