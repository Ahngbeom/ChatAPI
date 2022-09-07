SELECT * FROM authority;

SELECT * FROM user_authority;

SELECT * from mbti_info;


# 3 Tables Join - USER, USER_AUTHORITY, USER_MBTI
select user.id, ua.authority_name, um.code
from user right join user_authority ua on user.id = ua.user_id right join user_mbti um on user.id = um.user_id
where user.id = 31;

select user.id, username, authority_name
from user right outer join user_authority ua on user.id = ua.user_id
order by field(authority_name, 'ROLE_MANAGER', 'ROLE_ADMIN') desc , ua.id;
