package com.MyServlet.DBManager.Service.Impl;

import com.MyServlet.DBManager.DBConnection;
import com.MyServlet.DBManager.Dao.Impl.UserDaoImpl;
import com.MyServlet.DBManager.Dao.UserDao;
import com.MyServlet.DBManager.Service.UserService;
import com.MyServlet.Entity.User;
import com.MyServlet.Exception.ConnectionException;
import com.MyServlet.Exception.DAOException;
import com.MyServlet.Exception.ServiceException;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class UserServiceImpl implements UserService {
    private static final Logger log = Logger.getLogger(UserServiceImpl.class.getName());

    @Override
    public Collection<User> getAllEntities() throws ConnectionException, ServiceException {
        log.info("In UserServiceImpl (getAllEntities)");
        try (DBConnection dbConnection = DBConnection.createConnection()) {
            log.info("Initializing UserDao");
            UserDao userDao = new UserDaoImpl(dbConnection.getConnection());
            log.info("Control transferring to Dao");
            Collection<User> allEntities = userDao.getAllEntities();
            log.info("Closing connection");
            return allEntities;
        } catch (DAOException daoException) {
            log.error("Error!", daoException);
            throw new ServiceException(daoException.getMessage(), daoException);
        }
    }

    @Override
    public User selectEntityByID(int id) throws ConnectionException, ServiceException {
        log.info("In UserServiceImpl (selectEntityByID, id: " + id + ")");
        try (DBConnection dbConnection = DBConnection.createConnection()) {
            log.info("Initializing UserDao");
            UserDao userDao = new UserDaoImpl(dbConnection.getConnection());
            log.info("Control transferring to Dao");
            User user = userDao.selectEntityByID(id);
            log.info("Closing connection");
            return user;
        } catch (DAOException daoException) {
            log.error("Error!", daoException);
            throw new ServiceException(daoException.getMessage(), daoException);
        }
    }

    @Override
    public void updateEntity(User user) throws ConnectionException, ServiceException {
        log.info("In UserServiceImpl (updateEntity, user " + user + ")");
        try (DBConnection dbConnection = DBConnection.createConnection()) {
            log.info("Initializing UserDao");
            UserDao userDao = new UserDaoImpl(dbConnection.getConnection());
            log.info("Control transferring to Dao");
            userDao.updateEntity(user);
            log.info("Closing connection");
        } catch (DAOException daoException) {
            log.error("Error!", daoException);
            throw new ServiceException(daoException.getMessage(), daoException);
        }
    }

    @Override
    public void insertEntity(User entity) throws ConnectionException, ServiceException {
        log.info("In UserServiceImpl (insertEntity, entity: " + entity + ")");
        try (DBConnection dbConnection = DBConnection.createConnection()) {
            log.info("Initializing UserDao");
            UserDao userDao = new UserDaoImpl(dbConnection.getConnection());
            log.info("Control transferring to Dao");
            userDao.insertEntity(entity);
            log.info("Closing connection");
        } catch (DAOException daoException) {
            log.error("Error!", daoException);
            throw new ServiceException(daoException.getMessage(), daoException);
        }
    }

    @Override
    public void deleteEntityByID(int id) throws ConnectionException, ServiceException {
        log.info("In UserServiceImpl (deleteEntityByID, id: " + id + ")");
        try (DBConnection dbConnection = DBConnection.createConnection()) {
            log.info("Initializing UserDao");
            UserDao userDao = new UserDaoImpl(dbConnection.getConnection());
            log.info("Control transferring to Dao");
            userDao.deleteEntityByID(id);
            log.info("Closing connection");
        } catch (DAOException daoException) {
            log.error("Error!", daoException);
            throw new ServiceException(daoException.getMessage(), daoException);
        }
    }

    @Override
    public User selectUserByEmail(String email) throws ConnectionException, ServiceException {
        log.info("In UserServiceImpl (selectUserByEmail, email: " + email + ")");
        try (DBConnection dbConnection = DBConnection.createConnection()) {
            log.info("Initializing UserDao");
            UserDao userDao = new UserDaoImpl(dbConnection.getConnection());
            log.info("Control transferring to Dao");
            User user = userDao.selectUserByEmail(email);
            log.info("Closing connection");
            return user;
        } catch (DAOException daoException) {
            log.error("Error!", daoException);
            throw new ServiceException(daoException.getMessage(), daoException);
        }
    }

    @Override
    public Map<String, ArrayList<String>> getUsersData(String type, int pageNumber, int rowCount) throws ConnectionException, ServiceException {
        log.info("In UserServiceImpl (getUsersData, type: " + type + ", pageNumber: " + pageNumber + ", rowCount: " +
                rowCount + ")");
        try (DBConnection dbConnection = DBConnection.createConnection()) {
            log.info("Initializing UserDao");
            UserDao userDao = new UserDaoImpl(dbConnection.getConnection());
            Map<String, ArrayList<String>> userMap = new LinkedHashMap<>();
            userMap.put("userID", new ArrayList<>());
            userMap.put("userEmail", new ArrayList<>());
            log.info("Control transferring to Dao");
            Collection<User> userList = userDao.getAllTypeEntitiesWithLimit(type, rowCount, pageNumber);
            for (User user : userList) {
                userMap.get("userID").add(String.valueOf(user.getId()));
                userMap.get("userEmail").add(String.valueOf(user.getEmail()));
            }
            log.info("Closing connection");
            return userMap;
        } catch (DAOException daoException) {
            log.error("Error!", daoException);
            throw new ServiceException(daoException.getMessage(), daoException);
        }
    }
}
