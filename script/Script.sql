-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- Schema events

-- Schema events

CREATE SCHEMA IF NOT EXISTS oasip DEFAULT CHARACTER SET utf8 ;
USE oasip;
SET NAMES UTF8;
-- Table events.EventCategories

CREATE TABLE IF NOT EXISTS eventcategories (
eventCategoryId INT NOT NULL AUTO_INCREMENT,
eventCategoryName VARCHAR(100) NOT NULL,
eventCategoryDescription VARCHAR(500),
eventDuration INT NOT NULL, CHECK (eventDuration between 1 and 480),
PRIMARY KEY (eventCategoryId))
ENGINE = InnoDB;

-- Table events.Events

CREATE TABLE IF NOT EXISTS events (
bookingID INT NOT NULL AUTO_INCREMENT,
bookingName VARCHAR(100) NOT NULL,
bookingEmail VARCHAR(45) NOT NULL,
eventCategoryId INT NOT NULL,
eventDuration INT NOT NULL, CHECK (eventDuration between 1 and 480),
eventStartTime DATETIME NOT NULL,
eventNotes VARCHAR(500),
PRIMARY KEY (bookingID),
INDEX fk_event_eventCategoryId_idx (eventCategoryId ASC) VISIBLE,
CONSTRAINT fk_event_eventCategoryId
FOREIGN KEY (eventCategoryId)
REFERENCES eventcategories (eventCategoryId)
ON DELETE NO ACTION
ON UPDATE NO ACTION)
ENGINE = InnoDB;

-- User Table
CREATE TABLE IF NOT EXISTS users (
  userID INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(100) UNIQUE NOT NULL,
  password VARCHAR(100) NOT NULL,
  email VARCHAR(50) UNIQUE NOT NULL,
  role enum('admin', 'lecturer', 'student') NOT NULL DEFAULT 'student',
  createdOn DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updatedOn DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (userID))
ENGINE = InnoDB;

-- Event Category Owner Table
CREATE TABLE IF NOT EXISTS eventcategoryowner (
  -- categoryOwnerId INT NOT NULL,
  userId INT NOT NULL,
  eventCategoryId INT NOT NULL,
  INDEX fk_eventcategoryowner_eventcategories1_idx (eventCategoryId ASC) VISIBLE,
  PRIMARY KEY (userId, eventCategoryId),
  CONSTRAINT fk_eventcategoryowner_users
    FOREIGN KEY (userId)
    REFERENCES oasip.users (userID)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_eventcategoryowner_eventcategories1
    FOREIGN KEY (eventCategoryId)
    REFERENCES oasip.eventcategories (eventCategoryId)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

INSERT INTO eventcategories
values (1,'Project Management Clinic', 'ตารางนัดหมายนี้ใช้สำหรับนัดหมาย project management clinic ในวิชา INT221 integrated project I ให้นักศึกษาเตรียมเอกสารที่เกี่ยวข้องเพื่อแสดง ระหว่างขอคำปรึกษา', 30),
(2,'DevOps/Infra Clinic', 'Use this event category for DevOps/Infra clinic.', 20),
(3,'Database Clinic', 'ตารางนัดหมายนี้ใช้สำหรับนัดหมาย database clinic ในวิชา INT221 integrated project I', 15),
(4,'Client-side Clinic', 'ตารางนัดหมายนี้ใช้สำหรับนัดหมาย client-side clinic ในวิชา INT221 integrated project I', 30),
(5,'Server-side Clinic', null, 30);

-- BookingEvent
INSERT INTO events values
(1, 'Somchai Jaidee (OR-7)', 'somchai.jai@mail.kmutt.ac.th', 2, 30, '2022-05-23 6:30:00', null),
(2, 'Somsri Rakdee (SJ-3)', 'somsri.rak@mail.kmutt.ac.th', 1, 30, '2022-04-27 2:30:00', 'ขอปรึกษาปัญหาเพื่อนไม่ช่วยงาน'),
(3, 'สมเกียรติ ขยันเรียน กลุ่ม TT-4', 'somkiat.kay@kmutt.ac.th', 3, 15, '2022-05-23 9:30:00', null),
(4, 'TEST-DAY-11', 'somkiat.kay@kmutt.ac.th', 3, 15, '2022-12-11 14:00:00', null),
(5, 'TEST-DAY-11-1', 'somkiat.kay@kmutt.ac.th', 3, 15, '2022-12-11 15:00:00', null),
(6, 'TEST-DAY-11-2', 'somkiat.kay@kmutt.ac.th', 3, 15, '2022-12-11 16:00:00', null),
(7, 'TEST-DAY-11-3', 'somkiat.kay@kmutt.ac.th', 3, 15, '2022-12-11 17:00:00', null),
(8, 'TEST-DAY-01-00', 'somkiat.kay@kmutt.ac.th', 3, 15, '2022-12-11 18:00:00', null),
(9, 'TEST-DAY-02-00', 'somkiat.kay@kmutt.ac.th', 3, 15, '2022-12-11 19:00:00', null),
(10, 'TEST-DAY-03-00', 'somkiat.kay@kmutt.ac.th', 3, 15, '2022-12-11 20:00:00', null),
(11, 'TEST-DAY-04-00', 'somkiat.kay@kmutt.ac.th', 3, 15, '2022-12-11 21:00:00', null),
(12, 'TEST-DAY-05-00', 'somkiat.kay@kmutt.ac.th', 3, 15, '2022-12-11 22:00:00', null),
(13, 'TEST-DAY-06-00', 'somkiat.kay@kmutt.ac.th', 3, 15, '2022-12-11 23:00:00', null),
(14, 'TEST-DAY-07-00', 'somkiat.kay@kmutt.ac.th', 3, 15, '2022-12-12 00:00:00', null),
(15, 'TEST-DAY-08-00', 'somkiat.kay@kmutt.ac.th', 3, 15, '2022-12-12 01:00:00', null),
(16, 'TEST-DAY-09-00', 'somkiat.kay@kmutt.ac.th', 3, 15, '2022-12-12 02:00:00', null),
(17, 'TEST-DAY-10-00', 'somkiat.kay@kmutt.ac.th', 3, 15, '2022-12-12 03:00:00', null),
(18, 'TEST-DAY-11-00', 'somkiat.kay@kmutt.ac.th', 3, 15, '2022-12-12 04:00:00', null),
(19, 'TEST-DAY-12-00', 'somkiat.kay@kmutt.ac.th', 3, 15, '2022-12-12 05:00:00', null),
(20, 'TEST-DAY-13-00', 'somkiat.kay@kmutt.ac.th', 3, 15, '2022-12-12 06:00:00', null),
(21, 'TEST-DAY-14-00', 'somkiat.kay@kmutt.ac.th', 3, 15, '2022-12-12 07:00:00', null),
(22, 'TEST-DAY-15-00', 'somkiat.kay@kmutt.ac.th', 3, 15, '2022-12-12 08:00:00', null),
(23, 'TEST-DAY-16-00', 'somkiat.kay@kmutt.ac.th', 3, 15, '2022-12-12 09:00:00', null),
(24, 'TEST-DAY-17-00', 'somkiat.kay@kmutt.ac.th', 3, 15, '2022-12-12 10:00:00', null),
(25, 'TEST-DAY-18-00', 'somkiat.kay@kmutt.ac.th', 3, 15, '2022-12-12 11:00:00', null),
(26, 'TEST-DAY-19-00', 'somkiat.kay@kmutt.ac.th', 3, 15, '2022-12-12 12:00:00', null),
(27, 'TEST-DAY-20-00', 'somkiat.kay@kmutt.ac.th', 3, 15, '2022-12-12 13:00:00', null),
(28, 'TEST-DAY-21-00', 'somkiat.kay@kmutt.ac.th', 3, 15, '2022-12-12 14:00:00', null),
(29, 'TEST-DAY-22-00', 'somkiat.kay@kmutt.ac.th', 3, 15, '2022-12-12 15:00:00', null),
(30, 'TEST-DAY-23-00', 'somkiat.kay@kmutt.ac.th', 3, 15, '2022-12-12 16:00:00', null),
(31, 'TEST-DAY-13-1', 'somkiat.kay@kmutt.ac.th', 3, 15, '2022-12-12 17:00:00', null),
(32, 'TEST-DAY-13-2', 'somkiat.kay@kmutt.ac.th', 3, 15, '2022-12-12 18:00:00', null);

-- User
INSERT INTO users values
-- (1, 'OASIP ADMIN', 'oasip.admin@kmutt.ac.th', 'admin', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
(1, 'OASIP ADMIN', '$argon2id$v=19$m=4096,t=3,p=1$sYXzbUOqBoHY1NfhJ8cjnw$H6+adWySiFPgcUogJK3hEhcF6Y4fusy7tcXYEL+f0cQ', 'oasip.admin@kmutt.ac.th', 'admin', '2022-08-01 00:00:00+07:00', '2022-08-01 00:00:00+07:00'),
(7, 'Somchai Jaidee', '$argon2id$v=19$m=4096,t=3,p=1$dmsOy7LPTjmooPu+P2oTZA$NZFTFd3f0K1Sp19aaUwyn3jgiy15yFcXhp8E4/1yXoI', 'somchai.jai@kmutt.ac.th', 'student', '2022-08-08 15:00:00+07:00', '2022-08-08 15:00:00+07:00'),
(8, 'Komkrid Rakdee', '$argon2id$v=19$m=4096,t=3,p=1$8W61ZOC5RU7sJP5kKRbSqg$OLwZNPeMqxp+g0Vbn+odcA47XMClFN+IswTueVah7F0', 'komkrid.rak@mail.kmutt.ac.th', 'student', '2022-08-08 15:00:01+07:00', '2022-08-08 15:00:01+07:00'),
(9, 'สมเกียรติ ขยันเรียน', '$argon2id$v=19$m=4096,t=3,p=1$gBqgjspF45FcIKQEw8GmaQ$alrOCZ0YrDqOu8/aZiLDMGZo4vFkSEAXA0YoHhY0BDQ', 'somkiat.kay@kmutt.ac.th', 'student', '2022-08-16 09:00:00+07:00', '2022-08-16 09:00:00+07:00'),
(2, 'Olarn Rojanapornpun', '$argon2id$v=19$m=4096,t=3,p=1$Sx7y2jxKZSjpWUV4srd8eg$AMH09iFiPQgAZ00cAdN3Gucqfhx2kRo3tQbHeLSR0RE', 'olarn.roj@kmutt.ac.th', 'lecturer', '2022-08-16 09:00:00+07:00', '2022-08-16 09:00:00+07:00'),
(3, 'Pichet Limvachiranan', '$argon2id$v=19$m=4096,t=3,p=1$46EB43gQ46Z1/EmdqxtKNA$7m6cWGO2iDlFl/ETDYuYf+ArnSjRnsNwXLIP18DTYQY', 'pichet.limv@kmutt.ac.th', 'lecturer', '2022-08-16 09:00:00+07:00', '2022-08-16 09:00:00+07:00'),
(4, 'Umaporn Supasitthimethee', '$argon2id$v=19$m=4096,t=3,p=1$1Z2UK1zC76FIQeLH54GVAQ$qfXcHF31LnuWpt37QAcWyNp8PdbOQ+jjaV1xWXixS0M', 'umaporn.sup@kmutt.ac.th', 'lecturer', '2022-08-16 09:00:00+07:00', '2022-08-16 09:00:00+07:00'),
(5, 'Siam Yamsaengsung', '$argon2id$v=19$m=4096,t=3,p=1$46EB43gQ46Z1/EmdqxtKNA$7m6cWGO2iDlFl/ETDYuYf+ArnSjRnsNwXLIP18DTYQY', 'siam.yam@kmutt.ac.th', 'lecturer', '2022-08-16 09:00:00+07:00', '2022-08-16 09:00:00+07:00'),
(6, 'Sunisa Sathapornvajana', '$argon2id$v=19$m=4096,t=3,p=1$29/ffaszvjvi3CZO45bSCg$kKpfq5WEswoqa/LfyIZzQaQ6AFdjhyiYjXRCfMiTnwg', 'sunisa.sat@kmutt.ac.th', 'lecturer', '2022-08-16 09:00:00+07:00', '2022-08-16 09:00:00+07:00'),
(10, 'lecturer', '$argon2id$v=19$m=150,t=2,p=1$1JMkXLioU05zcQ$WNOMZTvqCuV11VF2sKPN/ER7osU', 'l@l.com', 'lecturer', '2022-08-16 09:00:00+07:00', '2022-08-16 09:00:00+07:00'),
(11, 'student', '$argon2id$v=19$m=150,t=2,p=1$1JMkXLioU05zcQ$WNOMZTvqCuV11VF2sKPN/ER7osU', 's@s.com', 'student', '2022-08-16 09:00:00+07:00', '2022-08-16 09:00:00+07:00'),
(12, 'admin', '$argon2id$v=19$m=150,t=2,p=1$1JMkXLioU05zcQ$WNOMZTvqCuV11VF2sKPN/ER7osU', 'a@a.com', 'admin', '2022-08-16 09:00:00+07:00', '2022-08-16 09:00:00+07:00');

INSERT INTO eventcategoryowner VALUES
(10, 1),
(2, 1),
(2, 2),
(5, 2),
(6, 3),
(4, 4),
(2, 5),
(3, 5);

-- INSERT INTO users values
-- (1, 'PBI24 สมส่วน สุขศรี 1', SHA2('admin', 256), 'somsuan.s241@kmutt.ac.th', 'admin', current_timestamp(), current_timestamp());

-- Create USER for specific use
CREATE USER 'OASIPBE'@'%' IDENTIFIED BY 'BEBE';
GRANT ALL PRIVILEGES ON oasip.* TO 'OASIPBE'@'%';
FLUSH PRIVILEGES;
