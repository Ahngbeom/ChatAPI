create table if not exists mbti_info
(
    code         varchar(20)   not null
        primary key,
    introduction varchar(1000) null,
    personality  varchar(50)   null,
    img_src      varchar(255)  null
);

