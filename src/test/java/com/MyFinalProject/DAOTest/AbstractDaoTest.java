package com.MyFinalProject.DAOTest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class AbstractDaoTest {
    private static final String DATABASE_TEST_URL = "jdbc:mysql://localhost:3306/testdb?user=testdb&password=root&serverTimezone=Europe/Kiev";
    protected static final String DROP_ADMINISTRATOR_TABLE = "DROP TABLE ADMINISTRATOR";
    protected static final String DROP_USER_TABLE = "DROP TABLE USER";
    protected static final String DROP_COURSE_TABLE = "DROP TABLE COURSE";
    protected static final String DROP_STUDENT_COURSE_TABLE = "DROP TABLE STUDENT_COURSE";
    protected static final String DROP_TEACHER_TABLE = "DROP TABLE TEACHER";
    protected static final String DROP_STUDENT_TABLE = "DROP TABLE STUDENT";
    protected static final String CREATE_STUDENT_TABLE = "CREATE TABLE IF NOT EXISTS `student` (\n" +
            "  `STUDENT_ID` int NOT NULL AUTO_INCREMENT,\n" +
            "  `STUDENT_NAME` varchar(30) NOT NULL,\n" +
            "  `STUDENT_SURNAME` varchar(30) NOT NULL,\n" +
            "  `STUDENT_BAN_STATUS` tinyint NOT NULL,\n" +
            "  `STUDENT_BIRTH_DATE` date NOT NULL,\n" +
            "  `USER_ID` int NOT NULL,\n" +
            "  PRIMARY KEY (`STUDENT_ID`),\n" +
            "  UNIQUE KEY `STUDENT_ID_UNIQUE` (`STUDENT_ID`),\n" +
            "  KEY `USER2_ID_idx` (`USER_ID`)\n" +
            ")";
    protected static final String CREATE_TEACHER_TABLE = "CREATE TABLE IF NOT EXISTS `teacher` (\n" +
            "  `TEACHER_ID` int NOT NULL AUTO_INCREMENT,\n" +
            "  `TEACHER_NAME` varchar(30) NOT NULL,\n" +
            "  `TEACHER_SURNAME` varchar(30) NOT NULL,\n" +
            "  `TEACHER_BIRTH_DATE` date NOT NULL,\n" +
            "  `USER_ID` int NOT NULL,\n" +
            "  PRIMARY KEY (`TEACHER_ID`),\n" +
            "  UNIQUE KEY `TEACHER_ID_UNIQUE` (`TEACHER_ID`),\n" +
            "  KEY `USER_ID_idx` (`USER_ID`)\n" +
            ")";
    protected static final String CREATE_STUDENT_COURSE_TABLE = "CREATE TABLE IF NOT EXISTS `student_course` (\n" +
            "  `STUDENT_ID` int NOT NULL,\n" +
            "  `COURSE_ID` int NOT NULL,\n" +
            "  `STUDENT_MARK` int NOT NULL DEFAULT '0',\n" +
            "  PRIMARY KEY (`STUDENT_ID`,`COURSE_ID`),\n" +
            "  KEY `COURSE_FK` (`COURSE_ID`)\n" +
            ") ";
    protected static final String CREATE_ADMINISTRATOR_TABLE = "CREATE TABLE IF NOT EXISTS `administrator` (\n" +
            "  `ADMINISTRATOR_ID` int NOT NULL AUTO_INCREMENT,\n" +
            "  `ADMINISTRATOR_NAME` varchar(30) NOT NULL,\n" +
            "  `ADMINISTRATOR_SURNAME` varchar(30) NOT NULL,\n" +
            "  `ADMINISTRATOR_BIRTH_DATE` date NOT NULL,\n" +
            "  `USER_ID` int NOT NULL,\n" +
            "  PRIMARY KEY (`ADMINISTRATOR_ID`),\n" +
            "  UNIQUE KEY `ADMINISTRATOR_ID_UNIQUE` (`ADMINISTRATOR_ID`),\n" +
            "  KEY `USER_ID_idx` (`USER_ID`)\n" +
            ");";
    protected static final String CREATE_USER_TABLE = "CREATE TABLE IF NOT EXISTS `user` (\n" +
            "  `USER_ID` int NOT NULL AUTO_INCREMENT,\n" +
            "  `USER_EMAIL` varchar(32) NOT NULL,\n" +
            "  `USER_PASSWORD` varchar(32) NOT NULL,\n" +
            "  `USER_ROLE` enum('ADMINISTRATOR','TEACHER','STUDENT') NOT NULL DEFAULT 'STUDENT',\n" +
            "  PRIMARY KEY (`USER_ID`)\n" +
            ");";
    protected static final String CREATE_COURSE_TABLE = "CREATE TABLE IF NOT EXISTS`course` (\n" +
            "  `COURSE_ID` int NOT NULL AUTO_INCREMENT,\n" +
            "  `COURSE_THEME` varchar(100) NOT NULL,\n" +
            "  `COURSE_NAME` varchar(30) NOT NULL,\n" +
            "  `COURSE_START_DATE` date NOT NULL,\n" +
            "  `COURSE_END_DATE` date NOT NULL,\n" +
            "  `TEACHER_ID` int NOT NULL,\n" +
            "  `COURSE_TERM` int GENERATED ALWAYS AS (timestampdiff(DAY,`COURSE_START_DATE`,`COURSE_END_DATE`)) VIRTUAL,\n" +
            "  `COURSE_STUDENT_COUNT` int DEFAULT '0',\n" +
            "  PRIMARY KEY (`COURSE_ID`),\n" +
            "  UNIQUE KEY `COURSE_ID_UNIQUE` (`COURSE_ID`),\n" +
            "  KEY `TEACHER_FK_idx` (`TEACHER_ID`)\n" +
            ");";
    protected Connection connection;

    protected void createTables(String... tables) throws SQLException {
        connection = DriverManager.getConnection(DATABASE_TEST_URL);
        try (Statement statement = connection.createStatement()) {
            for (String table : tables) {
                statement.executeUpdate(table);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    protected void clearTables(String... sqlCommands) {
        try (Statement statement = connection.createStatement();) {
            for (String sql : sqlCommands) {
                statement.executeUpdate(sql);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        try {
            connection.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
}
