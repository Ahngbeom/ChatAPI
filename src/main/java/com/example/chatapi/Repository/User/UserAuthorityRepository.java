package com.example.chatapi.Repository.User;

import com.example.chatapi.Entity.Authority.AuthorityEntity;
import com.example.chatapi.Entity.Authority.UserAuthorityJoinEntity;
import com.example.chatapi.Entity.User.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserAuthorityRepository extends JpaRepository<UserAuthorityJoinEntity, Long> {

    List<UserAuthorityJoinEntity> findAllByUser_Username(String username);

    boolean existsByUser_UsernameAndAuthority_AuthorityName(String username, String auth);

}
