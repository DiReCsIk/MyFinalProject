package com.MyServlet.DBManager.Dao;

import com.MyServlet.Entity.Administrator;
import com.MyServlet.Entity.Student;
import com.MyServlet.Entity.Teacher;
import com.MyServlet.Exception.DAOException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface TeacherDao extends DefaultDao<Teacher> {
    Map<String, ArrayList<String>> selectAllTeachersNameAndSurname() throws DAOException;
    Map<String, ArrayList<String>> selectFinishedTeachersData(int studentID) throws DAOException;

    Map<String, ArrayList<String>> selectStudentsDataOnTeacherCourse(int teacherID, int pageNumber, int rowCount) throws DAOException;

    List<Integer> selectTeacherStudentsID(int teacherID) throws DAOException;

    Teacher selectTeacherByUserID(int id) throws DAOException;

    String selectTeacherNameAndSurnameByID(int teacherID) throws DAOException;

    int selectTeacherCourseID(int teacherID) throws DAOException;

    int getAllTeachersCount() throws DAOException;

    void raiseToTeacher(Student student) throws DAOException;

    void declineToTeacher(Administrator administrator) throws DAOException;


}
