ALTER TABLE `cunha_emanager`.`email`
    MODIFY COLUMN 
        `email_body` LONGBLOB NULL DEFAULT NULL;
       
ALTER TABLE `cunha_emanager`.`attachment`
    MODIFY COLUMN 
        `file_name` VARCHAR(1000) NOT NULL;