create table if not exists user_mbti
(
    id              bigint auto_increment
        primary key,
    user_id         varchar(50) null,
    number_of_times int         null,
    code            varchar(20) null,
    reg_date        datetime(6) null,
    recent_reg_date datetime(6) null,
    constraint FKcsskkvho4q383jc1a46d9gngv
        foreign key (user_id) references user (username),
    constraint FKt8pyyigh86yn53q32bghkhkmx
        foreign key (code) references mbti_info (code)
);

