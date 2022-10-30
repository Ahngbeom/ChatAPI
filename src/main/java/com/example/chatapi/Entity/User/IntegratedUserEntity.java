package com.example.chatapi.Entity.User;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "INTEGRATED_USER")
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IntegratedUserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_name")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "type")
    private OAuth2UserEntity oauth2Type;

    public void setId(Long id) {
        this.id = id;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public void setOauth2Type(OAuth2UserEntity oauth2Type) {
        this.oauth2Type = oauth2Type;
    }
}
