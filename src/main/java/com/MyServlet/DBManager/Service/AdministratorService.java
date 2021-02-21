package com.MyServlet.DBManager.Service;

import com.MyServlet.Entity.Administrator;
import com.MyServlet.Exception.ConnectionException;
import com.MyServlet.Exception.ServiceException;

import java.util.ArrayList;
import java.util.Collection;

public interface AdministratorService extends DefaultService<Administrator> {
    Collection<Administrator> getAllEntitiesByUserID(ArrayList<String> usersID) throws ConnectionException, ServiceException;

    Administrator selectAdministratorByUserID(int userID) throws ConnectionException, ServiceException;

    int getAllAdministratorsCount() throws ConnectionException, ServiceException;

    void raiseToAdministrator(int userID) throws ConnectionException, ServiceException;

}
