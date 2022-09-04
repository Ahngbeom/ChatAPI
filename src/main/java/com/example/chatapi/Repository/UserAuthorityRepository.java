package com.example.chatapi.Repository;

import com.example.chatapi.Entity.Authority.UserAuthorityJoinEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserAuthorityRepository extends JpaRepository<UserAuthorityJoinEntity, Long> {

    List<UserAuthorityJoinEntity> findAllByUser_Id(Long userId);
}
