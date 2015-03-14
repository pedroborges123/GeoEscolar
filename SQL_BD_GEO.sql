-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema geoescolar
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema geoescolar
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `geoescolar` DEFAULT CHARACTER SET utf8 ;
USE `geoescolar` ;

-- -----------------------------------------------------
-- Table `geoescolar`.`adm`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `geoescolar`.`adm` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(45) NULL DEFAULT NULL,
  `email` VARCHAR(45) NOT NULL,
  `senha` VARCHAR(45) NOT NULL,
  `ultimoLogin` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC))
ENGINE = InnoDB
AUTO_INCREMENT = 17
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `geoescolar`.`motorista`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `geoescolar`.`motorista` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(45) NULL DEFAULT NULL,
  `telefone` VARCHAR(45) NULL DEFAULT NULL,
  `email` VARCHAR(45) NOT NULL,
  `senha` VARCHAR(45) NOT NULL,
  `carteiraMotorista` VARCHAR(45) NULL DEFAULT NULL,
  `codigoMotorista` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC),
  UNIQUE INDEX `carteiraMotorista_UNIQUE` (`carteiraMotorista` ASC),
  UNIQUE INDEX `codigoMotorista_UNIQUE` (`codigoMotorista` ASC))
ENGINE = InnoDB
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `geoescolar`.`transporteescolar`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `geoescolar`.`transporteescolar` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `placa` VARCHAR(10) NOT NULL,
  `capacidade` INT(11) NOT NULL,
  `registro` VARCHAR(45) NOT NULL,
  `marca` VARCHAR(45) NOT NULL,
  `idMotorista` INT(11) NOT NULL,
  `codigoVan` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `placa_UNIQUE` (`placa` ASC),
  UNIQUE INDEX `registro_UNIQUE` (`registro` ASC),
  UNIQUE INDEX `codigoVan_UNIQUE` (`codigoVan` ASC),
  INDEX `id_motorista_idx` (`idMotorista` ASC),
  CONSTRAINT `id_motorista`
    FOREIGN KEY (`idMotorista`)
    REFERENCES `geoescolar`.`motorista` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `geoescolar`.`crianca`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `geoescolar`.`crianca` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(45) NOT NULL,
  `idade` VARCHAR(45) NOT NULL,
  `sexo` CHAR(1) NOT NULL,
  `email` VARCHAR(20) NULL DEFAULT NULL,
  `telefone` CHAR(20) NULL DEFAULT NULL,
  `transporte` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  INDEX `transporte_idx` (`transporte` ASC),
  CONSTRAINT `transporte`
    FOREIGN KEY (`transporte`)
    REFERENCES `geoescolar`.`transporteescolar` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 5
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `geoescolar`.`localizacao`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `geoescolar`.`localizacao` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `longitude` VARCHAR(45) NOT NULL,
  `latitude` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC))
ENGINE = InnoDB
AUTO_INCREMENT = 17
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `geoescolar`.`endereco`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `geoescolar`.`endereco` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `tipoEndereco` VARCHAR(45) NULL DEFAULT NULL,
  `CEP` VARCHAR(45) NOT NULL,
  `logradouro` VARCHAR(45) NOT NULL,
  `numero` INT(11) NULL DEFAULT NULL,
  `complemento` VARCHAR(100) NULL DEFAULT NULL,
  `localizacao` INT(11) NULL DEFAULT NULL,
  `bairro` VARCHAR(45) NULL DEFAULT NULL,
  `cidade` VARCHAR(45) NULL DEFAULT NULL,
  `uf` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  INDEX `localizacao_idx` (`localizacao` ASC),
  CONSTRAINT `localizacao`
    FOREIGN KEY (`localizacao`)
    REFERENCES `geoescolar`.`localizacao` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 9
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `geoescolar`.`localidade`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `geoescolar`.`localidade` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `tipoLocal` VARCHAR(45) NOT NULL,
  `horaChegada` VARCHAR(10) NULL DEFAULT NULL,
  `horaSaida` VARCHAR(10) NULL DEFAULT NULL,
  `diasUtilizacao` VARCHAR(45) NULL DEFAULT NULL,
  `endereco` INT(11) NULL DEFAULT NULL,
  `descricao` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `endereco_idx` (`endereco` ASC),
  CONSTRAINT `endereco`
    FOREIGN KEY (`endereco`)
    REFERENCES `geoescolar`.`endereco` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 12
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `geoescolar`.`crianca_localidade`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `geoescolar`.`crianca_localidade` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `crianca` INT(11) NOT NULL,
  `localidade` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `crianca_idx` (`crianca` ASC),
  INDEX `local_idx` (`localidade` ASC),
  CONSTRAINT `crianca_foreign_key`
    FOREIGN KEY (`crianca`)
    REFERENCES `geoescolar`.`crianca` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `local`
    FOREIGN KEY (`localidade`)
    REFERENCES `geoescolar`.`localidade` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 9
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `geoescolar`.`faleconosco`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `geoescolar`.`faleconosco` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(45) NOT NULL,
  `assunto` VARCHAR(45) NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  `texto` TEXT NOT NULL,
  `resposta` TEXT NULL DEFAULT NULL,
  `responsavel` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  INDEX `responsavel_idx` (`responsavel` ASC),
  CONSTRAINT `responsavel`
    FOREIGN KEY (`responsavel`)
    REFERENCES `geoescolar`.`adm` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `geoescolar`.`itinerario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `geoescolar`.`itinerario` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `turno` VARCHAR(45) NOT NULL,
  `horaInicio` VARCHAR(8) NULL DEFAULT NULL,
  `horaFim` VARCHAR(8) NULL DEFAULT NULL,
  `transporteescolar` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `transporteFK_idx` (`transporteescolar` ASC),
  CONSTRAINT `transporteFK`
    FOREIGN KEY (`transporteescolar`)
    REFERENCES `geoescolar`.`transporteescolar` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 7
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `geoescolar`.`itinerario_localizacao`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `geoescolar`.`itinerario_localizacao` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `itinerario` INT(11) NOT NULL,
  `localizacao` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `itinerario_idx` (`itinerario` ASC),
  INDEX `localizacao_idx` (`localizacao` ASC),
  CONSTRAINT `id_itinerario`
    FOREIGN KEY (`itinerario`)
    REFERENCES `geoescolar`.`itinerario` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `id_localizacao`
    FOREIGN KEY (`localizacao`)
    REFERENCES `geoescolar`.`localizacao` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `geoescolar`.`mensagem`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `geoescolar`.`mensagem` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `mensagem` VARCHAR(200) NOT NULL,
  `destinatarioEmail` VARCHAR(45) NOT NULL,
  `remetenteEmail` VARCHAR(45) NOT NULL,
  `horaEnvio` DATETIME NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `geoescolar`.`registrolocalizacao`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `geoescolar`.`registrolocalizacao` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `data` VARCHAR(15) NOT NULL,
  `hora` VARCHAR(8) NOT NULL,
  `itinerario` INT(11) NOT NULL,
  `finalizado` TINYINT(1) NULL DEFAULT NULL,
  `localizacao` INT(11) NULL DEFAULT NULL,
  `inicio` TINYINT(1) NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  INDEX `itinerario_idx` (`itinerario` ASC),
  INDEX `localizacao_foreign_key_idx` (`localizacao` ASC),
  CONSTRAINT `itinerario_foreign_key`
    FOREIGN KEY (`itinerario`)
    REFERENCES `geoescolar`.`itinerario` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `localizacao_foreign_key`
    FOREIGN KEY (`localizacao`)
    REFERENCES `geoescolar`.`localizacao` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 15
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `geoescolar`.`responsavel`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `geoescolar`.`responsavel` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(45) NULL DEFAULT NULL,
  `email` VARCHAR(45) NOT NULL,
  `senha` VARCHAR(45) NOT NULL,
  `tipoResponsavel` VARCHAR(20) NOT NULL,
  `telefone` VARCHAR(45) NULL DEFAULT NULL,
  `validado` TINYINT(1) NOT NULL,
  `ehprincipal` TINYINT(1) NOT NULL,
  `codigo` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC),
  UNIQUE INDEX `codigo_UNIQUE` (`codigo` ASC))
ENGINE = InnoDB
AUTO_INCREMENT = 6
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `geoescolar`.`responsavel_crianca`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `geoescolar`.`responsavel_crianca` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `responsavel` INT(11) NOT NULL,
  `crianca` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `id_Resp_idx` (`responsavel` ASC),
  INDEX `id_Crianca_idx` (`crianca` ASC),
  CONSTRAINT `id_Crianca`
    FOREIGN KEY (`crianca`)
    REFERENCES `geoescolar`.`crianca` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `id_Resp`
    FOREIGN KEY (`responsavel`)
    REFERENCES `geoescolar`.`responsavel` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 6
DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
