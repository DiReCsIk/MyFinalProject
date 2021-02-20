package com.MyServlet.DBManager.Service;

import java.util.Collection;

public interface DefaultService <E>{
    Collection<E> getAllEntities() throws Exception;

    E selectEntityByID(int id) throws Exception;

    void updateEntity(E entity) throws Exception;

    void insertEntity(E entity) throws Exception;

    void deleteEntityByID(int id) throws Exception;
}
