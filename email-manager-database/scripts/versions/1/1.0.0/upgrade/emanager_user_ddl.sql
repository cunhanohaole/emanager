CREATE  TABLE IF NOT EXISTS `cunha_emanager`.`role` (
	`role` VARCHAR(45) NOT NULL ,
	`description` VARCHAR(100) NOT NULL ,
	PRIMARY KEY (`role`) 
);

CREATE  TABLE IF NOT EXISTS `cunha_emanager`.`user` (
	`username` VARCHAR(45) NOT NULL ,
	`password` VARCHAR(45) NOT NULL ,
	`active` TINYINT(1) NOT NULL ,
	PRIMARY KEY (`username`) 
);

CREATE  TABLE IF NOT EXISTS `cunha_emanager`.`user_has_role` (
	`username` VARCHAR(45) NOT NULL ,
	`role` VARCHAR(45) NOT NULL ,
	PRIMARY KEY (`username`, `role`) ,
	INDEX `fk_user_has_role_role1_idx` (`role` ASC) ,
	INDEX `fk_user_has_role_user1_idx` (`username` ASC) ,
	CONSTRAINT `fk_user_has_role_user1`
		FOREIGN KEY (`username` )
			REFERENCES `cunha_emanager`.`user` (`username` )
				ON DELETE NO ACTION
				ON UPDATE NO ACTION,
	CONSTRAINT `fk_user_has_role_role1`
		FOREIGN KEY (`role` )
			REFERENCES `cunha_emanager`.`role` (`role` )
				ON DELETE NO ACTION
				ON UPDATE NO ACTION
);