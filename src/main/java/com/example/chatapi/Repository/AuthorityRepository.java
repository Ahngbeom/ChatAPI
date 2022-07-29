package com.example.chatapi.Repository;

import com.example.chatapi.Entity.AuthorityEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<AuthorityEntity, String> {
}
