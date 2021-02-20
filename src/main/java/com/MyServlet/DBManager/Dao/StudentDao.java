package com.MyServlet.DBManager.Dao;

import com.MyServlet.Entity.Student;
import com.MyServlet.Entity.Teacher;

public interface StudentDao extends DefaultDao<Student> {
    Student selectStudentByUserID(int userID) throws Exception;

    int getAllStudentsCount() throws Exception;

    void banStudent(int userID) throws Exception;

    void unbanStudent(int userID) throws Exception;

    void declineToStudent(Teacher teacher) throws Exception;
}
