package com.MyServlet.DBManager.Service;

import com.MyServlet.Entity.Student;
import com.MyServlet.Exception.ConnectionException;
import com.MyServlet.Exception.ServiceException;

import java.util.ArrayList;
import java.util.Collection;

public interface StudentService extends DefaultService<Student> {
    Collection<Student> getAllEntitiesByUserID(ArrayList<String> usersID) throws ConnectionException, ServiceException;

    Student selectStudentByUserID(int userID) throws ConnectionException, ServiceException;

    int getAllStudentsCount() throws ConnectionException, ServiceException;

    void unbanStudent(int userID) throws ConnectionException, ServiceException;

    void banStudent(int userID) throws ConnectionException, ServiceException;

    void declineToStudent(int userID) throws ConnectionException, ServiceException;

}
