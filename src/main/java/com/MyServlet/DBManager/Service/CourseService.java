package com.MyServlet.DBManager.Service;

import com.MyServlet.Entity.Course;
import com.MyServlet.Exception.ConnectionException;
import com.MyServlet.Exception.ServiceException;

import java.util.ArrayList;
import java.util.Collection;

public interface CourseService extends DefaultService<Course> {
    Collection<Course> selectAllStudentAvailableCourses(String courseName) throws ConnectionException, ServiceException;

    Collection<Course> selectAllStudentInProgressCourses(int studentID) throws ConnectionException, ServiceException;

    Collection<Course> selectAllStudentFinishedCourses(int studentID) throws ConnectionException, ServiceException;

    Collection<Course> selectAllStudentNotStartedCourses(int studentID) throws ConnectionException, ServiceException;

    Collection<Course> selectAllAvailableCourses(int studentID, String courseName) throws ConnectionException, ServiceException;

    Collection<Course> getAllEntities(int pageNumber, int rowCount) throws ConnectionException, ServiceException;

    Collection<Integer> selectAllStudentMarks(ArrayList<Integer> coursesID, int studentID) throws ConnectionException, ServiceException;

    Collection<Course> selectAllFinishedTeacherCourses(int studentID, int teacherID, int pageNumber, int rowCount) throws ConnectionException, ServiceException;

    Collection<String> selectAllCoursesNameByStudentID(int studentID) throws ConnectionException, ServiceException;

    Collection<String> selectAllCoursesName() throws ConnectionException, ServiceException;

    int selectFinishedCoursesCount(int studentID) throws ConnectionException, ServiceException;

    int selectInProgressCoursesCount(int studentID) throws ConnectionException, ServiceException;

    int selectNotStartedCoursesCount(int studentID) throws ConnectionException, ServiceException;

    int selectStudentsCountOnCourse(int courseID) throws ConnectionException, ServiceException;

    int selectCourseCount() throws ConnectionException, ServiceException;

    int selectAllFinishedTeacherCoursesCount(int studentID, int teacherID) throws ConnectionException, ServiceException;

    void updateStudentMark(String mark, int studentID, int courseID) throws ConnectionException, ServiceException;

    void insertStudentToCourse(int studentID, int courseID) throws ConnectionException, ServiceException;

}
