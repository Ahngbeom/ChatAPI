package com.example.chatapi.Entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "USER_MBTI")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserMbtiJoinEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "mbti")
    private MBTIInfoEntity mbti;

}
