package com.example.chatapi.DTO;

import com.example.chatapi.Entity.Authority.AuthorityEntity;
import lombok.*;


@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthorityDTO {

    private String authorityName;

    public static AuthorityDTO convertToAuthorityDTO(AuthorityEntity entity) {
        return AuthorityDTO.builder().authorityName(entity.getAuthorityName()).build();
    }
}
