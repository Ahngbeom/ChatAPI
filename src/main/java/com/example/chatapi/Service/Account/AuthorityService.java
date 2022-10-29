package com.example.chatapi.Service.Account;

import com.example.chatapi.DTO.AuthorityDTO;

import java.util.Collection;
import java.util.List;

public interface AuthorityService {

    List<AuthorityDTO> getUserAuthorities(String username);

    void addAuthority(String username, Collection<String> authorities);

    void updateAuthority(String username, Collection<String> authorities);
}
