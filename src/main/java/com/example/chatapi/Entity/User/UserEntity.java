package com.example.chatapi.Entity.User;

import com.example.chatapi.DTO.UserDTO;
import com.example.chatapi.Entity.Authority.UserAuthorityJoinEntity;
import com.example.chatapi.Entity.MBTI.UserMbtiJoinEntity;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "USER")
@Getter
@Setter
@ToString
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

    @Column(length = 50, unique = true)
    private String nickname;

    @Column
    private boolean activate;

//    @ManyToMany // 실무에서 권장하지않는 방법. (@OneToMany로 관계 변경 후, 조인 테이블을 엔티티로 승격시켜 @ManyToOne 관계로 설정)
//    @JoinTable(
//            name = "user_authority",
//            joinColumns = {@JoinColumn(name = "id", referencedColumnName = "id"), @JoinColumn(name = "username", referencedColumnName = "username")},
//            inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "authority_name")})
//    private Set<AuthorityEntity> authorities;

    @ToString.Exclude
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_authority")
    private Set<UserAuthorityJoinEntity> authorities;

//    @ManyToMany
//    @JoinTable(name = "user_mbti",
//                joinColumns = {@JoinColumn(name = "id", referencedColumnName = "id"), @JoinColumn(name = "username", referencedColumnName = "username")},
//                inverseJoinColumns = {@JoinColumn(name = "mbti", referencedColumnName = "mbti")})
//    private Set<MBTIInfoEntity> mbtiList;

    @ToString.Exclude
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_mbti")
    private Set<UserMbtiJoinEntity> mbtiList;


    public static UserEntity convertToUserEntity(UserDTO dto) {
        return UserEntity.builder()
                .id(dto.getId())
                .username(dto.getUsername())
                .password(dto.getPassword())
                .nickname(dto.getPassword())
                .activate(dto.isActivate())
                .build();
    }
}