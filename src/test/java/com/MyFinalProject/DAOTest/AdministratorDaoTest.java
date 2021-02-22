package com.MyFinalProject.DAOTest;

import com.MyServlet.DBManager.Dao.AdministratorDao;
import com.MyServlet.DBManager.Dao.Impl.AdministratorDaoImpl;
import com.MyServlet.Entity.Administrator;
import com.MyServlet.Entity.Teacher;
import com.MyServlet.Exception.DAOException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdministratorDaoTest extends AbstractDaoTest {
    private AdministratorDao administratorDao;

    @Before
    public void CREATE_TABLES() throws SQLException {
        super.createTables(CREATE_ADMINISTRATOR_TABLE);
        administratorDao = new AdministratorDaoImpl(connection);
    }

    @Test
    public void GET_ALL_ENTITIES_TEST() throws DAOException {
        Administrator firstAdmin = new Administrator(1, "Name", "Surname",
                Date.valueOf("2021-02-20"), 5);
        Administrator secondAdmin = new Administrator(2, "SecondName", "SecondSurname",
                Date.valueOf("2021-02-20"), 6);
        List<Administrator> administrators = new ArrayList<>();
        administrators.add(firstAdmin);
        administrators.add(secondAdmin);
        administratorDao.insertEntity(firstAdmin);
        administratorDao.insertEntity(secondAdmin);
        Assert.assertEquals(administrators, administratorDao.getAllEntities());
    }

    @Test
    public void SELECT_ENTITY_BY_ID_TEST() throws DAOException {
        Administrator firstAdmin = new Administrator(1, "Name", "Surname",
                Date.valueOf("2021-02-20"), 5);
        administratorDao.insertEntity(firstAdmin);
        Assert.assertEquals(firstAdmin, administratorDao.selectEntityByID(1));
    }

    @Test
    public void SELECT_ENTITY_BY_USER_ID_TEST() throws DAOException {
        Administrator firstAdmin = new Administrator(1, "Name", "Surname",
                Date.valueOf("2021-02-20"), 5);
        administratorDao.insertEntity(firstAdmin);
        Assert.assertEquals(firstAdmin, administratorDao.selectAdministratorByUserID(5));
    }

    @Test
    public void GET_ALL_ADMINISTRATORS_COUNT_TEST() throws DAOException {
        Administrator firstAdmin = new Administrator(1, "Name", "Surname",
                Date.valueOf("2021-02-20"), 5);
        administratorDao.insertEntity(firstAdmin);
        Assert.assertEquals(1, administratorDao.getAllAdministratorsCount());
    }

    @Test
    public void DELETE_ENTITY_BY_ID_TEST() throws DAOException {
        Administrator firstAdmin = new Administrator(1, "Name", "Surname",
                Date.valueOf("2021-02-20"), 5);
        administratorDao.insertEntity(firstAdmin);
        administratorDao.deleteEntityByID(1);
        Assert.assertEquals(0, administratorDao.getAllAdministratorsCount());
    }

    @Test
    public void UPDATE_ENTITY_BY_ID_TEST() throws DAOException {
        Administrator firstAdmin = new Administrator(1, "Name", "Surname",
                Date.valueOf("2021-02-20"), 5);
        Administrator secondAdmin = new Administrator(1, "newName", "Surname",
                Date.valueOf("2021-02-20"), 5);
        administratorDao.insertEntity(firstAdmin);
        administratorDao.updateEntity(secondAdmin);
        Assert.assertEquals(secondAdmin, administratorDao.selectEntityByID(1));
    }

    @Test
    public void RAISE_TO_ADMINISTRATOR_TEST() throws DAOException {
        Teacher teacher = new Teacher(1, "Name", "Surname",
                Date.valueOf("2021-02-20"), 5);
        administratorDao.raiseToAdministrator(teacher);
        Assert.assertEquals(1, administratorDao.getAllAdministratorsCount());
    }

    @After
    public void clearTables() {
        super.clearTables(DROP_ADMINISTRATOR_TABLE);
    }
}
