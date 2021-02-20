package com.MyServlet.DBManager.Dao;

import com.MyServlet.Entity.Administrator;
import com.MyServlet.Entity.Teacher;

public interface AdministratorDao extends DefaultDao<Administrator> {

    Administrator selectAdministratorByUserID(int userID) throws Exception;

    int getAllAdministratorsCount() throws Exception;

    void raiseToAdministrator(Teacher teacher) throws Exception;

}
