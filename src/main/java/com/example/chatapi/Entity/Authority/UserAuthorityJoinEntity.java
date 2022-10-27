package com.example.chatapi.Entity.Authority;

import com.example.chatapi.Entity.User.UserEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "USER_AUTHORITY")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserAuthorityJoinEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "authority_name")
    private AuthorityEntity authority;


}
