package com.MyServlet.DBManager.Service;

import com.MyServlet.Entity.Teacher;
import com.MyServlet.Exception.ConnectionException;
import com.MyServlet.Exception.DAOException;
import com.MyServlet.Exception.ServiceException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public interface TeacherService extends DefaultService<Teacher> {
    Collection<Integer> selectTeacherStudentsID(int teacherID) throws ConnectionException, ServiceException;

    Collection<String> selectTeacherNameAndSurnameByID(ArrayList<Integer> teachersID) throws ConnectionException, ServiceException;

    Collection<Teacher> getAllEntitiesByUserID(ArrayList<String> usersID) throws ConnectionException, ServiceException;

    Collection<String> selectTeachersCourseID(ArrayList<Teacher> teachers) throws ConnectionException, ServiceException;

    Map<String, ArrayList<String>> selectFinishedTeachersData(int studentID) throws ConnectionException, ServiceException;

    Map<String, ArrayList<String>> selectThreeBestTeachers() throws ConnectionException, ServiceException;

    Map<String, ArrayList<String>> selectStudentsDataOnTeacherCourse(int teacherID, int pageNumber, int rowCount) throws ConnectionException, ServiceException;

    Map<String, ArrayList<String>> selectAllTeachersNameAndSurname() throws ConnectionException, ServiceException;

    Teacher selectTeacherByUserID(int userID) throws ConnectionException, ServiceException;

    int selectTeacherCourseID(int teacherID) throws ConnectionException, ServiceException;

    int selectTeacherCourseCount(int teacherID) throws ConnectionException, ServiceException;


    int getAllTeachersCount() throws ConnectionException, ServiceException;

    void raiseToTeacher(int userID) throws ConnectionException, ServiceException;

}
