CREATE  TABLE IF NOT EXISTS `cunha_emanager`.`address_group` (
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	`title` VARCHAR(200) NOT NULL,
	`user_stamp` VARCHAR(45) NOT NULL,
	`created_timestamp` TIMESTAMP NOT NULL,
	`last_edited_timestamp` TIMESTAMP NOT NULL,
	`active` TINYINT(1) NOT NULL,
	PRIMARY KEY (`id`) 
);

CREATE  TABLE IF NOT EXISTS `cunha_emanager`.`email_address` (
	`id` INT(11) NOT NULL AUTO_INCREMENT ,
	`name` VARCHAR(250) NOT NULL ,
	`address` VARCHAR(250) NOT NULL ,
	`group_id` INT(11) NOT NULL ,
	`created_timestamp` TIMESTAMP NOT NULL,
	`user_stamp` VARCHAR(45) NOT NULL,
	PRIMARY KEY (`id`) ,
	INDEX `fk_em_email_address_em_group_idx` (`group_id` ASC) ,
	CONSTRAINT `fk_em_email_address_em_group`
		FOREIGN KEY (`group_id` )
		REFERENCES `cunha_emanager`.`address_group` (`id` )
			ON DELETE NO ACTION
			ON UPDATE NO ACTION
);

CREATE  TABLE IF NOT EXISTS `cunha_emanager`.`email` (
	`id` INT(11) NOT NULL AUTO_INCREMENT ,
	`subject` VARCHAR(250) NOT NULL ,
	`group_id` INT(11) NOT NULL ,
	`email_body` BLOB NULL DEFAULT NULL ,
	`additional_contacts` VARCHAR(2000) NULL DEFAULT NULL ,
	`created_timestamp` TIMESTAMP NOT NULL ,
	`user_stamp` VARCHAR(45) NOT NULL ,
	`success` TINYINT(1) NOT NULL ,
	PRIMARY KEY (`id`) ,
	INDEX `fk_email_group1_idx` (`group_id` ASC) ,
	CONSTRAINT `fk_email_group1`
		FOREIGN KEY (`group_id` )
		REFERENCES `cunha_emanager`.`address_group` (`id` )
			ON DELETE NO ACTION
			ON UPDATE NO ACTION
);

CREATE  TABLE IF NOT EXISTS `cunha_emanager`.`attachment` (
	`id` INT(11) NOT NULL AUTO_INCREMENT ,
	`email_id` INT(11) NOT NULL ,
	`file_name` VARCHAR(45) NOT NULL ,
	`created_timestamp` TIMESTAMP NOT NULL ,
	`user_stamp` VARCHAR(45) NOT NULL ,
	PRIMARY KEY (`id`) ,
	INDEX `fk_attachment_email1_idx` (`email_id` ASC) ,
	CONSTRAINT `fk_attachment_email1`
		FOREIGN KEY (`email_id` )
		REFERENCES `cunha_emanager`.`email` (`id` )
			ON DELETE NO ACTION
			ON UPDATE NO ACTION
);

