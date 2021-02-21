package com.MyServlet.DBManager.Dao;

import com.MyServlet.Entity.Course;
import com.MyServlet.Exception.DAOException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public interface CourseDao extends DefaultDao<Course> {
    Collection<Course> selectAllStudentAvailableCourses(int pageNumber, int rowCount, String courseName) throws DAOException;

    Collection<Course> selectAllStudentAvailableCoursesRegistered(int studentID, int pageNumber, int rowCount, String courseName) throws DAOException;

    Collection<Course> selectAllStudentFinishedCourses(int studentID, int pageNumber, int rowCount) throws DAOException;

    Collection<Course> selectAllStudentInProgressCourses(int studentID, int pageNumber, int rowCount) throws DAOException;

    Collection<Course> selectAllStudentNotStartedCourses(int studentID, int pageNumber, int rowCount) throws DAOException;

    Collection<Course> selectAllFinishedTeacherCourses(int studentID, int teacherID, int pageNumber, int rowCount) throws DAOException;

    Collection<Course> getAllEntities(int pageNumber, int rowCount) throws DAOException;

    Collection<String> selectAllCoursesNameByStudentID(int studentID) throws DAOException;

    Collection<String>  selectAllCoursesName() throws DAOException;

    Integer selectStudentMarkOnCourse(int studentID, int courseID) throws DAOException;

    int selectAvailableCoursesCount(String courseName) throws DAOException;

    int selectAvailableCoursesCountRegistered(int studentID, String courseName) throws DAOException;

    int selectFinishedCoursesCount(int studentID) throws DAOException;

    int selectInProgressCoursesCount(int studentID) throws DAOException;

    int selectNotStartedCoursesCount(int studentID) throws DAOException;

    void updateStudentMark(String mark, int studentID, int courseID) throws DAOException;

    void insertStudentToCourse(int studentID, int courseID) throws DAOException;

    int selectStudentsCountOnCourse(int courseID) throws DAOException;

    int selectCourseCount() throws DAOException;

    int selectAllFinishedTeacherCoursesCount(int studentID, int teacherID) throws DAOException;
}
