package com.example.chatapi.Repository;

import com.example.chatapi.Entity.MBTI.UserMbtiJoinEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserMbtiRepository extends JpaRepository<UserMbtiJoinEntity, Long> {

//    @Query("select distinct UserMbtiJoinEntity.mbti, UserMbtiJoinEntity.user from UserMbtiJoinEntity left outer join UserEntity on UserEntity.id = UserMbtiJoinEntity.user.id where UserMbtiJoinEntity.user.id = ?1")
    List<UserMbtiJoinEntity> findAllByUser_id(Long userId);

    UserMbtiJoinEntity findByMbti_CodeAndUser_id(String code, Long userId);

    boolean existsByUser_id(Long userId);
    boolean existsByMbti_Code(String code);
    boolean existsByMbti_CodeAndUser_id(String code, Long userId);
//    boolean existsByCodeAndUser_id(String code, Long userId);

}
