-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema SwapMeetDB
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `SwapMeetDB` ;

-- -----------------------------------------------------
-- Schema SwapMeetDB
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `SwapMeetDB` DEFAULT CHARACTER SET utf8 ;
USE `SwapMeetDB` ;

-- -----------------------------------------------------
-- Table `community`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `community` ;

CREATE TABLE IF NOT EXISTS `community` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(100) NOT NULL,
  `owner_id` INT NOT NULL,
  `description` TEXT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC),
  INDEX `id_idx` (`owner_id` ASC),
  CONSTRAINT `user_id_fk`
    FOREIGN KEY (`owner_id`)
    REFERENCES `user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `user` ;

CREATE TABLE IF NOT EXISTS `user` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(45) NOT NULL,
  `last_name` VARCHAR(45) NOT NULL,
  `username` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `admin` TINYINT NULL,
  `home_community_id` INT NULL,
  PRIMARY KEY (`id`),
  INDEX `community_home_id_fk_idx` (`home_community_id` ASC),
  CONSTRAINT `community_home_id_fk`
    FOREIGN KEY (`home_community_id`)
    REFERENCES `community` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `category`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `category` ;

CREATE TABLE IF NOT EXISTS `category` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `type` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `item`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `item` ;

CREATE TABLE IF NOT EXISTS `item` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `content` TEXT NOT NULL,
  `post_time` TIMESTAMP NOT NULL,
  `category_id` INT NULL,
  `price` DECIMAL NULL,
  `title` TEXT NOT NULL,
  `community_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `id_idx` (`user_id` ASC),
  INDEX `category_id_idx` (`category_id` ASC),
  INDEX `community_id_idx` (`community_id` ASC),
  CONSTRAINT `user_item_fk`
    FOREIGN KEY (`user_id`)
    REFERENCES `user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `category_item_fk`
    FOREIGN KEY (`category_id`)
    REFERENCES `category` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `community_item_fk`
    FOREIGN KEY (`community_id`)
    REFERENCES `community` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `post`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `post` ;

CREATE TABLE IF NOT EXISTS `post` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `item_id` INT NOT NULL,
  `content` TEXT NOT NULL,
  `post_time` TIMESTAMP NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `id_idx` (`item_id` ASC),
  INDEX `id_idx1` (`user_id` ASC),
  CONSTRAINT `post_item_fk`
    FOREIGN KEY (`item_id`)
    REFERENCES `item` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `post_user_fk`
    FOREIGN KEY (`user_id`)
    REFERENCES `user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `user_community`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `user_community` ;

CREATE TABLE IF NOT EXISTS `user_community` (
  `user_id` INT NOT NULL,
  `community_id` INT NOT NULL,
  PRIMARY KEY (`user_id`, `community_id`),
  INDEX `fk_user_has_community_community1_idx` (`community_id` ASC),
  INDEX `fk_user_has_community_user1_idx` (`user_id` ASC),
  CONSTRAINT `fk_user_has_community_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_has_community_community1`
    FOREIGN KEY (`community_id`)
    REFERENCES `community` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `user_rating`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `user_rating` ;

CREATE TABLE IF NOT EXISTS `user_rating` (
  `community_id` INT NOT NULL,
  `user_id` INT NOT NULL,
  `rating` INT NOT NULL,
  INDEX `user_rating_fk_idx` (`user_id` ASC),
  CONSTRAINT `user_rating_fk`
    FOREIGN KEY (`user_id`)
    REFERENCES `user_community` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `community_rating_fk`
    FOREIGN KEY (`community_id`)
    REFERENCES `user_community` (`community_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

SET SQL_MODE = '';
GRANT USAGE ON *.* TO blossom@localhost;
 DROP USER blossom@localhost;
SET SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';
CREATE USER 'blossom'@'localhost' IDENTIFIED BY 'blossom';

GRANT SELECT, INSERT, TRIGGER, UPDATE, DELETE ON TABLE * TO 'blossom'@'localhost';

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;


-- -----------------------------------------------------
-- Data for table `user`
-- -----------------------------------------------------
START TRANSACTION;
USE `SwapMeetDB`;
INSERT INTO `user` (`id`, `first_name`, `last_name`, `username`, `password`, `admin`, `home_community_id`) VALUES (1, 'Robert', 'Smith', 'bobsmith', 'bobbob', TRUE, NULL);
INSERT INTO `user` (`id`, `first_name`, `last_name`, `username`, `password`, `admin`, `home_community_id`) VALUES (2, 'Hunter', 'Kiritsis', 'HunterK', 'password123', TRUE, NULL);
INSERT INTO `user` (`id`, `first_name`, `last_name`, `username`, `password`, `admin`) VALUES (4, 'John', 'Jackson', 'john', 'jonjon', FALSE);
INSERT INTO `user` (`id`, `first_name`, `last_name`, `username`, `password`, `admin`) VALUES (3, 'Shaquille', 'O\'Neal', 'shaq', 'bigshaq', FALSE);

COMMIT;


-- -----------------------------------------------------
-- Data for table `community`
-- -----------------------------------------------------
START TRANSACTION;
USE `SwapMeetDB`;
INSERT INTO `community` (`id`, `name`, `owner_id`) VALUES (1, 'Gobots', 1);
INSERT INTO `community` (`id`, `name`, `owner_id`, `description`) VALUES (2, 'SD13', 2, 'The best class to ever drink from the Skill Distillery firehose.');
INSERT INTO `community` (`id`, `name`, `owner_id`, `description`) VALUES (3, 'Transformers', 4, NULL);


COMMIT;



-- -----------------------------------------------------
-- Data for table `category`
-- -----------------------------------------------------
START TRANSACTION;
USE `SwapMeetDB`;
INSERT INTO `category` (`id`, `type`) VALUES (1, 'Vehicles');
INSERT INTO `category` (`id`, `type`) VALUES (2, 'Toys and Games');
INSERT INTO `category` (`id`, `type`) VALUES (3, 'Sports,Exercise, Outdoors Prodcuts');
INSERT INTO `category` (`id`, `type`) VALUES (4, ' Furniture and Household Goods');
INSERT INTO `category` (`id`, `type`) VALUES (5, 'Tickets to Events');
INSERT INTO `category` (`id`, `type`) VALUES (6, 'Tools and Equipment');
INSERT INTO `category` (`id`, `type`) VALUES (7, 'Media - Music, Movies, Books');
INSERT INTO `category` (`id`, `type`) VALUES (8, 'Clothes and Footwear');
INSERT INTO `category` (`id`, `type`) VALUES (9, 'Electronics');
INSERT INTO `category` (`id`, `type`) VALUES (10, 'Other');

COMMIT;


-- -----------------------------------------------------
-- Data for table `item`
-- -----------------------------------------------------
START TRANSACTION;
USE `SwapMeetDB`;
INSERT INTO `item` (`id`, `user_id`, `content`, `post_time`, `category_id`, `price`, `title`, `community_id`) VALUES (1, 1, 'A chair', '1970-01-01 00:00:01', 1, 1, 'Chair', 1);
INSERT INTO `item` (`id`, `user_id`, `content`, `post_time`, `category_id`, `price`, `title`, `community_id`) VALUES (2, 1, 'A squid', '1970-01-01 00:00:01', 2, 1, 'Squid', 1);
INSERT INTO `item` (`id`, `user_id`, `content`, `post_time`, `category_id`, `price`, `title`, `community_id`) VALUES (3, 1, 'A bowl', '1970-01-01 00:00:01', 2, 1, 'Bowl', 1);
INSERT INTO `item` (`id`, `user_id`, `content`, `post_time`, `category_id`, `price`, `title`, `community_id`) VALUES (4, 1, 'A Car', '1970-01-01 00:00:01', 3, 1, 'Car', 1);
INSERT INTO `item` (`id`, `user_id`, `content`, `post_time`, `category_id`, `price`, `title`, `community_id`) VALUES (5, 1, 'A bird', '1970-01-01 00:00:01', 3, 1, 'Bird', 1);
INSERT INTO `item` (`id`, `user_id`, `content`, `post_time`, `category_id`, `price`, `title`, `community_id`) VALUES (6, 2, 'Coffee Beans', '2017-12-01', 2, NULL, 'Free Coffee', 2);

COMMIT;
-- -----------------------------------------------------
-- Data for table `user_community`
-- -----------------------------------------------------
START TRANSACTION;
USE `SwapMeetDB`;
INSERT INTO `user_community` (`user_id`, `community_id`) VALUES (1, 1);
INSERT INTO `user_community` (`user_id`, `community_id`) VALUES (2, 2);
INSERT INTO `user_community` (`user_id`, `community_id`) VALUES (2, 1);
INSERT INTO `user_community` (`user_id`, `community_id`) VALUES (3, 2);

COMMIT;
