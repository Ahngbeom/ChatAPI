package com.example.chatapi.DTO;

import com.example.chatapi.Entity.User.UserEntity;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

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
    private Set<String> authorities;

    //    @ToString.Exclude
    private Set<String> mbtiList;

    private Set<String> oauth2List;

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
