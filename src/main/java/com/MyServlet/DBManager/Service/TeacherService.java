package com.MyServlet.DBManager.Service;

import com.MyServlet.Entity.Teacher;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public interface TeacherService extends DefaultService<Teacher> {
    Collection<Integer> selectTeacherStudentsID(int teacherID) throws Exception;

    Collection<String> selectTeacherNameAndSurnameByID(ArrayList<Integer> teachersID) throws Exception;

    Collection<Teacher> getAllEntitiesByUserID(ArrayList<String> usersID) throws Exception;

    Collection<String> selectTeachersCourseID(ArrayList<Teacher> teachers) throws Exception;

    Map<String, ArrayList<String>> selectFinishedTeachersData(int studentID) throws Exception;

    Map<String, ArrayList<String>> selectStudentsDataOnTeacherCourse(int teacherID, int pageNumber, int rowCount) throws Exception;

    Map<String, ArrayList<String>> selectAllTeachersNameAndSurname() throws Exception;

    Teacher selectTeacherByUserID(int userID) throws Exception;

    int selectTeacherCourseID(int teacherID) throws Exception;

    int getAllTeachersCount() throws Exception;

    void raiseToTeacher(int userID) throws Exception;

    void declineToTeacher(int userID) throws Exception;
}
