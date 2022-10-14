create table if not exists chat_log
(
    id            bigint auto_increment
        primary key,
    update_date   datetime(6)   null,
    from_username varchar(50)   null,
    message       varchar(1000) null,
    chat_room_id  varchar(100)  null,
    reg_date      datetime(6)   null,
    type          varchar(10)   null,
    constraint FKby9tv7p49do0vne9rqlwhndo4
        foreign key (chat_room_id) references chat_room (room_name)
);

