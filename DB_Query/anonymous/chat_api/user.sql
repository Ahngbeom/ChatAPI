create table if not exists user
(
    activate    bit          null,
    nickname    varchar(50)  null,
    password    varchar(100) null,
    username    varchar(50)  not null
        primary key,
    reg_date    datetime(6)  null,
    update_date datetime(6)  null
);

