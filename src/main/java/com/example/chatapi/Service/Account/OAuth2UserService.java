package com.example.chatapi.Service.Account;

import com.example.chatapi.DTO.UserDTO;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface OAuth2UserService {
    UserDTO getOAuth2UserInfo(String id) throws UsernameNotFoundException;

}
