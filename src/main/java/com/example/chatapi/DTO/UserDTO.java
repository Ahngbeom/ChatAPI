package com.example.chatapi.DTO;

import com.example.chatapi.Entity.User.UserEntity;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

//    private Long id;

    //    @NotNull
    private String username;

    @ToString.Exclude
//    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    //    @NotNull
    private String nickname;

    private boolean activate;

    private LocalDateTime regDate;
    private LocalDateTime updateDate;

    //    @ToString.Exclude
    private Set<AuthorityDTO> authorities;

    //    @ToString.Exclude
    private Set<MbtiDTO> mbtiInfoList;

    //    public static UserDTO convertToUserDTO(UserEntity userEntity, AuthorityEntity authorityEntity, MBTIInfoEntity mbtiInfoEntity) {
    public static UserDTO convertToUserDTO(UserEntity userEntity) {
        try {
            return UserDTO.builder()
//                    .id(userEntity.getId())
                    .username(userEntity.getUsername())
                    .password(userEntity.getPassword())
                    .nickname(userEntity.getNickname())
                    .activate(userEntity.isActivate())
                    .regDate(userEntity.getRegDate())
                    .updateDate(userEntity.getUpdateDate())
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
