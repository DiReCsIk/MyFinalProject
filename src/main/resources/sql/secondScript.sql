CREATE TABLE `user` (
                        `USER_ID` int NOT NULL AUTO_INCREMENT,
                        `USER_EMAIL` varchar(32) NOT NULL,
                        `USER_PASSWORD` varchar(32) NOT NULL,
                        `USER_ROLE` enum('ADMINISTRATOR','TEACHER','STUDENT') NOT NULL DEFAULT 'STUDENT',
                        PRIMARY KEY (`USER_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=132 DEFAULT CHARSET=utf8;
CREATE TABLE `student` (
                           `STUDENT_ID` int NOT NULL AUTO_INCREMENT,
                           `STUDENT_NAME` varchar(30) NOT NULL,
                           `STUDENT_SURNAME` varchar(30) NOT NULL,
                           `STUDENT_BAN_STATUS` tinyint NOT NULL,
                           `STUDENT_BIRTH_DATE` date NOT NULL,
                           `USER_ID` int NOT NULL,
                           PRIMARY KEY (`STUDENT_ID`),
                           UNIQUE KEY `STUDENT_ID_UNIQUE` (`STUDENT_ID`),
                           KEY `USER2_ID_idx` (`USER_ID`),
                           CONSTRAINT `USER2_ID` FOREIGN KEY (`USER_ID`) REFERENCES `user` (`USER_ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=98 DEFAULT CHARSET=utf8;
CREATE TABLE `administrator` (
                                 `ADMINISTRATOR_ID` int NOT NULL AUTO_INCREMENT,
                                 `ADMINISTRATOR_NAME` varchar(30) NOT NULL,
                                 `ADMINISTRATOR_SURNAME` varchar(30) NOT NULL,
                                 `ADMINISTRATOR_BIRTH_DATE` date NOT NULL,
                                 `USER_ID` int NOT NULL,
                                 PRIMARY KEY (`ADMINISTRATOR_ID`),
                                 UNIQUE KEY `ADMINISTRATOR_ID_UNIQUE` (`ADMINISTRATOR_ID`),
                                 KEY `USER_ID_idx` (`USER_ID`),
                                 CONSTRAINT `USER_ID` FOREIGN KEY (`USER_ID`) REFERENCES `user` (`USER_ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;
CREATE TABLE `teacher` (
                           `TEACHER_ID` int NOT NULL AUTO_INCREMENT,
                           `TEACHER_NAME` varchar(30) NOT NULL,
                           `TEACHER_SURNAME` varchar(30) NOT NULL,
                           `TEACHER_BIRTH_DATE` date NOT NULL,
                           `USER_ID` int NOT NULL,
                           PRIMARY KEY (`TEACHER_ID`),
                           UNIQUE KEY `TEACHER_ID_UNIQUE` (`TEACHER_ID`),
                           KEY `USER_ID_idx` (`USER_ID`),
                           CONSTRAINT `USER1_ID` FOREIGN KEY (`USER_ID`) REFERENCES `user` (`USER_ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=68 DEFAULT CHARSET=utf8;
CREATE TABLE `course` (
                          `COURSE_ID` int NOT NULL AUTO_INCREMENT,
                          `COURSE_THEME` varchar(100) NOT NULL,
                          `COURSE_NAME` varchar(30) NOT NULL,
                          `COURSE_START_DATE` date NOT NULL,
                          `COURSE_END_DATE` date NOT NULL,
                          `TEACHER_ID` int NOT NULL,
                          `COURSE_TERM` int GENERATED ALWAYS AS (timestampdiff(DAY,`COURSE_START_DATE`,`COURSE_END_DATE`)) VIRTUAL,
                          `COURSE_STUDENT_COUNT` int DEFAULT '0',
                          PRIMARY KEY (`COURSE_ID`),
                          UNIQUE KEY `COURSE_ID_UNIQUE` (`COURSE_ID`),
                          KEY `TEACHER_FK_idx` (`TEACHER_ID`),
                          CONSTRAINT `TEACHER_FK` FOREIGN KEY (`TEACHER_ID`) REFERENCES `teacher` (`TEACHER_ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=76 DEFAULT CHARSET=utf8;
CREATE TABLE `student_course` (
                                  `STUDENT_ID` int NOT NULL,
                                  `COURSE_ID` int NOT NULL,
                                  `STUDENT_MARK` int NOT NULL DEFAULT '0',
                                  PRIMARY KEY (`STUDENT_ID`,`COURSE_ID`),
                                  KEY `COURSE_FK` (`COURSE_ID`),
                                  CONSTRAINT `COURSE_FK` FOREIGN KEY (`COURSE_ID`) REFERENCES `course` (`COURSE_ID`) ON DELETE CASCADE ON UPDATE CASCADE,
                                  CONSTRAINT `STUDENT_FK` FOREIGN KEY (`STUDENT_ID`) REFERENCES `student` (`STUDENT_ID`) ON DELETE CASCADE ON UPDATE CASCADE,
                                  CONSTRAINT `student_course_chk_1` CHECK (((`STUDENT_MARK` >= 0) and (`STUDENT_MARK` <= 100)))
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE DEFINER=`root`@`localhost` TRIGGER `STUDENT_COURSE_INSERT_COUNT` AFTER INSERT ON `student_course` FOR EACH ROW BEGIN
    UPDATE COURSE SET COURSE_STUDENT_COUNT = (SELECT COUNT(*) FROM (SELECT * FROM STUDENT_COURSE AS S_C WHERE NEW.COURSE_ID = S_C.COURSE_ID) AS C) WHERE COURSE_ID = NEW.COURSE_ID;
END;

CREATE DEFINER=`root`@`localhost` TRIGGER `STUDENT_COURSE_DELETE_COUNT` AFTER DELETE ON `student_course` FOR EACH ROW BEGIN
    UPDATE COURSE SET COURSE_STUDENT_COUNT = -1 + (SELECT COUNT(*) FROM (SELECT * FROM STUDENT_COURSE AS S_C WHERE OLD.COURSE_ID = S_C.COURSE_ID) AS C) WHERE COURSE_ID = OLD.COURSE_ID;
END