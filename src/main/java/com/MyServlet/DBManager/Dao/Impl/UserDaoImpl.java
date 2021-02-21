package com.MyServlet.DBManager.Dao.Impl;

import com.MyServlet.DBManager.Dao.AbstractDao;
import com.MyServlet.DBManager.Dao.UserDao;
import com.MyServlet.Entity.User;
import com.MyServlet.Exception.DAOException;
import com.MyServlet.Util.UserRole;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

public class UserDaoImpl extends AbstractDao<User> implements UserDao {
    private static final String SELECT_ALL_USERS = "SELECT * FROM USER;";
    private static final String UPDATE_USER_ROLE = "UPDATE USER SET USER_ROLE = ? WHERE USER_ID = ?;";
    private static final String SELECT_ALL_USERS_STUDENT_ROLE_LIMIT = "SELECT * FROM USER WHERE USER_ROLE = 'STUDENT'" +
            " LIMIT ? OFFSET ?;";
    private static final String SELECT_ALL_USERS_ADMIN_ROLE_LIMIT = "SELECT * FROM USER WHERE USER_ROLE = " +
            "'ADMINISTRATOR' LIMIT ? OFFSET ?;";
    private static final String SELECT_ALL_USERS_TEACHER_ROLE_LIMIT = "SELECT * FROM USER WHERE USER_ROLE = " +
            "'TEACHER' LIMIT ? OFFSET ?;";
    private static final String SELECT_USER_BY_ID = "SELECT * FROM USER WHERE USER_ID = ?;";
    private static final String DELETE_USER_BY_ID = "DELETE FROM USER WHERE USER_ID = ?;";
    private static final String UPDATE_USER = "UPDATE USER SET USER_EMAIL = ?, USER_PASSWORD = ?, USER_ROLE = ?" +
            "WHERE USER_ID = ?";
    private static final String INSERT_USER = "INSERT INTO USER (USER_EMAIL, USER_PASSWORD, USER_ROLE) " +
            "VALUES (?, ?, ?);";
    private static final String SELECT_USER_BY_EMAIL = "SELECT * FROM USER WHERE USER_EMAIL = ?;";

    public UserDaoImpl(Connection connection) {
        super(connection);
    }

    @Override
    public Collection<User> getAllEntities() throws DAOException {
        return selectAllByStatement(SELECT_ALL_USERS);
    }

    @Override
    public User selectEntityByID(int userID) throws DAOException {
        return selectByStatement(SELECT_USER_BY_ID,
                String.valueOf(userID));
    }

    @Override
    public User selectUserByEmail(String email) throws DAOException {
        return selectByStatement(SELECT_USER_BY_EMAIL,
                email);
    }

    @Override
    public void updateUserRole(String role, int userID) throws DAOException {
        updateByStatement(UPDATE_USER_ROLE,
                role,
                String.valueOf(userID));
    }

    @Override
    public Collection<User> getAllTypeEntitiesWithLimit(String type, int rowCount, int pageNumber) throws DAOException {
        String statement;
        switch (type) {
            case "ADMINISTRATOR":
                statement = SELECT_ALL_USERS_ADMIN_ROLE_LIMIT;
                break;
            case "TEACHER":
                statement = SELECT_ALL_USERS_TEACHER_ROLE_LIMIT;
                break;
            default:
                statement = SELECT_ALL_USERS_STUDENT_ROLE_LIMIT;
        }
        return selectAllByStatement(statement,
                String.valueOf(rowCount),
                String.valueOf(rowCount * (pageNumber - 1)));
    }

    @Override
    public void updateEntity(User user) throws DAOException {
        updateByStatement(UPDATE_USER,
                user.getEmail(),
                user.getPassword(),
                String.valueOf(user.getUserRole()),
                String.valueOf(user.getId()));
    }

    @Override
    public void insertEntity(User user) throws DAOException {
        updateByStatement(INSERT_USER,
                user.getEmail(),
                user.getPassword(),
                String.valueOf(user.getUserRole()));
    }

    @Override
    public void deleteEntityByID(int userID) throws DAOException {
        updateByStatement(DELETE_USER_BY_ID, String.valueOf(userID));
    }

    @Override
    protected User createEntity(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getInt("USER_ID"));
        user.setPassword(resultSet.getString("USER_PASSWORD"));
        user.setEmail(resultSet.getString("USER_EMAIL"));
        user.setUserRole(UserRole.valueOf(resultSet.getString("USER_ROLE")));
        return user;
    }
}
