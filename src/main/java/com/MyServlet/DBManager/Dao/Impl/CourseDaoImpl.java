package com.MyServlet.DBManager.Dao.Impl;
//TODO валидация на server
//TODO в юзера добавить роль

import com.MyServlet.DBManager.Dao.AbstractDao;
import com.MyServlet.DBManager.Dao.CourseDao;
import com.MyServlet.Entity.Course;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class CourseDaoImpl extends AbstractDao<Course> implements CourseDao {
    private static final String SELECT_COURSE_BY_ID = "SELECT * FROM COURSE WHERE COURSE_ID = ?;";
    private static final String SELECT_ALL_COURSES = "SELECT * FROM COURSE;";
    private static final String SELECT_ALL_COURSES_COUNT = "SELECT COUNT(*) FROM COURSE;";
    private static final String SELECT_ALL_STUDENT_FINISHED_COURSES_COUNT = "SELECT COUNT(*) FROM (SELECT * FROM " +
            "COURSE INNER JOIN STUDENT_COURSE USING (COURSE_ID) WHERE COURSE_END_DATE < '" +
            new Date(new java.util.Date().getTime()) + "' AND STUDENT_ID = ?) AS C;";
    private static final String SELECT_ALL_STUDENT_FINISHED_COURSES = "SELECT * FROM COURSE INNER JOIN " +
            "STUDENT_COURSE USING (COURSE_ID) WHERE COURSE_END_DATE < '" + new Date(new java.util.Date().getTime()) +
            "' AND STUDENT_ID = ? LIMIT ? OFFSET ?";
    private static final String SELECT_ALL_STUDENT_NOT_STARTED_COURSES_COUNT = "SELECT COUNT(*) FROM (SELECT * FROM " +
            "COURSE INNER JOIN STUDENT_COURSE USING (COURSE_ID) WHERE COURSE_START_DATE > '" +
            new Date(new java.util.Date().getTime()) + "' AND STUDENT_ID = ?) AS C;";
    private static final String SELECT_ALL_STUDENT_NOT_STARTED_COURSES = "SELECT * FROM COURSE INNER JOIN " +
            "STUDENT_COURSE USING (COURSE_ID) WHERE COURSE_START_DATE > '" + new Date(new java.util.Date().getTime()) +
            "' AND STUDENT_ID = ? LIMIT ? OFFSET ?";
    private static final String SELECT_ALL_STUDENT_AVAILABLE_COURSES_COUNT = "SELECT COUNT(*) FROM COURSE WHERE " +
            "COURSE_START_DATE > '" + new Date(new java.util.Date().getTime()) + "' AND COURSE_NAME LIKE ?;";
    private static final String SELECT_ALL_STUDENT_AVAILABLE_COURSES = "SELECT * FROM COURSE WHERE COURSE_START_DATE > '"
            + new Date(new java.util.Date().getTime()) + "' AND COURSE_NAME LIKE ? LIMIT ? OFFSET ?";
    private static final String SELECT_ALL_STUDENT_IN_PROGRESS_COURSES_COUNT = "SELECT COUNT(*) FROM (SELECT * FROM " +
            "COURSE INNER JOIN STUDENT_COURSE USING (COURSE_ID) WHERE COURSE_START_DATE < '" +
            new Date(new java.util.Date().getTime()) + "' AND COURSE_END_DATE > '" +
            new Date(new java.util.Date().getTime()) + "' AND STUDENT_ID = ?) AS C;";
    private static final String SELECT_ALL_STUDENT_IN_PROGRESS_COURSES = "SELECT * FROM COURSE INNER JOIN " +
            "STUDENT_COURSE USING (COURSE_ID) WHERE COURSE_START_DATE <= '" +
            new Date(new java.util.Date().getTime()) + "' AND COURSE_END_DATE > '" +
            new Date(new java.util.Date().getTime()) + "' AND STUDENT_ID = ? LIMIT ? OFFSET ?";
    private static final String SELECT_ALL_STUDENT_AVAILABLE_COURSES_REGISTERED = "SELECT * FROM COURSE WHERE " +
            "COURSE_START_DATE > '" + new Date(new java.util.Date().getTime()) + "' AND COURSE_ID NOT IN " +
            "(SELECT COURSE_ID FROM STUDENT_COURSE WHERE STUDENT_ID = ?)  AND COURSE_NAME LIKE ? LIMIT ? OFFSET ? ;";
    private static final String SELECT_ALL_STUDENT_AVAILABLE_COURSES_COUNT_REGISTERED = "SELECT " +
            "COUNT(*) FROM (SELECT * FROM COURSE WHERE COURSE_ID NOT IN (SELECT COURSE_ID FROM " +
            "STUDENT_COURSE WHERE  STUDENT_ID = ?) AND COURSE_START_DATE > '" +
            new Date(new java.util.Date().getTime()) + "' AND COURSE_NAME LIKE ?) AS C;";
    private static final String UPDATE_COURSE = "UPDATE COURSE SET " +
            "COURSE_THEME = ?, COURSE_NAME = ?, COURSE_START_DATE = ?, COURSE_END_DATE = ?, " +
            "TEACHER_ID = ? WHERE COURSE_ID = ?;";
    private static final String UPDATE_STUDENT_MARK = "UPDATE STUDENT_COURSE SET STUDENT_MARK = ? WHERE STUDENT_ID = ?" +
            " AND COURSE_ID = ?;";
    private static final String DELETE_COURSE_BY_ID = "DELETE FROM COURSE WHERE COURSE_ID = ?;";
    private static final String INSERT_COURSE = "INSERT INTO COURSE (COURSE_THEME, COURSE_NAME," +
            "COURSE_START_DATE, COURSE_END_DATE, TEACHER_ID) VALUES (?, ?, ?, ?, ?);";
    private static final String SELECT_STUDENTS_COUNT_ON_COURSE = "SELECT COUNT(STUDENT_ID) FROM STUDENT_COURSE " +
            "WHERE COURSE_ID = ?;";
    private static final String INSERT_STUDENT_INTO_COURSE = "INSERT INTO STUDENT_COURSE (STUDENT_ID, COURSE_ID) " +
            "VALUES (?, ?)";
    private static final String SELECT_STUDENT_MARK_ON_COURSE = "SELECT STUDENT_MARK FROM STUDENT_COURSE WHERE " +
            "STUDENT_ID = ? AND COURSE_ID = ?;";
    private static final String SELECT_ALL_COURSES_LIMIT = "SELECT * FROM COURSE LIMIT ? OFFSET ?;";
    private static final String SELECT_ALL_FINISHED_TEACHER_COURSES = "SELECT * FROM COURSE INNER JOIN " +
            "STUDENT_COURSE USING (COURSE_ID) WHERE COURSE_END_DATE < '" + new Date(new java.util.Date().getTime()) +
            "' AND STUDENT_ID = ? AND TEACHER_ID = ? LIMIT ? OFFSET ?;";
    private static final String SELECT_ALL_FINISHED_TEACHER_COURSES_COUNT = "SELECT COUNT(*) FROM " +
            "(SELECT * FROM COURSE INNER JOIN STUDENT_COURSE USING (COURSE_ID) WHERE COURSE_END_DATE " +
            "< '" + new Date(new java.util.Date().getTime()) + "' AND STUDENT_ID = ? AND TEACHER_ID = ?) AS C;";
    private static final String SELECT_ALL_COURSES_AVAILABLE_NAME_BY_ID = "SELECT DISTINCT COURSE_NAME " +
            "FROM COURSE WHERE COURSE_START_DATE > '" + new Date(new java.util.Date().getTime()) + "' AND" +
            " COURSE_ID NOT IN (SELECT COURSE_ID FROM STUDENT_COURSE WHERE STUDENT_ID = ?);";
    private static final String SELECT_ALL_COURSES_AVAILABLE_NAME = "SELECT DISTINCT COURSE_NAME " +
            "FROM COURSE WHERE COURSE_START_DATE > '" + new Date(new java.util.Date().getTime()) + "';";

    public CourseDaoImpl(Connection connection) {
        super(connection);
    }

    @Override
    public Collection<Course> getAllEntities() throws Exception {
        return selectAllByStatement(SELECT_ALL_COURSES);
    }

    @Override
    public Course selectEntityByID(int courseID) throws Exception {
        return selectByStatement(SELECT_COURSE_BY_ID,
                String.valueOf(courseID));
    }

    @Override
    public void updateEntity(Course course) throws Exception {
        updateByStatement(UPDATE_COURSE,
                course.getTheme(),
                course.getName(),
                String.valueOf(course.getStartDate()),
                String.valueOf(course.getEndDate()),
                String.valueOf(course.getTeacherID()),
                String.valueOf(course.getId())
        );
    }

    @Override
    public void insertEntity(Course course) throws Exception {
        updateByStatement(INSERT_COURSE,
                course.getTheme(),
                course.getName(),
                String.valueOf(course.getStartDate()),
                String.valueOf(course.getEndDate()),
                String.valueOf(course.getTeacherID())
        );
    }

    @Override
    public void deleteEntityByID(int courseID) throws Exception {
        updateByStatement(DELETE_COURSE_BY_ID, String.valueOf(courseID));
    }

    @Override
    protected Course createEntity(ResultSet resultSet) throws SQLException {
        Course course = new Course();
        course.setId(resultSet.getInt("COURSE_ID"));
        course.setTheme(resultSet.getString("COURSE_THEME"));
        course.setName(resultSet.getString("COURSE_NAME"));
        course.setStartDate(resultSet.getDate("COURSE_START_DATE"));
        course.setEndDate(resultSet.getDate("COURSE_END_DATE"));
        course.setTeacherID(resultSet.getInt("TEACHER_ID"));
        course.setTerm(resultSet.getInt("COURSE_TERM"));
        course.setStudentCount(resultSet.getInt("COURSE_STUDENT_COUNT"));
        return course;
    }

    @Override
    public Collection<Course> selectAllStudentAvailableCourses(int pageNumber, int rowCount, String courseName) throws Exception {
        return selectAllByStatement(SELECT_ALL_STUDENT_AVAILABLE_COURSES,
                courseName,
                String.valueOf(rowCount),
                String.valueOf(rowCount * (pageNumber - 1)));
    }

    @Override
    public Collection<Course> selectAllStudentAvailableCoursesRegistered(int studentID, int pageNumber, int rowCount, String courseName) throws Exception {
        return selectAllByStatement(SELECT_ALL_STUDENT_AVAILABLE_COURSES_REGISTERED,
                String.valueOf(studentID),
                courseName,
                String.valueOf(rowCount),
                String.valueOf(rowCount * (pageNumber - 1)));
    }

    @Override
    public Collection<Course> selectAllStudentFinishedCourses(int studentID, int pageNumber, int rowCount) throws Exception {
        return selectAllByStatement(SELECT_ALL_STUDENT_FINISHED_COURSES,
                String.valueOf(studentID),
                String.valueOf(rowCount),
                String.valueOf(rowCount * (pageNumber - 1)));
    }

    @Override
    public Collection<Course> selectAllStudentInProgressCourses(int studentID, int pageNumber, int rowCount) throws Exception {
        return selectAllByStatement(SELECT_ALL_STUDENT_IN_PROGRESS_COURSES,
                String.valueOf(studentID),
                String.valueOf(rowCount),
                String.valueOf(rowCount * (pageNumber - 1)));
    }

    @Override
    public Collection<Course> selectAllStudentNotStartedCourses(int studentID, int pageNumber, int rowCount) throws Exception {
        return selectAllByStatement(SELECT_ALL_STUDENT_NOT_STARTED_COURSES,
                String.valueOf(studentID),
                String.valueOf(rowCount),
                String.valueOf(rowCount * (pageNumber - 1)));
    }

    @Override
    public int selectAvailableCoursesCount(String courseName) throws Exception {
        return selectIntByStatement(SELECT_ALL_STUDENT_AVAILABLE_COURSES_COUNT,
                courseName);
    }

    @Override
    public int selectAvailableCoursesCountRegistered(int studentID, String courseName) throws Exception {
        return selectIntByStatement(SELECT_ALL_STUDENT_AVAILABLE_COURSES_COUNT_REGISTERED,
                String.valueOf(studentID),
                courseName);
    }

    @Override
    public int selectFinishedCoursesCount(int studentID) throws Exception {
        return selectIntByStatement(SELECT_ALL_STUDENT_FINISHED_COURSES_COUNT,
                String.valueOf(studentID));
    }

    @Override
    public int selectInProgressCoursesCount(int studentID) throws Exception {
        return selectIntByStatement(SELECT_ALL_STUDENT_IN_PROGRESS_COURSES_COUNT,
                String.valueOf(studentID));
    }

    @Override
    public int selectNotStartedCoursesCount(int studentID) throws Exception {
        return selectIntByStatement(SELECT_ALL_STUDENT_NOT_STARTED_COURSES_COUNT,
                String.valueOf(studentID));
    }

    @Override
    public void updateStudentMark(String mark, int studentID, int courseID) throws Exception {
        updateByStatement(UPDATE_STUDENT_MARK,
                mark,
                String.valueOf(studentID),
                String.valueOf(courseID));
    }

    @Override
    public void insertStudentToCourse(int studentID, int courseID) throws Exception {
        updateByStatement(INSERT_STUDENT_INTO_COURSE,
                String.valueOf(studentID),
                String.valueOf(courseID));
    }

    @Override
    public int selectStudentsCountOnCourse(int courseID) throws Exception {
        return selectIntByStatement(SELECT_STUDENTS_COUNT_ON_COURSE,
                String.valueOf(courseID));
    }

    @Override
    public Integer selectStudentMarkOnCourse(int studentID, int courseID) throws Exception {
        return selectIntByStatement(SELECT_STUDENT_MARK_ON_COURSE,
                String.valueOf(studentID),
                String.valueOf(courseID));
    }

    @Override
    public Collection<Course> getAllEntities(int pageNumber, int rowCount) throws Exception {
        return selectAllByStatement(SELECT_ALL_COURSES_LIMIT,
                String.valueOf(rowCount),
                String.valueOf(rowCount * (pageNumber - 1)));
    }

    @Override
    public Collection<String>  selectAllCoursesName() throws Exception {
        return selectStringListByStatement(SELECT_ALL_COURSES_AVAILABLE_NAME);
    }

    @Override
    public Collection<String>  selectAllCoursesNameByStudentID(int studentID) throws Exception {
        return selectStringListByStatement(SELECT_ALL_COURSES_AVAILABLE_NAME_BY_ID,
                String.valueOf(studentID));
    }

    @Override
    public Collection<Course> selectAllFinishedTeacherCourses(int studentID, int teacherID, int pageNumber, int rowCount) throws Exception {
        return selectAllByStatement(SELECT_ALL_FINISHED_TEACHER_COURSES,
                String.valueOf(studentID),
                String.valueOf(teacherID),
                String.valueOf(rowCount),
                String.valueOf(rowCount * (pageNumber - 1)));
    }

    @Override
    public int selectAllFinishedTeacherCoursesCount(int studentID, int teacherID) throws Exception {
        return selectIntByStatement(SELECT_ALL_FINISHED_TEACHER_COURSES_COUNT,
                String.valueOf(studentID),
                String.valueOf(teacherID));
    }

    @Override
    public int selectCourseCount() throws Exception {
        return selectIntByStatement(SELECT_ALL_COURSES_COUNT);
    }
}
