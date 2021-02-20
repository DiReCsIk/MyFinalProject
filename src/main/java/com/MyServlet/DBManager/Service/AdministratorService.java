package com.MyServlet.DBManager.Service;

import com.MyServlet.Entity.Administrator;

import java.util.ArrayList;
import java.util.Collection;

public interface AdministratorService extends DefaultService<Administrator> {
    Collection<Administrator> getAllEntitiesByUserID(ArrayList<String> usersID) throws Exception;

    Administrator selectAdministratorByUserID(int userID) throws Exception;

    int getAllAdministratorsCount() throws Exception;

    void raiseToAdministrator(int userID) throws Exception;

}
