package com.MyServlet.DBManager.Dao.Impl;

import com.MyServlet.DBManager.Dao.AbstractDao;
import com.MyServlet.DBManager.Dao.AdministratorDao;
import com.MyServlet.Entity.Administrator;
import com.MyServlet.Entity.Teacher;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

public class AdministratorDaoImpl extends AbstractDao<Administrator> implements AdministratorDao {
    private static final String SELECT_ADMINISTRATOR_BY_ID = "SELECT * FROM ADMINISTRATOR WHERE " +
            "ADMINISTRATOR_ID = ?;";
    private static final String SELECT_ALL_ADMINISTRATORS_COUNT = "SELECT COUNT(*) FROM ADMINISTRATOR;";
    private static final String SELECT_ADMINISTRATOR_BY_USER_ID = "SELECT * FROM ADMINISTRATOR WHERE " +
            "USER_ID = ?;";
    private static final String SELECT_ALL_ADMINISTRATORS = "SELECT * FROM ADMINISTRATOR;";
    private static final String UPDATE_ADMINISTRATOR = "UPDATE ADMINISTRATOR SET ADMINISTRATOR_NAME = ?, " +
            "ADMINISTRATOR_SURNAME = ?, ADMINISTRATOR_BIRTH_DATE = ? WHERE ADMINISTRATOR_ID = ?;";
    private static final String DELETE_ADMINISTRATOR_BY_ID = "DELETE FROM ADMINISTRATOR WHERE " +
            "ADMINISTRATOR_ID = ?;";
    private static final String INSERT_ADMINISTRATOR = "INSERT INTO ADMINISTRATOR (ADMINISTRATOR_NAME, " +
            "ADMINISTRATOR_SURNAME, ADMINISTRATOR_BIRTH_DATE, USER_ID) VALUES (?, ?, ?, ?);";

    public AdministratorDaoImpl(Connection connection) {
        super(connection);
    }

    @Override
    public Collection<Administrator> getAllEntities() throws Exception {
        return selectAllByStatement(SELECT_ALL_ADMINISTRATORS);
    }

    @Override
    public Administrator selectEntityByID(int administratorID) throws Exception {
        return selectByStatement(SELECT_ADMINISTRATOR_BY_ID,
                String.valueOf(administratorID));
    }

    @Override
    public Administrator selectAdministratorByUserID(int userID) throws Exception {
        return selectByStatement(SELECT_ADMINISTRATOR_BY_USER_ID,
                String.valueOf(userID));
    }

    @Override
    public int getAllAdministratorsCount() throws Exception {
        return selectIntByStatement(SELECT_ALL_ADMINISTRATORS_COUNT);
    }

    @Override
    public void raiseToAdministrator(Teacher teacher) throws Exception {
        updateByStatement(INSERT_ADMINISTRATOR,
                teacher.getName(),
                teacher.getSurName(),
                String.valueOf(teacher.getBirthDate()),
                String.valueOf(teacher.getUserID())
        );
    }

    @Override
    public void updateEntity(Administrator administrator) throws Exception {
        updateByStatement(UPDATE_ADMINISTRATOR,
                administrator.getName(),
                administrator.getSurName(),
                String.valueOf(administrator.getBirthDate()),
                String.valueOf(administrator.getId()));
    }

    @Override
    public void insertEntity(Administrator administrator) throws Exception {
        updateByStatement(INSERT_ADMINISTRATOR,
                administrator.getName(),
                administrator.getSurName(),
                String.valueOf(administrator.getBirthDate()),
                String.valueOf(administrator.getUserID()));
    }

    @Override
    public void deleteEntityByID(int administratorID) throws Exception {
        updateByStatement(DELETE_ADMINISTRATOR_BY_ID,
                String.valueOf(administratorID));
    }

    @Override
    protected Administrator createEntity(ResultSet resultSet) throws SQLException {
        Administrator administrator = new Administrator();
        administrator.setId(resultSet.getInt("ADMINISTRATOR_ID"));
        administrator.setName(resultSet.getString("ADMINISTRATOR_NAME"));
        administrator.setSurName(resultSet.getString("ADMINISTRATOR_SURNAME"));
        administrator.setBirthDate(resultSet.getDate("ADMINISTRATOR_BIRTH_DATE"));
        administrator.setUserID(resultSet.getInt("USER_ID"));
        return administrator;
    }
}
