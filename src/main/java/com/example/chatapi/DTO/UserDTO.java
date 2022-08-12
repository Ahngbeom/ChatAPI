package com.example.chatapi.DTO;

import com.example.chatapi.Entity.AuthorityEntity;
import com.example.chatapi.Entity.MBTIInfoEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.NotNull;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private Long id;

    @NotNull
    private String username;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @NotNull
    private String nickname;

    private boolean activate;

    private Set<AuthorityEntity> authorities;

    private Set<MBTIInfoEntity> mbtiInfoList;
}
