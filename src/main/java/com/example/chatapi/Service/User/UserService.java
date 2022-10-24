package com.example.chatapi.Service.User;

import com.example.chatapi.DTO.AuthorityDTO;
import com.example.chatapi.DTO.UserDTO;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public interface UserService {

    UserDTO signUp(UserDTO userDTO);

    UserDTO getUserInfo(String username) throws UsernameNotFoundException;

    List<UserDTO> getUserList();

    List<AuthorityDTO> getUserAuthorities(String username);

    Boolean nicknameValidation(String nickname);
}