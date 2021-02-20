package com.MyServlet.DBManager.Service;

import com.MyServlet.Entity.Course;

import java.util.ArrayList;
import java.util.Collection;

public interface CourseService extends DefaultService<Course> {
    Collection<Course> selectAllStudentAvailableCourses(int pageNumber, int rowCount, String courseName) throws Exception;

    Collection<Course> selectAllStudentInProgressCourses(int studentID, int pageNumber, int rowCount) throws Exception;

    Collection<Course> selectAllStudentFinishedCourses(int studentID, int pageNumber, int rowCount) throws Exception;

    Collection<Course> selectAllStudentNotStartedCourses(int studentID, int pageNumber, int rowCount) throws Exception;

    Collection<Course> selectAllStudentAvailableCoursesRegistered(int studentID, int pageNumber, int rowCount, String courseName) throws Exception;

    Collection<Course> getAllEntities(int pageNumber, int rowCount) throws Exception;

    Collection<Integer> selectAllStudentMarks(ArrayList<Integer> coursesID, int studentID) throws Exception;

    Collection<Course> selectAllFinishedTeacherCourses(int studentID, int teacherID, int pageNumber, int rowCount) throws Exception;

    Collection<String> selectAllCoursesNameByStudentID(int studentID) throws Exception;

    Collection<String> selectAllCoursesName() throws Exception;

    int selectAvailableCoursesCountRegistered(int studentID, String courseName) throws Exception;

    int selectAvailableCoursesCount(String courseName) throws Exception;

    int selectFinishedCoursesCount(int studentID) throws Exception;

    int selectInProgressCoursesCount(int studentID) throws Exception;

    int selectNotStartedCoursesCount(int studentID) throws Exception;

    int selectStudentsCountOnCourse(int courseID) throws Exception;

    int selectCourseCount() throws Exception;

    int selectAllFinishedTeacherCoursesCount(int studentID, int teacherID) throws Exception;

    void updateStudentMark(String mark, int studentID, int courseID) throws Exception;

    void insertStudentToCourse(int studentID, int courseID) throws Exception;

}
