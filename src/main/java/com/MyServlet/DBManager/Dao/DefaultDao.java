package com.MyServlet.DBManager.Dao;

import java.util.Collection;

public interface DefaultDao<E> {

    Collection<E> getAllEntities() throws Exception;

    E selectEntityByID(int id) throws Exception;

    void updateEntity(E entity) throws Exception;

    void insertEntity(E entity) throws Exception;

    void deleteEntityByID(int id) throws Exception;
}
