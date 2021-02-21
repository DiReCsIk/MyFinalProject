package com.MyServlet.DBManager.Service;

import com.MyServlet.Exception.ConnectionException;
import com.MyServlet.Exception.ServiceException;

import java.util.Collection;

public interface DefaultService <E>{
    Collection<E> getAllEntities() throws ConnectionException, ServiceException;

    E selectEntityByID(int id) throws ConnectionException, ServiceException;

    void updateEntity(E entity) throws ConnectionException, ServiceException;

    void insertEntity(E entity) throws ConnectionException, ServiceException;

    void deleteEntityByID(int id) throws ConnectionException, ServiceException;
}
