package com.example.chatapi.Repository;

import com.example.chatapi.Entity.MBTIInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MbtiRepository extends JpaRepository<MBTIInfoEntity, String> {
}
