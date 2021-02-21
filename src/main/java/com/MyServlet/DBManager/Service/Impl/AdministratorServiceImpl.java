package com.MyServlet.DBManager.Service.Impl;

import com.MyServlet.Command.UpdateUserInfoCommand;
import com.MyServlet.DBManager.DBConnection;
import com.MyServlet.DBManager.Dao.AdministratorDao;
import com.MyServlet.DBManager.Dao.Impl.AdministratorDaoImpl;
import com.MyServlet.DBManager.Dao.Impl.TeacherDaoImpl;
import com.MyServlet.DBManager.Dao.Impl.UserDaoImpl;
import com.MyServlet.DBManager.Dao.TeacherDao;
import com.MyServlet.DBManager.Dao.UserDao;
import com.MyServlet.DBManager.Service.AdministratorService;
import com.MyServlet.Entity.Administrator;
import com.MyServlet.Entity.Teacher;
import com.MyServlet.Exception.ConnectionException;
import com.MyServlet.Exception.DAOException;
import com.MyServlet.Exception.ServiceException;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Collection;

public class AdministratorServiceImpl implements AdministratorService {
    private static final Logger log = Logger.getLogger(AdministratorServiceImpl.class.getName());

    @Override
    public Collection<Administrator> getAllEntities() throws ConnectionException, ServiceException {
        log.info("In AdministratorServiceImpl (getAllEntities)");
        try (DBConnection dbConnection = DBConnection.createConnection()) {
            log.info("Initializing AdministratorDao");
            AdministratorDao administratorDao = new AdministratorDaoImpl(dbConnection.getConnection());
            log.info("Control transferring to Dao");
            Collection<Administrator> allEntities = administratorDao.getAllEntities();
            log.info("Closing connection");
            return allEntities;
        } catch (DAOException daoException) {
            log.error("Error!", daoException);
            throw new ServiceException(daoException.getMessage(), daoException);
        }
    }

    @Override
    public Administrator selectEntityByID(int id) throws ConnectionException, ServiceException {
        log.info("In AdministratorServiceImpl (selectEntityByID, id: " + id + ")");
        try (DBConnection dbConnection = DBConnection.createConnection()) {
            log.info("Initializing AdministratorDao");
            AdministratorDao administratorDao = new AdministratorDaoImpl(dbConnection.getConnection());
            log.info("Control transferring to Dao");
            Administrator administrator = administratorDao.selectEntityByID(id);
            log.info("Closing connection");
            return administrator;
        } catch (DAOException daoException) {
            log.error("Error!", daoException);
            throw new ServiceException(daoException.getMessage(), daoException);
        }
    }

    @Override
    public void updateEntity(Administrator administrator) throws ConnectionException, ServiceException {
        log.info("In AdministratorServiceImpl (updateEntity, administrator: " + administrator + ")");
        try (DBConnection dbConnection = DBConnection.createConnection()) {
            log.info("Initializing AdministratorDao");
            AdministratorDao administratorDao = new AdministratorDaoImpl(dbConnection.getConnection());
            log.info("Control transferring to Dao");
            administratorDao.updateEntity(administrator);
            log.info("Closing connection");
        } catch (DAOException daoException) {
            log.error("Error!", daoException);
            throw new ServiceException(daoException.getMessage(), daoException);
        }
    }

    @Override
    public void insertEntity(Administrator entity) throws ConnectionException, ServiceException {
        log.info("In AdministratorServiceImpl (insertEntity, entity: " + entity + ")");
        try (DBConnection dbConnection = DBConnection.createConnection()) {
            log.info("Initializing AdministratorDao");
            AdministratorDao administratorDao = new AdministratorDaoImpl(dbConnection.getConnection());
            log.info("Control transferring to Dao");
            administratorDao.insertEntity(entity);
            log.info("Closing connection");
        } catch (DAOException daoException) {
            log.error("Error!", daoException);
            throw new ServiceException(daoException.getMessage(), daoException);
        }
    }

    @Override
    public void deleteEntityByID(int id) throws ConnectionException, ServiceException {
        log.info("In AdministratorServiceImpl (deleteEntityByID, id: " + id + ")");
        try (DBConnection dbConnection = DBConnection.createConnection()) {
            log.info("Initializing AdministratorDao");
            AdministratorDao administratorDao = new AdministratorDaoImpl(dbConnection.getConnection());
            log.info("Control transferring to Dao");
            administratorDao.deleteEntityByID(id);
            log.info("Closing connection");
        } catch (DAOException daoException) {
            log.error("Error!", daoException);
            throw new ServiceException(daoException.getMessage(), daoException);
        }

    }

    @Override
    public Administrator selectAdministratorByUserID(int userID) throws ConnectionException, ServiceException {
        log.info("In AdministratorServiceImpl (selectAdministratorByUserID, userID: " + userID + ")");
        try (DBConnection dbConnection = DBConnection.createConnection()) {
            log.info("Initializing AdministratorDao");
            AdministratorDao administratorDao = new AdministratorDaoImpl(dbConnection.getConnection());
            log.info("Control transferring to Dao");
            Administrator administrator = administratorDao.selectAdministratorByUserID(userID);
            log.info("Closing connection");
            return administrator;
        } catch (DAOException daoException) {
            log.error("Error!", daoException);
            throw new ServiceException(daoException.getMessage(), daoException);
        }
    }

    @Override
    public Collection<Administrator> getAllEntitiesByUserID(ArrayList<String> usersID) throws ConnectionException, ServiceException {
        log.info("In AdministratorServiceImpl (getAllEntitiesByUserID, usersID: " + usersID.toString() + ")");
        try (DBConnection dbConnection = DBConnection.createConnection()) {
            log.info("Initializing AdministratorDao");
            AdministratorDao administratorDao = new AdministratorDaoImpl(dbConnection.getConnection());
            ArrayList<Administrator> administratorList = new ArrayList<>();
            log.info("Control transferring to Dao");
            for (String userID : usersID) {
                administratorList.add(administratorDao.selectAdministratorByUserID(Integer.parseInt(userID)));
            }
            log.info("Closing connection");
            return administratorList;
        } catch (DAOException daoException) {
            log.error("Error!", daoException);
            throw new ServiceException(daoException.getMessage(), daoException);
        }
    }

    @Override
    public void raiseToAdministrator(int userID) throws ConnectionException, ServiceException {
        log.info("In AdministratorServiceImpl (raiseToAdministrator, userID: " + userID + ")");
        DBConnection dbConnection = DBConnection.createConnection();
        try {
            log.info("Initializing AdministratorDao");
            AdministratorDao administratorDao = new AdministratorDaoImpl(dbConnection.getConnection());
            log.info("Initializing UserDao");
            UserDao userDao = new UserDaoImpl(dbConnection.getConnection());
            log.info("Initializing TeacherDao");
            TeacherDao teacherDao = new TeacherDaoImpl(dbConnection.getConnection());
            log.info("Starting transaction Dao");
            dbConnection.startTransaction();
            log.info("Control transferring to Dao");
            Teacher teacher = teacherDao.selectTeacherByUserID(userID);
            administratorDao.raiseToAdministrator(teacher);
            teacherDao.deleteEntityByID(teacher.getId());
            userDao.updateUserRole("ADMINISTRATOR", userID);
            log.info("Committing transaction");
            dbConnection.commit();
        } catch (DAOException daoException) {
            log.error("Error!", daoException);
            log.info("Rollback a transaction");
            dbConnection.rollback();
            throw new ServiceException(daoException.getMessage(), daoException);
        }
        finally {
            log.info("Closing connection");
            dbConnection.close();
        }

    }

    @Override
    public int getAllAdministratorsCount() throws ConnectionException, ServiceException {
        log.info("In AdministratorServiceImpl (getAllAdministratorsCount)");
        try (DBConnection dbConnection = DBConnection.createConnection()) {
            log.info("Initializing AdministratorDao");
            AdministratorDao administratorDao = new AdministratorDaoImpl(dbConnection.getConnection());
            log.info("Control transferring to Dao");
            int result = administratorDao.getAllAdministratorsCount();
            log.info("Closing connection");
            return result;
        } catch (DAOException daoException) {
            log.error("Error!", daoException);
            throw new ServiceException(daoException.getMessage(), daoException);
        }
    }
}
