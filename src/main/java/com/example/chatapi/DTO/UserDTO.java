package com.example.chatapi.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.NotNull;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@ToString(exclude = "authorities")
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

    private Set<AuthorityDTO> authorities;

    private Set<MbtiDTO> mbtiInfoList;
}
