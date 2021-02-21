package com.MyServlet.DBManager.Dao.Impl;

import com.MyServlet.DBManager.Dao.AbstractDao;
import com.MyServlet.DBManager.Dao.TeacherDao;
import com.MyServlet.Entity.Administrator;
import com.MyServlet.Entity.Student;
import com.MyServlet.Entity.Teacher;
import com.MyServlet.Exception.DAOException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class TeacherDaoImpl extends AbstractDao<Teacher> implements TeacherDao {
    private static final String SELECT_TEACHER_BY_ID = "SELECT * FROM TEACHER WHERE TEACHER_ID = ?;";
    private static final String SELECT_ALL_TEACHERS_COUNT = "SELECT COUNT(*) FROM TEACHER;";
    private static final String SELECT_TEACHER_BY_USER_ID = "SELECT * FROM TEACHER WHERE USER_ID = ?;";
    private static final String SELECT_ALL_TEACHERS = "SELECT * FROM TEACHER;";
    private static final String SELECT_TEACHER_NAME_AND_SURNAME_BY_ID = "SELECT CONCAT(TEACHER_NAME, ' ', " +
            "TEACHER_SURNAME) AS 'TEACHER_INFO' FROM TEACHER WHERE TEACHER_ID = ?;";
    private static final String SELECT_ALL_FREE_TEACHERS_NAME_AND_SURNAME = "SELECT TEACHER_ID, CONCAT(TEACHER_NAME, ' ', " +
            "TEACHER_SURNAME) AS 'TEACHER_INFO' FROM TEACHER WHERE TEACHER_ID NOT IN (SELECT TEACHER_ID FROM COURSE);";
    private static final String SELECT_TEACHER_COURSE_ID = "SELECT COURSE_ID FROM COURSE WHERE TEACHER_ID = ?";
    private static final String SELECT_STUDENTS_DATA_ON_TEACHER_COURSE = "SELECT STUDENT_NAME, " +
            "STUDENT_SURNAME, STUDENT_BIRTH_DATE, STUDENT_MARK, STUDENT_ID FROM STUDENT_COURSE INNER JOIN STUDENT " +
            "USING(STUDENT_ID) INNER JOIN COURSE USING(COURSE_ID) WHERE TEACHER_ID = ? LIMIT ? OFFSET ?;";
    private static final String UPDATE_TEACHER = "UPDATE TEACHER SET TEACHER_NAME = ?, TEACHER_SURNAME = ?, " +
            "TEACHER_BIRTH_DATE = ? WHERE TEACHER_ID = ?;";
    private static final String DELETE_TEACHER_BY_ID = "DELETE FROM TEACHER WHERE TEACHER_ID = ?;";
    private static final String INSERT_TEACHER = "INSERT INTO TEACHER (TEACHER_NAME, TEACHER_SURNAME," +
            "TEACHER_BIRTH_DATE, USER_ID) VALUES (?, ?, ?, ?);";
    private static final String SELECT_TEACHER_STUDENTS_ID = "SELECT STUDENT_ID FROM STUDENT_COURSE INNER JOIN " +
            "COURSE USING(COURSE_ID) WHERE TEACHER_ID = ?;";
    private static final String SELECT_ALL_FINISHED_TEACHERS_DATA = "SELECT DISTINCT CONCAT" +
            "(TEACHER_NAME, ' ', TEACHER_SURNAME) AS 'TEACHER_INFO', TEACHER_ID FROM TEACHER WHERE " +
            "TEACHER_ID IN (SELECT TEACHER_ID FROM COURSE INNER JOIN STUDENT_COURSE USING (COURSE_ID)" +
            " WHERE COURSE_END_DATE < '" + new java.sql.Date(new java.util.Date().getTime()) + "' AND STUDENT_ID = ?);";

    public TeacherDaoImpl(Connection connection) {
        super(connection);
    }

    @Override
    public Collection<Teacher> getAllEntities() throws DAOException {
        return selectAllByStatement(SELECT_ALL_TEACHERS);
    }

    @Override
    public Teacher selectEntityByID(int teacherID) throws DAOException {
        return selectByStatement(SELECT_TEACHER_BY_ID,
                String.valueOf(teacherID));
    }

    @Override
    public Teacher selectTeacherByUserID(int userID) throws DAOException {
        return selectByStatement(SELECT_TEACHER_BY_USER_ID,
                String.valueOf(userID));
    }

    @Override
    public Map<String, ArrayList<String>> selectStudentsDataOnTeacherCourse(int teacherID, int pageNumber, int rowCount) throws DAOException {
        String[] listArgs = new String[]{"name", "surName", "birthDay", "mark", "id"};
        return selectMapByStatement(SELECT_STUDENTS_DATA_ON_TEACHER_COURSE,
                listArgs,
                String.valueOf(teacherID),
                String.valueOf(rowCount),
                String.valueOf(rowCount * (pageNumber - 1)));
    }

    @Override
    public List<Integer> selectTeacherStudentsID(int teacherID) throws DAOException {
        return selectIntegerListByStatement(SELECT_TEACHER_STUDENTS_ID,
                String.valueOf(teacherID));
    }

    @Override
    public int selectTeacherCourseID(int teacherID) throws DAOException {
        return selectIntByStatement(SELECT_TEACHER_COURSE_ID,
                String.valueOf(teacherID));
    }

    @Override
    public String selectTeacherNameAndSurnameByID(int teacherID) throws DAOException {
        return selectStringByStatement(SELECT_TEACHER_NAME_AND_SURNAME_BY_ID,
                String.valueOf(teacherID));
    }

    @Override
    public int getAllTeachersCount() throws DAOException {
        return selectIntByStatement(SELECT_ALL_TEACHERS_COUNT);
    }

    @Override
    public void raiseToTeacher(Student student) throws DAOException {
        updateByStatement(INSERT_TEACHER,
                student.getName(),
                student.getSurName(),
                String.valueOf(student.getBirthDate()),
                String.valueOf(student.getUserID()));
    }

    @Override
    public void declineToTeacher(Administrator administrator) throws DAOException {
        updateByStatement(INSERT_TEACHER,
                administrator.getName(),
                administrator.getSurName(),
                String.valueOf(administrator.getBirthDate()),
                String.valueOf(administrator.getUserID()));
    }

    @Override
    public Map<String, ArrayList<String>> selectAllTeachersNameAndSurname() throws DAOException {
        String[] listArgs = new String[]{"id", "data"};
        return selectMapByStatement(SELECT_ALL_FREE_TEACHERS_NAME_AND_SURNAME,
                listArgs);
    }

    @Override
    public Map<String, ArrayList<String>> selectFinishedTeachersData(int studentID) throws DAOException {
        String[] listArgs = new String[]{"data", "id"};
        return selectMapByStatement(SELECT_ALL_FINISHED_TEACHERS_DATA,
                listArgs,
                String.valueOf(studentID));
    }

    @Override
    public void updateEntity(Teacher teacher) throws DAOException {
        updateByStatement(UPDATE_TEACHER,
                teacher.getName(),
                teacher.getSurName(),
                String.valueOf(teacher.getBirthDate()),
                String.valueOf(teacher.getId()));
    }

    @Override
    public void insertEntity(Teacher teacher) throws DAOException {
        updateByStatement(INSERT_TEACHER,
                teacher.getName(),
                teacher.getSurName(),
                String.valueOf(teacher.getBirthDate()),
                String.valueOf(teacher.getUserID()));
    }

    @Override
    public void deleteEntityByID(int teacherID) throws DAOException {
        updateByStatement(DELETE_TEACHER_BY_ID,
                String.valueOf(teacherID));
    }

    @Override
    protected Teacher createEntity(ResultSet resultSet) throws SQLException {
        Teacher teacher = new Teacher();
        teacher.setId(resultSet.getInt("TEACHER_ID"));
        teacher.setName(resultSet.getString("TEACHER_NAME"));
        teacher.setSurName(resultSet.getString("TEACHER_SURNAME"));
        teacher.setBirthDate(resultSet.getDate("TEACHER_BIRTH_DATE"));
        teacher.setUserID(resultSet.getInt("USER_ID"));
        return teacher;
    }
}
