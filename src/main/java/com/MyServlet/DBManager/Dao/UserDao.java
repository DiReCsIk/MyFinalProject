package com.MyServlet.DBManager.Dao;

import com.MyServlet.Entity.User;

import java.util.Collection;

public interface UserDao extends DefaultDao<User> {
    Collection<User> getAllTypeEntitiesWithLimit(String type, int rowCount, int pageNumber) throws Exception;

    User selectUserByEmail(String email) throws Exception;

    void updateUserRole(String role, int userID) throws Exception;

}
