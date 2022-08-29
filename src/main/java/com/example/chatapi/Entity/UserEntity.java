package com.example.chatapi.Entity;

import com.example.chatapi.DTO.UserDTO;
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

//    @ManyToMany // 실무에서 권장하지않는 방법. (@OneToMany로 관계 변경 후, 조인 테이블을 엔티티로 승격시켜 @ManyToOne 관계로 설정)
//    @JoinTable(
//            name = "user_authority",
//            joinColumns = {@JoinColumn(name = "id", referencedColumnName = "id"), @JoinColumn(name = "username", referencedColumnName = "username")},
//            inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "authority_name")})
//    private Set<AuthorityEntity> authorities;

    @OneToMany(mappedBy = "user")
//    @JoinColumn(name = "user_authority")
    private Set<UserAuthorityJoinEntity> authorities;

//    @ManyToMany
//    @JoinTable(name = "user_mbti",
//                joinColumns = {@JoinColumn(name = "id", referencedColumnName = "id"), @JoinColumn(name = "username", referencedColumnName = "username")},
//                inverseJoinColumns = {@JoinColumn(name = "mbti", referencedColumnName = "mbti")})
//    private Set<MBTIInfoEntity> mbtiList;

    @OneToMany(mappedBy = "user")
//    @JoinColumn(name = "user_mbti")
    private Set<UserMbtiJoinEntity> mbtiList;

    public UserDTO dtoConverter() {
        return UserDTO.builder()
                .id(this.id)
                .username(this.username)
                .password(this.password)
                .nickname(this.nickname)
                .activate(this.activate)
//                .authorities(this.authorities)
//                .mbtiInfoList(this.mbtiList)
                .build();
    }
}
