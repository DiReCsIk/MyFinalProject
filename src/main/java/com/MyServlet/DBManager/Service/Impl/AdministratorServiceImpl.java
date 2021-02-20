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
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Collection;

public class AdministratorServiceImpl implements AdministratorService {
    private static final Logger log = Logger.getLogger(AdministratorServiceImpl.class.getName());

    @Override
    public Collection<Administrator> getAllEntities() throws Exception {
        log.info("In AdministratorServiceImpl (getAllEntities)");
        DBConnection dbConnection = DBConnection.createConnection();
        log.info("Initializing AdministratorDao");
        AdministratorDao administratorDao = new AdministratorDaoImpl(dbConnection.getConnection());
        log.info("Control transferring to Dao");
        Collection<Administrator> allEntities = administratorDao.getAllEntities();
        log.info("Closing connection");
        dbConnection.closeConnection();
        return allEntities;
    }

    @Override
    public Administrator selectEntityByID(int id) throws Exception {
        log.info("In AdministratorServiceImpl (selectEntityByID, id: " + id + ")");
        DBConnection dbConnection = DBConnection.createConnection();
        log.info("Initializing AdministratorDao");
        AdministratorDao administratorDao = new AdministratorDaoImpl(dbConnection.getConnection());
        log.info("Control transferring to Dao");
        Administrator administrator = administratorDao.selectEntityByID(id);
        log.info("Closing connection");
        dbConnection.closeConnection();
        return administrator;
    }

    @Override
    public void updateEntity(Administrator administrator) throws Exception {
        log.info("In AdministratorServiceImpl (updateEntity, administrator: " + administrator + ")");
        DBConnection dbConnection = DBConnection.createConnection();
        log.info("Initializing AdministratorDao");
        AdministratorDao administratorDao = new AdministratorDaoImpl(dbConnection.getConnection());
        log.info("Control transferring to Dao");
        administratorDao.updateEntity(administrator);
        log.info("Closing connection");
        dbConnection.closeConnection();
    }

    @Override
    public void insertEntity(Administrator entity) throws Exception {
        log.info("In AdministratorServiceImpl (insertEntity, entity: " + entity + ")");
        DBConnection dbConnection = DBConnection.createConnection();
        log.info("Initializing AdministratorDao");
        AdministratorDao administratorDao = new AdministratorDaoImpl(dbConnection.getConnection());
        log.info("Control transferring to Dao");
        administratorDao.insertEntity(entity);
        log.info("Closing connection");
        dbConnection.closeConnection();
    }

    @Override
    public void deleteEntityByID(int id) throws Exception {
        log.info("In AdministratorServiceImpl (deleteEntityByID, id: " + id + ")");
        DBConnection dbConnection = DBConnection.createConnection();
        log.info("Initializing AdministratorDao");
        AdministratorDao administratorDao = new AdministratorDaoImpl(dbConnection.getConnection());
        log.info("Control transferring to Dao");
        administratorDao.deleteEntityByID(id);
        log.info("Closing connection");
        dbConnection.closeConnection();
    }

    @Override
    public Administrator selectAdministratorByUserID(int userID) throws Exception {
        log.info("In AdministratorServiceImpl (selectAdministratorByUserID, userID: " + userID + ")");
        DBConnection dbConnection = DBConnection.createConnection();
        log.info("Initializing AdministratorDao");
        AdministratorDao administratorDao = new AdministratorDaoImpl(dbConnection.getConnection());
        log.info("Control transferring to Dao");
        Administrator administrator = administratorDao.selectAdministratorByUserID(userID);
        log.info("Closing connection");
        dbConnection.closeConnection();
        return administrator;
    }

    @Override
    public Collection<Administrator> getAllEntitiesByUserID(ArrayList<String> usersID) throws Exception {
        log.info("In AdministratorServiceImpl (getAllEntitiesByUserID, usersID: " + usersID.toString() + ")");
        DBConnection dbConnection = DBConnection.createConnection();
        log.info("Initializing AdministratorDao");
        AdministratorDao administratorDao = new AdministratorDaoImpl(dbConnection.getConnection());
        ArrayList<Administrator> administratorList = new ArrayList<>();
        log.info("Control transferring to Dao");
        for (String userID : usersID) {
            administratorList.add(administratorDao.selectAdministratorByUserID(Integer.parseInt(userID)));
        }
        log.info("Closing connection");
        dbConnection.closeConnection();
        return administratorList;
    }

    @Override
    public void raiseToAdministrator(int userID) throws Exception {
        log.info("In AdministratorServiceImpl (raiseToAdministrator, userID: " + userID + ")");
        DBConnection dbConnection = DBConnection.createConnection();
        log.info("Initializing AdministratorDao");
        AdministratorDao administratorDao = new AdministratorDaoImpl(dbConnection.getConnection());
        log.info("Initializing TeacherDao");
        TeacherDao teacherDao = new TeacherDaoImpl(dbConnection.getConnection());
        log.info("Initializing UserDao");
        UserDao userDao = new UserDaoImpl(dbConnection.getConnection());
        log.info("Starting transaction Dao");
        dbConnection.startTransaction();
        try {
            log.info("Control transferring to Dao");
            Teacher teacher = teacherDao.selectTeacherByUserID(userID);
            administratorDao.raiseToAdministrator(teacher);
            teacherDao.deleteEntityByID(teacher.getId());
            userDao.updateUserRole("ADMINISTRATOR", userID);
            log.info("Committing transaction");
            dbConnection.commit();
        } catch (Exception exception) {
            log.error("Error! " + exception);
            log.info("Rollback a transaction");
            dbConnection.rollback();
            throw new Exception();
        } finally {
            log.info("Closing connection");
            dbConnection.closeConnection();
        }
    }

    @Override
    public int getAllAdministratorsCount() throws Exception {
        log.info("In AdministratorServiceImpl (getAllAdministratorsCount)");
        DBConnection dbConnection = DBConnection.createConnection();
        log.info("Initializing AdministratorDao");
        AdministratorDao administratorDao = new AdministratorDaoImpl(dbConnection.getConnection());
        log.info("Control transferring to Dao");
        int result = administratorDao.getAllAdministratorsCount();
        log.info("Closing connection");
        dbConnection.closeConnection();
        return result;
    }
}