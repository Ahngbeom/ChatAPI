package com.example.chatapi.DTO;

import com.example.chatapi.Entity.OAuth2UserEntity;
import lombok.*;

@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OAuth2DTO {

    private String type;

    public static OAuth2DTO oauth2UserEntityToOAuth2Dto(OAuth2UserEntity entity) {
        return OAuth2DTO.builder().type(entity.getOauth2Type().getName()).build();
    }

    public void setType(String type) {
        this.type = type;
    }
}
