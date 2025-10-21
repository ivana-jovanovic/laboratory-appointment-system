/*
SQLyog Community v13.1.6 (64 bit)
MySQL - 10.4.18-MariaDB : Database - database
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`laboratorija` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci */;

USE `laboratorija`;



DROP TABLE IF EXISTS `Zaposleni`;

CREATE TABLE `Zaposleni` (
  `ZaposleniID` BIGINT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `Ime` VARCHAR(50) NOT NULL,
  `Prezime` VARCHAR(50) NOT NULL,
  `Username` VARCHAR(30) NOT NULL,
  `Password` VARCHAR(30) NOT NULL,
  PRIMARY KEY (`ZaposleniID`)
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;



INSERT  INTO `Zaposleni` VALUES 
(1,'Ivana','Jovanovic','ivana', 'ivana');



DROP TABLE IF EXISTS `Laboratorija`;

CREATE TABLE `Laboratorija` (
  `LaboratorijaID` BIGINT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `Naziv` VARCHAR(50) NOT NULL,
  `Grad` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`LaboratorijaID`)
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;



INSERT  INTO `Laboratorija` VALUES 
(1,'Lab BG', 'Beograd'),
(2,'Lab NI', 'Nis');



DROP TABLE IF EXISTS `ZaposleniLaboratorija`;

CREATE TABLE `ZaposleniLaboratorija` (
  `LaboratorijaID` BIGINT(10) UNSIGNED NOT NULL,
  `ZaposleniID` BIGINT(10) UNSIGNED NOT NULL,
  `Datum` DATE NOT NULL,
  `OpisRada` VARCHAR(300) NOT NULL,
  PRIMARY KEY (`LaboratorijaID`, `ZaposleniID`),
  CONSTRAINT `fk_lab_id` FOREIGN KEY (`LaboratorijaID`) REFERENCES `Laboratorija` (`LaboratorijaID`),
  CONSTRAINT `fk_zap_id2` FOREIGN KEY (`ZaposleniID`) REFERENCES `Zaposleni` (`ZaposleniID`)
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;



INSERT  INTO `ZaposleniLaboratorija` VALUES 
(1,1,'2024-10-28','Opis rada.');



DROP TABLE IF EXISTS `Kategorija`;

CREATE TABLE `Kategorija` (
  `KategorijaID` BIGINT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `Naziv` VARCHAR(30) NOT NULL,
  `Popust` DECIMAL(10,2) NOT NULL,
  PRIMARY KEY (`KategorijaID`)
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;



INSERT  INTO `Kategorija` VALUES
(1,'Kategorija I',0),
(2,'Kategorija II',10),
(3,'Kategorija III',20);


DROP TABLE IF EXISTS `Pacijent`;

CREATE TABLE `Pacijent` (
  `PacijentID` BIGINT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `Ime` VARCHAR(30) NOT NULL,
  `Prezime` VARCHAR(30) NOT NULL,
  `Email` VARCHAR(50) NOT NULL,
  `Telefon` VARCHAR(30) NOT NULL,
  `Napomena` VARCHAR(200) NOT NULL,
  `KategorijaID` BIGINT(10) UNSIGNED NOT NULL,
  PRIMARY KEY (`PacijentID`),
  CONSTRAINT `fk_kat_id` FOREIGN KEY (`KategorijaID`) REFERENCES `Kategorija` (`KategorijaID`)
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;



INSERT  INTO `Pacijent` VALUES 
(1,'Andjela','Tosic','andjela@gmail.com', '0623721283', '', 1),
(2,'Valentina', 'Petrovic','tina@gmail.com', '0662721823', '', 2),
(3,'Milena', 'Radovanovic','milena@gmail.com', '0621738219', '', 3);



DROP TABLE IF EXISTS `Usluga`;

CREATE TABLE `Usluga` (
  `UslugaID` BIGINT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `Naziv` VARCHAR(50) NOT NULL,
  `Opis` VARCHAR(300) NOT NULL,
  `Cena` DECIMAL(10,2) NOT NULL,
  PRIMARY KEY (`UslugaID`)
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;



INSERT  INTO `Usluga` VALUES 
(1,'Biohemijska analiza', 'Koristi se za dijagnostikovanje i pracenje mnogih zdravstvenih stanja.', 3000),
(2,'Bakterioloski nalaz', 'Kompletna krvna slika.', 4000),
(3,'Uzimanje brisa', 'Uzimanje brisa iz grla ili nosa.', 2000),
(4,'Urinokultura', 'Analiza urina.', 1500);



DROP TABLE IF EXISTS `Termin`;

CREATE TABLE `Termin` (
  `TerminID` BIGINT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `DatumVreme` DATETIME NOT NULL,
  `Opis` VARCHAR(200) NOT NULL,
  `IznosBezPopusta` DECIMAL(10,2) NOT NULL,
  `Popust` DECIMAL(10,2) NOT NULL,
  `UkupanIznos` DECIMAL(10,2) NOT NULL,
  `PacijentID` BIGINT(10) UNSIGNED NOT NULL,
  `ZaposleniID` BIGINT(10) UNSIGNED NOT NULL,
  PRIMARY KEY (`TerminID`),
  CONSTRAINT `fk_pacijent_id` FOREIGN KEY (`PacijentID`) REFERENCES `Pacijent` (`PacijentID`),
  CONSTRAINT `fk_zaposleni_id` FOREIGN KEY (`ZaposleniID`) REFERENCES `Zaposleni` (`ZaposleniID`)
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;



INSERT  INTO `Termin` VALUES 
(1,'2024-10-25 12:00:00','Biohemijska analiza i urinokultura.', 4500, 0, 4500, 1, 1);


DROP TABLE IF EXISTS `StavkaTermina`;

CREATE TABLE `StavkaTermina` (
  `TerminID` BIGINT(10) UNSIGNED NOT NULL,
  `Rb` INT(7) NOT NULL,
  `Iznos` DECIMAL(10,2) NOT NULL,
  `Napomena` VARCHAR(200) NOT NULL,
  `UslugaID` BIGINT(10) UNSIGNED NOT NULL,
  PRIMARY KEY (`TerminID`,`Rb`),
  CONSTRAINT `fk_termin_id` FOREIGN KEY (`TerminID`) REFERENCES `Termin` (`TerminID`) ON DELETE CASCADE,
  CONSTRAINT `fk_usluga_id` FOREIGN KEY (`UslugaID`) REFERENCES `Usluga` (`UslugaID`)
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;



INSERT  INTO `StavkaTermina` VALUES 
(1,1,3000,'',1),
(1,2,1500,'',4);

INSERT INTO `Zaposleni` VALUES
(2,'Marko','Nikolic','marko','marko123'),
(3,'Ana','Petrovic','ana','ana123'),
(4,'Jelena','Savic','jelena','jelena123');

INSERT INTO `Laboratorija` VALUES
(3,'Lab NS', 'Novi Sad'),
(4,'Lab KG', 'Kragujevac');

INSERT INTO `ZaposleniLaboratorija` VALUES
(1,2,'2024-11-01','Analiza uzoraka krvi.'),
(2,3,'2024-11-02','Pregled nalaza pacijenata.'),
(3,4,'2024-11-05','Priprema laboratorijske opreme.');

INSERT INTO `Usluga` VALUES
(5,'Test intolerancije na hranu','Detekcija intolerancija na određene namirnice.', 5000),
(6,'Hormon analiza','Provera nivoa hormona u krvi.', 3500),
(7,'Alergološki test','Otkrivanje alergija na polen, prašinu i hranu.', 4500);

INSERT INTO `Termin` VALUES
(2,'2024-10-26 09:30:00','Bakteriološki nalaz i uzimanje brisa.', 6000, 600, 5400, 2, 2),
(3,'2024-10-27 14:00:00','Kompletan alergološki test.', 4500, 450, 4050, 3, 3);

INSERT INTO `StavkaTermina` VALUES
(2,1,4000,'',2),   -- Bakteriološki nalaz
(2,2,2000,'',3),   -- Uzimanje brisa
(3,1,4500,'',7);   -- Alergološki test





/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
