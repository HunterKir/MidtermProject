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
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `community`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `community` ;

CREATE TABLE IF NOT EXISTS `community` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(100) NOT NULL,
  `owner_id` INT NOT NULL,
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
-- Table `user_community`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `user_community` ;

CREATE TABLE IF NOT EXISTS `user_community` (
  `user_id` INT NOT NULL,
  `community_id` INT NOT NULL,
  INDEX `id_idx` (`user_id` ASC),
  INDEX `id_idx1` (`community_id` ASC),
  CONSTRAINT `join_user_id`
    FOREIGN KEY (`user_id`)
    REFERENCES `user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `join_community_id`
    FOREIGN KEY (`community_id`)
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
-- Table `user_rating`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `user_rating` ;

CREATE TABLE IF NOT EXISTS `user_rating` (
  `community_id` INT NOT NULL,
  `user_id` INT NOT NULL,
  `rating` INT NOT NULL,
  INDEX `community_id_idx` (`community_id` ASC),
  INDEX `user_id_idx` (`user_id` ASC),
  CONSTRAINT `rating_community_id`
    FOREIGN KEY (`community_id`)
    REFERENCES `user_community` (`community_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `rating_user_id`
    FOREIGN KEY (`user_id`)
    REFERENCES `user_community` (`user_id`)
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
