package com.example.chatapi.Repository;

import com.example.chatapi.Entity.MBTI.UserMbtiJoinEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserMbtiRepository extends JpaRepository<UserMbtiJoinEntity, Long> {

    List<UserMbtiJoinEntity> findDistinctByUser_Id(Long userId);

}
