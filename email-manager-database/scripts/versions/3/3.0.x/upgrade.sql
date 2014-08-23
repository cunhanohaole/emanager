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

-- Add FK to SenderConfig on Email so that we can identify who sent the email
ALTER TABLE `cunha_emanager`.`email`
ADD COLUMN `sender_config_id` INT(11) NOT NULL AFTER `status`,
ADD INDEX `fk_email_sender_config1_idx` (`sender_config_id` ASC);

ALTER TABLE `cunha_emanager`.`email`
ADD CONSTRAINT `fk_email_sender_config1`
  FOREIGN KEY (`sender_config_id`)
  REFERENCES `cunha_emanager`.`sender_config` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;