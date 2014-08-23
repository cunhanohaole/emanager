CREATE  TABLE IF NOT EXISTS `cunha_emanager`.`sender_config` (
	`id` INT(11) NOT NULL AUTO_INCREMENT ,
	`username` VARCHAR(250) NOT NULL ,
	`password` VARCHAR(250) NOT NULL ,
	`hostname` VARCHAR(250) NOT NULL ,
	`email_from` VARCHAR(45) NOT NULL ,
	`mail_smtp_auth` TINYINT(1) NULL DEFAULT NULL ,
	`mail_smtp_port` VARCHAR(10) NULL DEFAULT NULL ,
	`mail_smtp_socket_factory_fallback` VARCHAR(250) NULL DEFAULT NULL ,
	`mail_smtp_starttls_enable` TINYINT(1) NULL DEFAULT NULL ,
	`mail_smtp_socket_factory_port` VARCHAR(10) NULL DEFAULT NULL ,
	`mail_smtp_socket_factory_class` VARCHAR(250) NULL DEFAULT NULL ,
	PRIMARY KEY (`id`) 
);

ALTER TABLE `cunha_emanager`.`user` 
ADD COLUMN `sender_config_id` INT(11)
AFTER `active` , 
ADD CONSTRAINT `fk_user_sender_config1`
FOREIGN KEY (`sender_config_id` )
REFERENCES `cunha_emanager`.`sender_config` (`id` )
ON DELETE NO ACTION
ON UPDATE NO ACTION, 
ADD INDEX `fk_user_sender_config1_idx` (`sender_config_id` ASC);