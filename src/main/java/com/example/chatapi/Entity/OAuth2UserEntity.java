package com.example.chatapi.Entity;

import com.example.chatapi.Entity.User.UserEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "OAUTH2_USER")
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OAuth2UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_name")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "oauth2_type")
    private OAuth2Entity oAuth2Type;

    public void setId(Long id) {
        this.id = id;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public void setoAuth2Type(OAuth2Entity oAuth2Type) {
        this.oAuth2Type = oAuth2Type;
    }
}
