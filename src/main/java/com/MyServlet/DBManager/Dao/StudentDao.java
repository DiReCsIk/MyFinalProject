package com.MyServlet.DBManager.Dao;

import com.MyServlet.Entity.Student;
import com.MyServlet.Entity.Teacher;
import com.MyServlet.Exception.DAOException;

public interface StudentDao extends DefaultDao<Student> {
    Student selectStudentByUserID(int userID) throws DAOException;

    int getAllStudentsCount() throws DAOException;

    void banStudent(int userID) throws DAOException;

    void unbanStudent(int userID) throws DAOException;

    void declineToStudent(Teacher teacher) throws DAOException;
}
