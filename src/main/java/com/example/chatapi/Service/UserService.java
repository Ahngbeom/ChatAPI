package com.example.chatapi.Service;

import com.example.chatapi.DTO.UserDTO;
import com.example.chatapi.Entity.UserEntity;

public interface UserService {

    UserEntity signUp(UserDTO userDTO);
}
