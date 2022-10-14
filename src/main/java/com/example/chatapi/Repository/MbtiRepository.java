package com.example.chatapi.Repository;

import com.example.chatapi.Entity.MBTI.MBTIInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MbtiRepository extends JpaRepository<MBTIInfoEntity, String> {

}
