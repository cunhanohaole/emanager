-- Drops column sender_config from user
ALTER TABLE `cunha_emanager`.`user`
DROP FOREIGN KEY `fk_user_sender_config1`

ALTER TABLE `cunha_emanager`.`user`
DROP COLUMN `sender_config_id`,
DROP INDEX `fk_user_sender_config1_idx`

--Adds FK to user.username in sender_config
ALTER TABLE `cunha_emanager`.`sender_config`
ADD COLUMN `user_username` VARCHAR(45) NOT NULL AFTER `id`,
ADD INDEX `fk_sender_config_user1_idx` (`user_username` ASC);

ALTER TABLE `cunha_emanager`.`sender_config`
ADD CONSTRAINT `fk_sender_config_user1`
  FOREIGN KEY (`user_username`)
  REFERENCES `cunha_emanager`.`user` (`username`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;