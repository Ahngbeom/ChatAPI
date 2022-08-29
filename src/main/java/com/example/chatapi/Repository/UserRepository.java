package com.example.chatapi.Repository;

import com.example.chatapi.Entity.UserEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByUsername(String username);
    Optional<UserEntity> findByNickname(String nickname);

//    @EntityGraph(attributePaths = {"authorities", "mbtiList"}) // 쿼리 수행 시 Lazy 조회가 아닌 Eager 조회로 authrities 정보를 가져오게한다. (즉시 로딩으로 연관된 데이터도 같이 조회한다.)
//    Optional<UserEntity> findOneWithAuthoritiesByUsername(String username);

//    @EntityGraph(attributePaths = {"authorities", "mbtiList"}) // 쿼리 수행 시 Lazy 조회가 아닌 Eager 조회로 authrities 정보를 가져오게한다. (즉시 로딩으로 연관된 데이터도 같이 조회한다.)
//    Optional<UserEntity> findAllWithAuthoritiesAndMBTIList();
}
