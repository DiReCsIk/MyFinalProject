package com.MyServlet.DBManager.Dao;

import com.MyServlet.Entity.Administrator;
import com.MyServlet.Entity.Student;
import com.MyServlet.Entity.Teacher;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface TeacherDao extends DefaultDao<Teacher> {
    Map<String, ArrayList<String>> selectAllTeachersNameAndSurname() throws Exception;
    Map<String, ArrayList<String>> selectFinishedTeachersData(int studentID) throws Exception;

    Map<String, ArrayList<String>> selectStudentsDataOnTeacherCourse(int teacherID, int pageNumber, int rowCount) throws Exception;

    List<Integer> selectTeacherStudentsID(int teacherID) throws Exception;

    Teacher selectTeacherByUserID(int id) throws Exception;

    String selectTeacherNameAndSurnameByID(int teacherID) throws Exception;

    int selectTeacherCourseID(int teacherID) throws Exception;

    int getAllTeachersCount() throws Exception;

    void raiseToTeacher(Student student) throws Exception;

    void declineToTeacher(Administrator administrator) throws Exception;


}
