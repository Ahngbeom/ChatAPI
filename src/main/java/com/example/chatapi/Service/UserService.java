package com.example.chatapi.Service;

import com.example.chatapi.DTO.UserDTO;
import com.example.chatapi.Entity.MBTIInfoEntity;
import com.example.chatapi.Entity.UserEntity;

import java.util.Set;

public interface UserService {

    UserEntity signUp(UserDTO userDTO);

    UserDTO getUserInfo(String username);

    UserEntity addMbti(String username, MBTIInfoEntity mbtiInfoEntity) throws RuntimeException;

    Set<MBTIInfoEntity> getMbtiList(String username) throws RuntimeException;
}
