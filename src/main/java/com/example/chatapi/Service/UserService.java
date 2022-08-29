package com.example.chatapi.Service;

import com.example.chatapi.DTO.UserDTO;
import com.example.chatapi.Entity.MBTIInfoEntity;
import com.example.chatapi.Entity.UserEntity;
import com.example.chatapi.Entity.UserMbtiJoinEntity;

import java.util.List;
import java.util.Set;

public interface UserService {

    UserEntity signUp(UserDTO userDTO);

    UserDTO getUserInfo(String username);

    List<UserDTO> getUserList() throws Exception;

    UserEntity addMbti(String username, MBTIInfoEntity mbtiInfoEntity) throws RuntimeException;

    Set<UserMbtiJoinEntity> getMbtiList(String username) throws RuntimeException;

    Boolean nicknameValidation(String nickname);
}
