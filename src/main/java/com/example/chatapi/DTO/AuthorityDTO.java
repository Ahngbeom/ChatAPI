package com.example.chatapi.DTO;

import com.example.chatapi.Entity.Authority.AuthorityEntity;
import lombok.*;

import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthorityDTO {

    public static final String ADMIN = "ROLE_ADMIN";
    public static final String MANAGER = "ROLE_MANAGER";
    public static final String USER = "ROLE_USER";

    private String authorityName;

    public static AuthorityDTO convertToAuthorityDTO(AuthorityEntity entity) {
        return AuthorityDTO.builder().authorityName(entity.getAuthorityName()).build();
    }

    public static Set<AuthorityDTO> authoritiesToSet(String... auth) {
        Set<AuthorityDTO> authorityDTOSet = new HashSet<>();
        for (String authority : auth) {
            if (authority.equals(ADMIN) || authority.equals(MANAGER) || authority.equals(USER))
                authorityDTOSet.add(AuthorityDTO.builder().authorityName(authority).build());
        }
        return authorityDTOSet;
    }
}
