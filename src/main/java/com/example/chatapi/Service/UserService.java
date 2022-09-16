package com.example.chatapi.Service;

import com.example.chatapi.DTO.AuthorityDTO;
import com.example.chatapi.DTO.UserDTO;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public interface UserService {

    boolean signUp(UserDTO userDTO);

    UserDTO getUserInfo(String username) throws UsernameNotFoundException;

    List<UserDTO> getUserList() throws Exception;

    List<AuthorityDTO> getUserAuthorities(String username);

    Boolean nicknameValidation(String nickname);
}
