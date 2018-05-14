insert ignore into t_oauth_client_details (
  id, client_id, client_secret, authorized_grant_types, access_token_validity_seconds, refresh_token_validity_seconds, scope)
values (
  '1', 'client', '$2a$10$rbqv2MhnGYluniPriL0WX.vZ1wIEsM1sANZnsoauKDsF1zdpM54bG',
  'password,authorization_code,refresh_token,implicit,client_credentials'
  , 3600, 7200, 'app'
);

insert ignore into t_user (id, enabled, password, username)
values ('1', true, '$2a$10$XSzHHxfZZH/ILVDoJtkgEeAZBS/.ivmn2.MLKh6Tb1DDZcGLEiEX2', 'user');
insert ignore into t_user (id, enabled, password, username)
values ('2', true, '$2a$10$XSzHHxfZZH/ILVDoJtkgEeAZBS/.ivmn2.MLKh6Tb1DDZcGLEiEX2', 'admin');

insert into t_role (id, name) values ('1', 'USER');
insert into t_role (id, name) values ('2', 'ADMIN');

insert into t_user_role (user_id, role_id) values (1, 1);
insert into t_user_role (user_id, role_id) values (2, 1);
insert into t_user_role (user_id, role_id) values (2, 2);