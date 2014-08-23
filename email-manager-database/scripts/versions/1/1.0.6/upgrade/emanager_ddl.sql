CREATE  TABLE IF NOT EXISTS `cunha_emanager`.`user_acess_to_group` (
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	`user_id` INT(11) NOT NULL,
	`address_group_id` INT(11) NOT NULL,
	`user_stamp` VARCHAR(45) NOT NULL,
	`created_timestamp` TIMESTAMP NOT NULL,
	`last_edited_timestamp` TIMESTAMP NOT NULL,
	PRIMARY KEY (`id`),
	INDEX `fk_email_address_group_idx` (`address_group_id` ASC),
	CONSTRAINT `fk_email_address_group`
		FOREIGN KEY (`group_id` )
		REFERENCES `cunha_emanager`.`address_group` (`id` )
			ON DELETE NO ACTION
			ON UPDATE NO ACTION,
	CONSTRAINT `fk_user`
		FOREIGN KEY (`user_id` )
		REFERENCES `cunha_emanager`.`user` (`id` )
			ON DELETE NO ACTION
			ON UPDATE NO ACTION
);