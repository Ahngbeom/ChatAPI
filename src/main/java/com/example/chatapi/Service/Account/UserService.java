package com.example.chatapi.Service.Account;

import com.example.chatapi.DTO.UserDTO;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public interface UserService {

    void signUp(UserDTO userDTO);

    UserDTO getUserInfo(String username) throws UsernameNotFoundException;

    List<UserDTO> getUserList();

    void removeUser(String username);

    Boolean nicknameValidation(String nickname);
}
