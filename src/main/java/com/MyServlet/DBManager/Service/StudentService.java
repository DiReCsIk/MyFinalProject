package com.MyServlet.DBManager.Service;

import com.MyServlet.Entity.Student;

import java.util.ArrayList;
import java.util.Collection;

public interface StudentService extends DefaultService<Student> {
    Collection<Student> getAllEntitiesByUserID(ArrayList<String> usersID) throws Exception;

    Student selectStudentByUserID(int userID) throws Exception;

    int getAllStudentsCount() throws Exception;

    void unbanStudent(int userID) throws Exception;

    void banStudent(int userID) throws Exception;

    void declineToStudent(int userID) throws Exception;

}
