package com.MyFinalProject.DAOTest;

import com.MyServlet.DBManager.Dao.Impl.UserDaoImpl;
import com.MyServlet.DBManager.Dao.UserDao;
import com.MyServlet.Entity.User;
import com.MyServlet.Exception.DAOException;
import com.MyServlet.Util.UserRole;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoTest extends AbstractDaoTest {
    private UserDao userDao;

    @Before
    public void CREATE_TABLES() throws SQLException {
        super.createTables(CREATE_USER_TABLE);
        userDao = new UserDaoImpl(connection);
    }

    @Test
    public void GET_ALL_ENTITIES_TEST() throws DAOException {
        User user = new User("test","123123", UserRole.ADMINISTRATOR);
        User secondUser = new User("test","123123", UserRole.ADMINISTRATOR);
        List<User> users = new ArrayList<>();
        userDao.insertEntity(user);
        userDao.insertEntity(secondUser);
        users.add(userDao.selectEntityByID(1));
        users.add(userDao.selectEntityByID(2));
        Assert.assertEquals(users, userDao.getAllEntities());
    }

    @Test
    public void SELECT_ENTITY_BY_ID_TEST() throws DAOException {
        User user = new User("test","123123", UserRole.ADMINISTRATOR);
        userDao.insertEntity(user);
        user.setId(1);
        Assert.assertEquals(user, userDao.selectEntityByID(1));
    }

    @Test
    public void SELECT_USER_BY_EMAIL_TEST() throws DAOException {
        User user = new User("test","123123", UserRole.ADMINISTRATOR);
        userDao.insertEntity(user);
        user.setId(1);
        Assert.assertEquals(user, userDao.selectUserByEmail("test"));
    }

    @Test
    public void UPDATE_USER_ROLE_TEST() throws DAOException {
        User user = new User("test","123123", UserRole.ADMINISTRATOR);
        userDao.insertEntity(user);
        userDao.updateUserRole(String.valueOf(UserRole.TEACHER), 1);
        user.setUserRole(UserRole.TEACHER);
        user.setId(1);
        Assert.assertEquals(user, userDao.selectEntityByID(1));
    }

    @After
    public void clearTables() {
        super.clearTables(DROP_USER_TABLE);
    }
}
