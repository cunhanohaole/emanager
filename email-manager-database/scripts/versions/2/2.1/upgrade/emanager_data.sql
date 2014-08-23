INSERT INTO cunha_emanager.user (username, password, active) values ('adm', '123', 1);

INSERT INTO cunha_emanager.role (role, description) values ('ROLE_ADM', 'Perfil de administrador');
INSERT INTO cunha_emanager.role (role, description) values ('ROLE_USER', 'Perfil de usuario');

INSERT INTO cunha_emanager.user_has_role values ('adm', 'ROLE_ADM');

INSERT INTO cunha_emanager.user (username, password, active) values ('marcelo', '1234', 1);
INSERT INTO cunha_emanager.user_has_role values ('marcelo', 'ROLE_ADM');

INSERT INTO cunha_emanager.sender_config (username, password, hostname, mail_from, mail_smtp_auth, mail_smtp_port)
values ('49583900035b28567726201610008da2', 'af0e435b6a7ce215f5f46b81107b7e44', 'in.mailjet.com', 'marcelo@test.com.br', 1, '587');

INSERT INTO cunha_emanager.address_group (title, user_stamp, created_timestamp, last_edited_timestamp, active)
values ('Teste', 'adm', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1);


INSERT INTO cunha_emanager.sender_config (username, password, hostname, mail_from, mail_smtp_auth, mail_smtp_port, mail_smtp_starttls_enable, )
values ('cunhanohaole@gmail.com', 'cerezo@123', 'smtp.gmail.com', 'marcelo@test.com.br', 1, '587', 1);