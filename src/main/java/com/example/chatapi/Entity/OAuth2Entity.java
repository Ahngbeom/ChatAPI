package com.example.chatapi.Entity;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "OAUTH2")
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OAuth2Entity {

    @Id
    @Column(name = "type", length = 20)
    private String type;

    @OneToMany(mappedBy = "oAuth2Type", fetch = FetchType.LAZY)
    private Set<OAuth2UserEntity> oAuth2Type;


    public void setType(String type) {
        this.type = type;
    }
}
