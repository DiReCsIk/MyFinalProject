package com.MyServlet.DBManager.Dao;

import com.MyServlet.Entity.Administrator;
import com.MyServlet.Entity.Teacher;
import com.MyServlet.Exception.DAOException;

public interface AdministratorDao extends DefaultDao<Administrator> {

    Administrator selectAdministratorByUserID(int userID) throws DAOException;

    int getAllAdministratorsCount() throws DAOException;

    void raiseToAdministrator(Teacher teacher) throws DAOException;

}
