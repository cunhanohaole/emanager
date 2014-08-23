-- -----------------------------------------------------
-- Table `cunha_emanager`.`address_group`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cunha_emanager`.`address_group` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(200) NOT NULL,
  `user_stamp` VARCHAR(45) NOT NULL,
  `created_timestamp` TIMESTAMP NOT NULL DEFAULT 0,
  `last_edited_timestamp` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `active` TINYINT(4) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 5
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `cunha_emanager`.`email`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cunha_emanager`.`email` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `subject` VARCHAR(250) NOT NULL,
  `group_id` INT(11) NOT NULL,
  `email_body` LONGBLOB NULL DEFAULT NULL,
  `additional_contacts` VARCHAR(2000) NULL DEFAULT NULL,
  `created_timestamp` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `user_stamp` VARCHAR(45) NOT NULL,
  `success` TINYINT(1) NOT NULL,
  `status` VARCHAR(10) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_email_group1_idx` (`group_id` ASC),
  CONSTRAINT `fk_email_group1`
    FOREIGN KEY (`group_id`)
    REFERENCES `cunha_emanager`.`address_group` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 18
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `cunha_emanager`.`attachment`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cunha_emanager`.`attachment` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `email_id` INT(11) NOT NULL,
  `file_name` VARCHAR(1000) NOT NULL,
  `created_timestamp` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `user_stamp` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_attachment_email1_idx` (`email_id` ASC),
  CONSTRAINT `fk_attachment_email1`
    FOREIGN KEY (`email_id`)
    REFERENCES `cunha_emanager`.`email` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 5
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `cunha_emanager`.`email_address`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cunha_emanager`.`email_address` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(250) NOT NULL,
  `address` VARCHAR(250) NOT NULL,
  `group_id` INT(11) NOT NULL,
  `created_timestamp` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `user_stamp` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_em_email_address_em_group_idx` (`group_id` ASC),
  CONSTRAINT `fk_em_email_address_em_group`
    FOREIGN KEY (`group_id`)
    REFERENCES `cunha_emanager`.`address_group` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 357
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `cunha_emanager`.`role`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cunha_emanager`.`role` (
  `role` VARCHAR(45) NOT NULL,
  `description` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`role`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `cunha_emanager`.`sender_config`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cunha_emanager`.`sender_config` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(250) NOT NULL,
  `password` VARCHAR(250) NOT NULL,
  `hostname` VARCHAR(250) NOT NULL,
  `mail_from` VARCHAR(45) NOT NULL,
  `mail_smtp_auth` TINYINT(1) NULL DEFAULT NULL,
  `mail_smtp_port` VARCHAR(10) NULL DEFAULT NULL,
  `mail_smtp_socket_factory_fallback` VARCHAR(250) NULL DEFAULT NULL,
  `mail_smtp_starttls_enable` TINYINT(1) NULL DEFAULT NULL,
  `mail_smtp_socket_factory_port` VARCHAR(10) NULL DEFAULT NULL,
  `mail_smtp_socket_factory_class` VARCHAR(250) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `cunha_emanager`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cunha_emanager`.`user` (
  `username` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `active` TINYINT(1) NOT NULL,
  `sender_config_id` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`username`),
  INDEX `fk_user_sender_config1_idx` (`sender_config_id` ASC),
  CONSTRAINT `fk_user_sender_config1`
    FOREIGN KEY (`sender_config_id`)
    REFERENCES `cunha_emanager`.`sender_config` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `cunha_emanager`.`user_has_role`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cunha_emanager`.`user_has_role` (
  `username` VARCHAR(45) NOT NULL,
  `role` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`username`, `role`),
  INDEX `fk_user_has_role_role1_idx` (`role` ASC),
  INDEX `fk_user_has_role_user1_idx` (`username` ASC),
  CONSTRAINT `fk_user_has_role_user1`
    FOREIGN KEY (`username`)
    REFERENCES `cunha_emanager`.`user` (`username`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_has_role_role1`
    FOREIGN KEY (`role`)
    REFERENCES `cunha_emanager`.`role` (`role`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;