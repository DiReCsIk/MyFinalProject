package com.MyServlet.DBManager.Dao;

import com.MyServlet.Exception.DAOException;

import java.util.Collection;

public interface DefaultDao<E> {

    Collection<E> getAllEntities() throws DAOException;

    E selectEntityByID(int id) throws DAOException;

    void updateEntity(E entity) throws DAOException;

    void insertEntity(E entity) throws DAOException;

    void deleteEntityByID(int id) throws DAOException;
}
