insert into users (user_id, encoded_password) values
('tiger','$2a$10$BRMZmPOOaLp5ksyMZMY8rOCphXq8xZtgcsi8svVIeSQnEVMp4LY0a'),
('sisi', '$2a$10$BRMZmPOOaLp5ksyMZMY8rOCphXq8xZtgcsi8svVIeSQnEVMp4LY0a'),
('cathy','$2a$10$BRMZmPOOaLp5ksyMZMY8rOCphXq8xZtgcsi8svVIeSQnEVMp4LY0a');

insert into authorities (permission) values
('account:read'),
('account:write'),
('ROLE_ADMIN'),
('ROLE_ACCOUNT'),
('ROLE_GROUPADMIN');

insert into users_authorities(user_id, authority_id)
select users.id, authorities.id from users, authorities
where users.user_id='tiger' and authorities.permission='ROLE_ACCOUNT';

insert into users_authorities(user_id, authority_id)
select users.id, authorities.id from users, authorities
where users.user_id='sisi' and authorities.permission='ROLE_ADMIN';

insert into users_authorities(user_id, authority_id)
select users.id, authorities.id from users, authorities
where users.user_id='sisi' and authorities.permission='account:read';

insert into users_authorities(user_id, authority_id)
select users.id, authorities.id from users, authorities
where users.user_id='sisi' and authorities.permission='account:write';

insert into users_authorities(user_id, authority_id)
select users.id, authorities.id from users, authorities
where users.user_id='cathy' and authorities.permission='ROLE_GROUPADMIN';

insert into users_authorities(user_id, authority_id)
select users.id, authorities.id from users, authorities
where users.user_id='cathy' and authorities.permission='account:read';