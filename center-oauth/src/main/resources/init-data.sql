insert ignore into `oauth`.`t_client`
(`id`, `client_id`, `client_secret`,
 `authorized_grant_types`, `scope`,
 `refresh_token_validity_seconds`, `access_token_validity_seconds`,
 `registered_redirect_uri`)
values ('ff80808163d8fd380163d8fd793b0005', 'client', '$2a$10$YACEb/LqIN2eZRSd2sKbuOleAsCm7PIJQ0ob73FKxGrD3dmeGUZPy',
        'password,authorization_code,refresh_token', 'app',
        604800, 7200,
        'http://localhost:4200');

insert ignore into `oauth`.`t_user`
(`id`, `username`, `password`, `enabled`, `update_at`, `create_at`, `sex`, `phone_id`, `user_id`)
values
  ('ff80808163d8fd380163d8fd788a0004', 'user', '$2a$10$aAZeACgARa/UHeNvPAhhZe3hooPCAadIL/2/ATuJodtb3luiCe8Au',
   true, now(), now(), null, null, null);

insert ignore into `oauth`.`t_role` (`id`, `name`) values ('ff80808163d8fd380163d8fd77a40003', 'USER');

insert ignore into `oauth`.`t_user_role` (`user_id`, `role_id`)
values ('ff80808163d8fd380163d8fd788a0004', 'ff80808163d8fd380163d8fd77a40003');