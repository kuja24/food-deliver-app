CREATE TABLE `fooddelivery`.`USERS` (
  `user_id` INT NOT NULL AUTO_INCREMENT,
 `name` VARCHAR(100) NOT NULL,
  `email` VARCHAR(100) NOT NULL,
  `password_hash` VARCHAR(100) NOT NULL,
  `phone` VARCHAR(10) NOT NULL,
  `role` VARCHAR(100) NOT NULL,
  `address_id` INT NOT NULL,
  `created_at` TIMESTAMP NOT NULL,
  PRIMARY KEY (`user_id`),
  INDEX `_idx` (`address_id` ASC) VISIBLE,
  CONSTRAINT ``
  FOREIGN KEY (`address_id`)
  REFERENCES `fooddelivery`.`ADDRESS` (`address_id`)
  ON DELETE CASCADE
  ON UPDATE NO ACTION);

CREATE TABLE IF NOT EXISTS `fooddelivery`.`ADDRESS` (
    `address_id` INT NOT NULL AUTO_INCREMENT,
    `address_line` VARCHAR(100) NOT NULL,
    `city` VARCHAR(100) NOT NULL,
    `state` VARCHAR(100) NOT NULL,
    `zip_code` INT NOT NULL,
    PRIMARY KEY (`address_id`)
);

CREATE TABLE `fooddelivery`.`RESTAURANTS` (
  `restaurant_id` INT NOT NULL AUTO_INCREMENT,
 `name` VARCHAR(100) NOT NULL,
  `address_id` INT NOT NULL,
  `owner_id` INT NOT NULL,
  `cuisine_type` VARCHAR(100) NOT NULL,
  `hours_of_operation` TIME NOT NULL,
  `created_at` TIMESTAMP NOT NULL,
  PRIMARY KEY (`restaurant_id`),
  INDEX `address_id_idx` (`address_id` ASC) VISIBLE,
  INDEX `user_id_idx` (`owner_id` ASC) VISIBLE,
 CONSTRAINT `address_id`
 FOREIGN KEY (`address_id`)
 REFERENCES `fooddelivery`.`ADDRESS` (`address_id`)
 ON DELETE CASCADE
 ON UPDATE NO ACTION,
 CONSTRAINT `user_id`
 FOREIGN KEY (`owner_id`)
 REFERENCES `fooddelivery`.`USERS` (`user_id`)
 ON DELETE CASCADE
 ON UPDATE NO ACTION);

CREATE TABLE `fooddelivery`.`RESTAURANT_RATING` (
 `rating_id` INT NOT NULL AUTO_INCREMENT,
  `restaurant_id` INT NOT NULL,
 `users_id` INT NOT NULL,
  `rating` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`rating_id`),
  INDEX `restaurant_id_idx` (`restaurant_id` ASC) VISIBLE,
  INDEX `user_id_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `restaurant_id`
  FOREIGN KEY (`restaurant_id`)
  REFERENCES `fooddelivery`.`RESTAURANTS` (`restaurant_id`)
  ON DELETE CASCADE
  ON UPDATE NO ACTION,
  CONSTRAINT `users_id`
  FOREIGN KEY (`users_id`)
  REFERENCES `fooddelivery`.`USERS` (`user_id`)
  ON DELETE CASCADE
  ON UPDATE NO ACTION);

CREATE TABLE `fooddelivery`.`MENUS` (
 `menu_id` INT NOT NULL AUTO_INCREMENT,
 `rest_id` INT NOT NULL,
 `item_name` VARCHAR(100) NOT NULL,
 `description` VARCHAR(500) NOT NULL,
 `price` DECIMAL(10, 2) NOT NULL,
 `is_Available` TINYINT NOT NULL,
 PRIMARY KEY (`menu_id`),
 INDEX `rest_id_idx` (`rest_id` ASC) VISIBLE,
 CONSTRAINT `rest_id`
  FOREIGN KEY (`rest_id`)
   REFERENCES `fooddelivery`.`RESTAURANTS` (`restaurant_id`)
ON DELETE CASCADE
 ON UPDATE NO ACTION);

CREATE TABLE `fooddelivery`.`ORDER_DETAILS` (
 `order_id` INT NOT NULL AUTO_INCREMENT,
 `menu_id` INT NOT NULL,
 `quantity` INT NOT NULL,
 `price` DECIMAL(10, 2) NOT NULL,
 PRIMARY KEY (`order_id`),
 INDEX `menu_id_idx` (`menu_id` ASC) VISIBLE,
 CONSTRAINT `menu_id`
  FOREIGN KEY (`menu_id`)
   REFERENCES `fooddelivery`.`MENUS` (`menu_id`)
ON DELETE CASCADE
 ON UPDATE NO ACTION);

CREATE TABLE `fooddelivery`.`DELIVERY` (
  `delivery_id` INT NOT NULL AUTO_INCREMENT,
  `order_id` INT NOT NULL,
  `delivery_partner_id` INT NOT NULL,
  `status` ENUM("assigned", "picked_up", "en_route", "delivered") NOT NULL,
  `created_at` TIMESTAMP NOT NULL,
  `picked_up_at` TIMESTAMP NOT NULL,
  `delivered_at` TIMESTAMP NOT NULL,
  PRIMARY KEY (`delivery_id`),
  INDEX `order_id_idx` (`order_id` ASC) VISIBLE,
  INDEX `user_id_idx` (`delivery_partner_id` ASC) VISIBLE,
 CONSTRAINT `order_id`
 FOREIGN KEY (`order_id`)
 REFERENCES `fooddelivery`.`ORDER_DETAILS` (`order_id`)
 ON DELETE CASCADE
 ON UPDATE NO ACTION,
 CONSTRAINT `user_id`
 FOREIGN KEY (`user_id`)
 REFERENCES `fooddelivery`.`USERS` (`user_id`)
 ON DELETE CASCADE
 ON UPDATE NO ACTION);

CREATE TABLE `fooddelivery`.`DELIVERY_PARTNER_DETAILS` (
  `delivery_partner_id` INT NOT NULL,
  `current_loc_lat` DECIMAL(10,8) NOT NULL,
  `current_loc_long` DECIMAL(11,8) NOT NULL,
  `is_available` BOOLEAN NOT NULL DEFAULT TRUE,
  PRIMARY KEY (`delivery_id`),
  INDEX `user_id_idx` (`delivery_partner_id` ASC) VISIBLE,
 CONSTRAINT `user_id`
 FOREIGN KEY (`user_id`)
 REFERENCES `fooddelivery`.`USERS` (`user_id`)
 ON DELETE CASCADE
 ON UPDATE NO ACTION);

CREATE TABLE `fooddelivery`.`PAYMENT` (
 `payment_id` INT NOT NULL AUTO_INCREMENT,
 `order_id` INT NOT NULL,
 `amount` DECIMAL(10, 2) NOT NULL,
 `payment_method` ENUM("credit_card", "debit_card", "upi", "cash") NOT NULL,
 `payment_status` ENUM("pending", "paid", "failed") NOT NULL,
 `paid_at` TIMESTAMP NOT NULL,
 PRIMARY KEY (`payment_id`),
 INDEX `order_id_idx` (`order_id` ASC) VISIBLE,
 CONSTRAINT `order_id`
  FOREIGN KEY (`order_id`)
   REFERENCES `fooddelivery`.`ORDER_DETAILS` (`order_id`)
ON DELETE CASCADE
 ON UPDATE NO ACTION);



