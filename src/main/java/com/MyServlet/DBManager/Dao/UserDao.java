package com.MyServlet.DBManager.Dao;

import com.MyServlet.Entity.User;
import com.MyServlet.Exception.DAOException;

import java.util.Collection;

public interface UserDao extends DefaultDao<User> {
    Collection<User> getAllTypeEntitiesWithLimit(String type, int rowCount, int pageNumber) throws DAOException;

    User selectUserByEmail(String email) throws DAOException;

    void updateUserRole(String role, int userID) throws DAOException;
}
