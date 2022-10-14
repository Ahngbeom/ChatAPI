create table if not exists chat_room
(
    room_name        varchar(100) not null
        primary key,
    create_date      datetime(6)  null,
    description      varchar(200) null,
    founder_username varchar(50)  null,
    constraint FK48m2wrgf9830v03eh30pdcfh6
        foreign key (founder_username) references user (username)
);

