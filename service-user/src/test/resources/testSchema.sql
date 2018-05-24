create table t_oauth_client_details
(
  id                             varchar(36)  not null
    primary key,
  access_token_validity_seconds  int          not null,
  additional_information         varchar(255) null,
  authorities                    varchar(255) null,
  authorized_grant_types         varchar(255) not null,
  auto_approve_scope             varchar(255) null,
  client_id                      varchar(255) not null,
  client_secret                  varchar(255) not null,
  refresh_token_validity_seconds int          not null,
  registered_redirect_uri        varchar(255) null,
  resource_ids                   varchar(255) null,
  scope                          varchar(255) null
)
  engine = InnoDB;

create table t_role
(
  id   varchar(36)  not null
    primary key,
  name varchar(255) not null
)
  engine = InnoDB;

create table t_user
(
  id       varchar(36)  not null
    primary key,
  enabled  bit          null,
  password varchar(255) not null,
  username varchar(255) not null
)
  engine = InnoDB;

create table t_user_role
(
  user_id varchar(36) not null,
  role_id varchar(36) not null
)
  engine = InnoDB;

create index FKa9c8iiy6ut0gnx491fqx4pxam
  on t_user_role (role_id);

create index FKq5un6x7ecoef5w1n39cop66kl
  on t_user_role (user_id);