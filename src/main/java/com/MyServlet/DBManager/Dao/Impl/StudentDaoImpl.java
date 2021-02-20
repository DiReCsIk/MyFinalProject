package com.MyServlet.DBManager.Dao.Impl;

import com.MyServlet.DBManager.Dao.AbstractDao;
import com.MyServlet.DBManager.Dao.StudentDao;
import com.MyServlet.Entity.Student;
import com.MyServlet.Entity.Teacher;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

//TODO TRY TO BREAK WITH LARGE NAME
public class StudentDaoImpl extends AbstractDao<Student> implements StudentDao {
    private static final String SELECT_STUDENT_BY_ID = "SELECT * FROM STUDENT WHERE STUDENT_ID = ?;";
    private static final String SELECT_ALL_STUDENTS_COUNT = "SELECT COUNT(*) FROM STUDENT;";
    private static final String SELECT_STUDENT_BY_USER_ID = "SELECT * FROM STUDENT WHERE USER_ID = ?;";
    private static final String SELECT_ALL_STUDENTS = "SELECT * FROM STUDENT;";
    private static final String UPDATE_STUDENT = "UPDATE STUDENT SET STUDENT_NAME = ?, " +
            "STUDENT_SURNAME = ?, STUDENT_AVERAGE_RATING = ?, STUDENT_BAN_STATUS = ?, " +
            "STUDENT_BIRTH_DATE = ? WHERE STUDENT_ID = ?;";
    private static final String SET_BAN_STATUS_STUDENT = "UPDATE STUDENT SET STUDENT_BAN_STATUS = ? WHERE USER_ID = ?;";
    private static final String DELETE_STUDENT_BY_ID = "DELETE FROM STUDENT WHERE STUDENT_ID = ?;";
    private static final String INSERT_STUDENT = "INSERT INTO STUDENT (STUDENT_NAME, STUDENT_SURNAME, STUDENT_AVERAGE_RATING," +
            "STUDENT_BAN_STATUS, STUDENT_BIRTH_DATE, USER_ID) VALUES (?, ?, ?, ?, ?, ?);";
    private static final String SELECT_STUDENT_BY_EMAIL = "SELECT * FROM STUDENT WHERE USER_ID IN";

    public StudentDaoImpl(Connection connection) {
        super(connection);
    }

    @Override
    public Collection<Student> getAllEntities() throws Exception {
        return selectAllByStatement(SELECT_ALL_STUDENTS);
    }

    @Override
    public Student selectEntityByID(int studentID) throws Exception {
        return selectByStatement(SELECT_STUDENT_BY_ID,
                String.valueOf(studentID));
    }

    @Override
    public Student selectStudentByUserID(int userID) throws Exception {
        return selectByStatement(SELECT_STUDENT_BY_USER_ID,
                String.valueOf(userID));
    }

    @Override
    public int getAllStudentsCount() throws Exception {
        return selectIntByStatement(SELECT_ALL_STUDENTS_COUNT);
    }

    @Override
    public void banStudent(int userID) throws Exception {
        updateByStatement(SET_BAN_STATUS_STUDENT,
                "1",
                String.valueOf(userID));
    }

    @Override
    public void unbanStudent(int userID) throws Exception {
        updateByStatement(SET_BAN_STATUS_STUDENT,
                "0",
                String.valueOf(userID));
    }

    @Override
    public void declineToStudent(Teacher teacher) throws Exception {
        updateByStatement(INSERT_STUDENT,
                teacher.getName(),
                teacher.getSurName(),
                "0",
                "0",
                String.valueOf(teacher.getBirthDate()),
                String.valueOf(teacher.getUserID())
        );
    }

    @Override
    public void updateEntity(Student student) throws Exception {
        updateByStatement(UPDATE_STUDENT,
                student.getName(),
                student.getSurName(),
                String.valueOf(student.getAverageRating()),
                student.isBanStatus() ? "1" : "0",
                String.valueOf(student.getBirthDate()),
                String.valueOf(student.getId())
        );
    }

    @Override
    public void insertEntity(Student student) throws Exception {
        updateByStatement(INSERT_STUDENT,
                student.getName(),
                student.getSurName(),
                String.valueOf(student.getAverageRating()),
                student.isBanStatus() ? "1" : "0",
                String.valueOf(student.getBirthDate()),
                String.valueOf(student.getUserID())
        );
    }

    @Override
    public void deleteEntityByID(int studentID) throws Exception {
        updateByStatement(DELETE_STUDENT_BY_ID,
                String.valueOf(studentID));
    }

    @Override
    protected Student createEntity(ResultSet resultSet) throws SQLException {
        Student student = new Student();
        student.setId(resultSet.getInt("STUDENT_ID"));
        student.setName(resultSet.getString("STUDENT_NAME"));
        student.setSurName(resultSet.getString("STUDENT_SURNAME"));
        student.setBanStatus(resultSet.getBoolean("STUDENT_BAN_STATUS"));
        student.setBirthDate(resultSet.getDate("STUDENT_BIRTH_DATE"));
        student.setUserID(resultSet.getInt("USER_ID"));
        return student;
    }
}
