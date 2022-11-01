package com.example.chatapi.DTO;

import com.example.chatapi.Entity.User.OAuth2UserEntity;
import com.example.chatapi.Entity.User.UserEntity;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Collections;
import java.util.Set;

@Getter
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

    private String email;

    //    @NotNull
    private String nickname;

    private boolean activate;

    private LocalDateTime regDate;
    //    private String regDate;
    private LocalDateTime updateDate;
//    private String updateDate;

    //    @ToString.Exclude
    private Set<String> authorities;

    //    @ToString.Exclude
    private Set<String> mbtiList;

    private Set<String> oauth2List;

    //    public static UserDTO convertToUserDTO(UserEntity userEntity, AuthorityEntity authorityEntity, MBTIInfoEntity mbtiInfoEntity) {
    public static UserDTO UserEntityToDTO(UserEntity userEntity) {
        try {
            return UserDTO.builder()
//                    .id(userEntity.getId())
                    .username(userEntity.getUsername())
                    .password(userEntity.getPassword())
                    .email(userEntity.getEmail())
                    .nickname(userEntity.getNickname())
                    .activate(userEntity.isActivate())
                    .regDate(userEntity.getRegDate())
//                    .regDate(userEntity.getRegDate().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG)))
                    .updateDate(userEntity.getUpdateDate())
//                    .updateDate(userEntity.getUpdateDate().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG)))
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static UserDTO oauth2UserEntityToDTO(OAuth2UserEntity oAuth2UserEntity) {
        try {
            return UserDTO.builder()
//                    .id(userEntity.getId())
                    .username(oAuth2UserEntity.getId())
                    .email(oAuth2UserEntity.getEmail())
                    .nickname(oAuth2UserEntity.getNickname())
                    .activate(oAuth2UserEntity.isActivate())
                    .regDate(oAuth2UserEntity.getRegDate())
//                    .regDate(oAuth2UserEntity.getRegDate().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG)))
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

//    public void setRegDate(String regDate) {
//        this.regDate = regDate;
//    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
//    public void setUpdateDate(String updateDate) {
//        this.updateDate = updateDate;
//    }

    public void setActivate(boolean activate) {
        this.activate = activate;
    }

    public void setRegDate(LocalDateTime regDate) {
        this.regDate = regDate;
//        this.regDate = regDate.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG));
    }

    public void setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
//        this.updateDate = updateDate.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG));
    }

    public void setAuthorities(Set<String> authorities) {
        this.authorities = authorities;
    }

    public void setMbtiList(Set<String> mbtiList) {
        this.mbtiList = mbtiList;
    }

    public void setOauth2List(Set<String> oauth2List) {
        this.oauth2List = oauth2List;
    }

}
