package com.example.chatapi.Entity.MBTI;

import com.example.chatapi.Entity.User.UserEntity;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Calendar;

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
    @JoinColumn(name = "code")
    private MBTIInfoEntity mbti;

    @Column(name = "number_of_times")
    private int numberOfTimes;

    @Column(name = "reg_date")
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar regDate;

    public void increaseNumberOfTimes() {
        this.numberOfTimes += 1;
    }
}
