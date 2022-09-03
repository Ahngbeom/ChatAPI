package com.example.chatapi.Service;

import com.example.chatapi.DTO.AuthorityDTO;
import com.example.chatapi.DTO.UserDTO;
import com.example.chatapi.Entity.MBTIInfoEntity;
import com.example.chatapi.Entity.UserEntity;
import com.example.chatapi.Entity.UserMbtiJoinEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Set;

public interface UserService {

    boolean signUp(UserDTO userDTO);

    UserDTO getUserInfo(String username) throws UsernameNotFoundException;

    List<UserDTO> getUserList() throws Exception;

    List<AuthorityDTO> getUserAuthorities(Long userNo);

    UserEntity addMbti(String username, MBTIInfoEntity mbtiInfoEntity) throws RuntimeException;

    Set<UserMbtiJoinEntity> getMbtiList(String username) throws RuntimeException;

    Boolean nicknameValidation(String nickname);
}
