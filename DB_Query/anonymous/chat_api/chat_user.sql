create table if not exists chat_user
(
    id             bigint auto_increment
        primary key,
    chat_room_name varchar(50) null,
    user_name      varchar(50) null,
    constraint FK3744i2fa9h6r4hn25vvpr66kn
        foreign key (user_name) references user (username),
    constraint FKias472iay9bj7167rnfv3jbas
        foreign key (chat_room_name) references chat_room (room_name)
);

