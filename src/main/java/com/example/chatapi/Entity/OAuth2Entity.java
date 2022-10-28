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
    @Column(name = "name", length = 20)
    private String name;

    @OneToMany(mappedBy = "oauth2Type", fetch = FetchType.LAZY)
    private Set<OAuth2UserEntity> oauth2Types;


    public void setName(String name) {
        this.name = name;
    }
}
