INSERT INTO cunha_emanager.user (username, password, active) values ('adm', '123', 1);

INSERT INTO cunha_emanager.role (role, description) values ('ROLE_ADM', 'Perfil de administrador');
INSERT INTO cunha_emanager.role (role, description) values ('ROLE_USER', 'Perfil de usuario');

INSERT INTO cunha_emanager.user_has_role values ('adm', 'ROLE_ADM');