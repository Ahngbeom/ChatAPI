package com.example.chatapi.Repository;

import com.example.chatapi.Entity.UserMbtiJoinEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserMbtiRepository extends JpaRepository<UserMbtiJoinEntity, Long> {

}
