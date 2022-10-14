create table if not exists user_authority
(
    id             bigint auto_increment
        primary key,
    authority_name varchar(50) null,
    user_id        varchar(50) null,
    constraint FK6ktglpl5mjosa283rvken2py5
        foreign key (authority_name) references authority (authority_name),
    constraint FKpqlsjpkybgos9w2svcri7j8xy
        foreign key (user_id) references user (username)
);

