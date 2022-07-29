package com.example.chatapi.Entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "AUTHORITY")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthorityEntity {

    @Id
    @Column(name = "authority_name", length = 50)
    private String authorityName;

}
