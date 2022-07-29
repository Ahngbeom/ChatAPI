package com.example.chatapi.Entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "USER")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity implements Serializable {
    // Serializable : 분산환경, 직렬화하여 외부 전송?

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, unique = true)
    private String username;

    @Column(length = 100)
    private String password;

    @Column(length = 50)
    private String nickname;

    @Column
    private boolean activate;

    @ManyToMany
    @JoinTable(
            name = "user_authority",
            joinColumns = {@JoinColumn(name = "id", referencedColumnName = "id"), @JoinColumn(name = "username", referencedColumnName = "username")},
            inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "authority_name")})
    private Set<AuthorityEntity> authorities;

}
