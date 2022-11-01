package com.example.chatapi.Entity.User;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "OAUTH2_USER")
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OAuth2UserEntity {

    @Id
    private String id;

    @OneToOne
    @JoinColumn(name = "oauth2_type")
    private OAuth2Entity oauth2Type;

    @Column
    private String email;

    @Column
    private String nickname;

    @NotNull
    @Builder.Default
    @ColumnDefault("true")
    private boolean activate = true;

    @CreationTimestamp
    @Column(name = "reg_date", updatable = false)
    private LocalDateTime regDate;

    public void setId(String id) {
        this.id = id;
    }

    public void setOauth2Type(OAuth2Entity oauth2Type) {
        this.oauth2Type = oauth2Type;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setActivate(boolean activate) {
        this.activate = activate;
    }

    public void setRegDate(LocalDateTime regDate) {
        this.regDate = regDate;
    }
}
