-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema cinema_schema
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema cinema_schema
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `cinema_schema` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `cinema_schema` ;

-- -----------------------------------------------------
-- Table `cinema_schema`.`movies`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cinema_schema`.`movies` (
  `movieid` INT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(100) NOT NULL,
  `director` VARCHAR(35) NOT NULL,
  `genre` SET('Action', 'Adventure', 'Sci-Fi', 'Drama', 'Comedy', 'Family', 'Animation', 'Fantasy', 'History', 'Documentary', 'Horror', 'Sports', 'Foreign', 'Biography') NOT NULL,
  `releasedate` DATE NOT NULL,
  `movielength` SMALLINT NOT NULL,
  `synopsis` TEXT NOT NULL,
  `filmrating` ENUM('G', 'PG', 'PG-13', 'R', 'NC-17', 'NR') NOT NULL,
  PRIMARY KEY (`movieid`))
ENGINE = InnoDB
AUTO_INCREMENT = 12
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `cinema_schema`.`theaters`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cinema_schema`.`theaters` (
  `theaterid` INT NOT NULL AUTO_INCREMENT,
  `numrow` INT NOT NULL,
  `numcol` INT NOT NULL,
  `capacity` INT GENERATED ALWAYS AS ((`numrow` * `numcol`)) VIRTUAL,
  PRIMARY KEY (`theaterid`))
ENGINE = InnoDB
AUTO_INCREMENT = 8
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `cinema_schema`.`showtimes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cinema_schema`.`showtimes` (
  `time` TIME NOT NULL,
  `theaterid` INT NOT NULL,
  `movieid` INT NOT NULL,
  `date` DATE NOT NULL,
  PRIMARY KEY (`time`, `theaterid`, `date`),
  INDEX `fk_movie` (`movieid` ASC) VISIBLE,
  INDEX `fk_theater` (`theaterid` ASC) VISIBLE,
  CONSTRAINT `fk_movie`
    FOREIGN KEY (`movieid`)
    REFERENCES `cinema_schema`.`movies` (`movieid`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_theater`
    FOREIGN KEY (`theaterid`)
    REFERENCES `cinema_schema`.`theaters` (`theaterid`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
