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
    @JoinColumn(name = "oauth2_id")
    private OAuth2UserEntity oauth2Id;

    public void setId(Long id) {
        this.id = id;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public void setOauth2Id(OAuth2UserEntity oauth2Id) {
        this.oauth2Id = oauth2Id;
    }
}
