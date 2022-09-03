package com.example.chatapi.Entity;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "AUTHORITY")
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthorityEntity {

    @Id
    @Column(name = "authority_name", length = 50)
    private String authorityName;

    @ToString.Exclude
    @OneToMany(mappedBy = "authority")
    private Set<UserAuthorityJoinEntity> authorities;
}
