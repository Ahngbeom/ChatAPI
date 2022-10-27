package com.example.chatapi.Repository.User;

import com.example.chatapi.Entity.OAuth2Entity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OAuth2Repository extends JpaRepository<OAuth2Entity, String> {
}
