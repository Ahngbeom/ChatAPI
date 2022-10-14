create table if not exists chat_mbti
(
    id               bigint auto_increment
        primary key,
    chat_room_name   varchar(100) null,
    permit_mbti_code varchar(20)  null,
    constraint FKj8pgy9227v7ypcvnmrmselea9
        foreign key (chat_room_name) references chat_room (room_name),
    constraint FKqve7g7xoyx3qnu8jc1kod9a7v
        foreign key (permit_mbti_code) references mbti_info (code)
);

