-- -----------------------------------------------------
-- Table `cunha_emanager`.`email_address`
-- -----------------------------------------------------
ALTER TABLE `cunha_emanager`.`email_address` 
ADD COLUMN `invalid` TINYINT(4) NOT NULL DEFAULT 0 AFTER `user_stamp`;

ALTER TABLE `cunha_emanager`.`email_address` 
ADD COLUMN `invalid_reason` VARCHAR(250) NULL DEFAULT NULL AFTER `invalid`;

-- -----------------------------------------------------
-- Table `cunha_emanager`.`user_has_address_group`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cunha_emanager`.`user_has_address_group` (
  `user_username` VARCHAR(45) NOT NULL,
  `address_group_id` INT(11) NOT NULL,
  PRIMARY KEY (`user_username`, `address_group_id`),
  INDEX `fk_user_has_address_group_address_group1_idx` (`address_group_id` ASC),
  INDEX `fk_user_has_address_group_user1_idx` (`user_username` ASC),
  CONSTRAINT `fk_user_has_address_group_user1`
    FOREIGN KEY (`user_username`)
    REFERENCES `cunha_emanager`.`user` (`username`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_has_address_group_address_group1`
    FOREIGN KEY (`address_group_id`)
    REFERENCES `cunha_emanager`.`address_group` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;

-- -----------------------------------------------------
-- Table `cunha_emanager`.`signature`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cunha_emanager`.`signature` (
  `id_signature` INT(11) NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(45) NOT NULL,
  `title` VARCHAR(45) NOT NULL,
  `html_signature` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id_signature`),
  INDEX `fk_signature_user1_idx` (`username` ASC),
  CONSTRAINT `fk_signature_user1`
    FOREIGN KEY (`username`)
    REFERENCES `cunha_emanager`.`user` (`username`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;