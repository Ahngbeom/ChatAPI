SHOW TABLES;

SELECT * FROM USER LEFT OUTER JOIN user_mbti um on user.username = um.username;

SELECT * FROM authority;

SELECT * FROM user_authority;

SELECT * from mbti_info;