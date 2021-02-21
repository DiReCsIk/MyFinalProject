package com.MyServlet.DBManager.Service.Impl;

import com.MyServlet.DBManager.DBConnection;
import com.MyServlet.DBManager.Dao.Impl.StudentDaoImpl;
import com.MyServlet.DBManager.Dao.Impl.TeacherDaoImpl;
import com.MyServlet.DBManager.Dao.Impl.UserDaoImpl;
import com.MyServlet.DBManager.Dao.StudentDao;
import com.MyServlet.DBManager.Dao.TeacherDao;
import com.MyServlet.DBManager.Dao.UserDao;
import com.MyServlet.DBManager.Service.TeacherService;
import com.MyServlet.Entity.Student;
import com.MyServlet.Entity.Teacher;
import com.MyServlet.Exception.ConnectionException;
import com.MyServlet.Exception.DAOException;
import com.MyServlet.Exception.ServiceException;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class TeacherServiceImpl implements TeacherService {
    private static final Logger log = Logger.getLogger(AdministratorServiceImpl.class.getName());

    @Override
    public Collection<Teacher> getAllEntities() throws ConnectionException, ServiceException {
        log.info("In TeacherServiceImpl (getAllEntities)");
        try (DBConnection dbConnection = DBConnection.createConnection()) {
            log.info("Initializing TeacherDao");
            TeacherDao teacherDao = new TeacherDaoImpl(dbConnection.getConnection());
            log.info("Control transferring to Dao");
            Collection<Teacher> allEntities = teacherDao.getAllEntities();
            log.info("Closing connection");
            return allEntities;
        } catch (DAOException daoException) {
            log.error("Error!", daoException);
            throw new ServiceException(daoException.getMessage(), daoException);
        }
    }

    @Override
    public Teacher selectEntityByID(int id) throws ConnectionException, ServiceException {
        log.info("In TeacherServiceImpl (selectEntityByID, id: " + id + ")");
        try (DBConnection dbConnection = DBConnection.createConnection()) {
            log.info("Initializing TeacherDao");
            TeacherDao teacherDao = new TeacherDaoImpl(dbConnection.getConnection());
            log.info("Control transferring to Dao");
            Teacher teacher = teacherDao.selectEntityByID(id);
            log.info("Closing connection");
            return teacher;
        } catch (DAOException daoException) {
            log.error("Error!", daoException);
            throw new ServiceException(daoException.getMessage(), daoException);
        }
    }

    @Override
    public void updateEntity(Teacher teacher) throws ConnectionException, ServiceException {
        log.info("In TeacherServiceImpl (updateEntity, teacher: " + teacher + ")");
        try (DBConnection dbConnection = DBConnection.createConnection()) {
            log.info("Initializing TeacherDao");
            TeacherDao teacherDao = new TeacherDaoImpl(dbConnection.getConnection());
            log.info("Control transferring to Dao");
            teacherDao.updateEntity(teacher);
            log.info("Closing connection");
        } catch (DAOException daoException) {
            log.error("Error!", daoException);
            throw new ServiceException(daoException.getMessage(), daoException);
        }
    }

    @Override
    public void insertEntity(Teacher entity) throws ConnectionException, ServiceException {
        log.info("In TeacherServiceImpl (insertEntity, entity: " + entity + ")");
        try (DBConnection dbConnection = DBConnection.createConnection()) {
            log.info("Initializing TeacherDao");
            TeacherDao teacherDao = new TeacherDaoImpl(dbConnection.getConnection());
            log.info("Control transferring to Dao");
            teacherDao.insertEntity(entity);
            log.info("Closing connection");
        } catch (DAOException daoException) {
            log.error("Error!", daoException);
            throw new ServiceException(daoException.getMessage(), daoException);
        }
    }

    @Override
    public void deleteEntityByID(int id) throws ConnectionException, ServiceException {
        log.info("In TeacherServiceImpl (deleteEntityByID, id: " + id + ")");
        try (DBConnection dbConnection = DBConnection.createConnection()) {
            log.info("Initializing TeacherDao");
            TeacherDao teacherDao = new TeacherDaoImpl(dbConnection.getConnection());
            log.info("Control transferring to Dao");
            teacherDao.deleteEntityByID(id);
            log.info("Closing connection");
        } catch (DAOException daoException) {
            log.error("Error!", daoException);
            throw new ServiceException(daoException.getMessage(), daoException);
        }
    }

    @Override
    public Teacher selectTeacherByUserID(int userID) throws ConnectionException, ServiceException {
        log.info("In TeacherServiceImpl (selectTeacherByUserID, userID: " + userID + ")");
        try (DBConnection dbConnection = DBConnection.createConnection()) {
            log.info("Initializing TeacherDao");
            TeacherDao teacherDao = new TeacherDaoImpl(dbConnection.getConnection());
            log.info("Control transferring to Dao");
            Teacher teacher = teacherDao.selectTeacherByUserID(userID);
            log.info("Closing connection");
            return teacher;
        } catch (DAOException daoException) {
            log.error("Error!", daoException);
            throw new ServiceException(daoException.getMessage(), daoException);
        }
    }

    @Override
    public int selectTeacherCourseID(int teacherID) throws ConnectionException, ServiceException {
        log.info("In TeacherServiceImpl (selectTeacherCourseID, teacherID: " + teacherID + ")");
        try (DBConnection dbConnection = DBConnection.createConnection()) {
            log.info("Initializing TeacherDao");
            TeacherDao teacherDao = new TeacherDaoImpl(dbConnection.getConnection());
            log.info("Control transferring to Dao");
            int courseID = teacherDao.selectTeacherCourseID(teacherID);
            log.info("Closing connection");
            return courseID;
        } catch (DAOException daoException) {
            log.error("Error!", daoException);
            throw new ServiceException(daoException.getMessage(), daoException);
        }
    }

    @Override
    public Map<String, ArrayList<String>> selectStudentsDataOnTeacherCourse(int teacherID, int pageNumber, int rowCount) throws ConnectionException, ServiceException {
        log.info("In TeacherServiceImpl (selectTeacherNameAndSurnameByID, teacherID: " + teacherID + ", pageNumber: " +
                pageNumber + ", rowCount: " + rowCount + ")");
        try (DBConnection dbConnection = DBConnection.createConnection()) {
            log.info("Initializing TeacherDao");
            TeacherDao teacherDao = new TeacherDaoImpl(dbConnection.getConnection());
            log.info("Control transferring to Dao");
            Map<String, ArrayList<String>> map = teacherDao.selectStudentsDataOnTeacherCourse(teacherID, pageNumber, rowCount);
            log.info("Closing connection");
            return map;
        } catch (DAOException daoException) {
            log.error("Error!", daoException);
            throw new ServiceException(daoException.getMessage(), daoException);
        }
    }

    @Override
    public Collection<String> selectTeacherNameAndSurnameByID(ArrayList<Integer> teachersID) throws ConnectionException, ServiceException {
        log.info("In TeacherServiceImpl (selectTeacherNameAndSurnameByID, teachersID: " + teachersID.toString() + ")");
        try (DBConnection dbConnection = DBConnection.createConnection()) {
            log.info("Initializing TeacherDao");
            TeacherDao teacherDao = new TeacherDaoImpl(dbConnection.getConnection());
            Collection<String> result = new ArrayList<>();
            log.info("Control transferring to Dao");
            for (Integer integer : teachersID) {
                result.add(teacherDao.selectTeacherNameAndSurnameByID(integer));
            }
            log.info("Closing connection");
            return result;
        } catch (DAOException daoException) {
            log.error("Error!", daoException);
            throw new ServiceException(daoException.getMessage(), daoException);
        }
    }

    @Override
    public Collection<Teacher> getAllEntitiesByUserID(ArrayList<String> usersID) throws ConnectionException, ServiceException {
        log.info("In TeacherServiceImpl (getAllEntitiesByUserID, usersID: " + usersID.toString() + ")");
        try (DBConnection dbConnection = DBConnection.createConnection()) {
            log.info("Initializing TeacherDao");
            TeacherDao teacherDao = new TeacherDaoImpl(dbConnection.getConnection());
            Collection<Teacher> teacherList = new ArrayList<>();
            log.info("Control transferring to Dao");
            for (String userID : usersID) {
                teacherList.add(teacherDao.selectTeacherByUserID(Integer.parseInt(userID)));
            }
            log.info("Closing connection");
            return teacherList;
        } catch (DAOException daoException) {
            log.error("Error!", daoException);
            throw new ServiceException(daoException.getMessage(), daoException);
        }
    }

    @Override
    public int getAllTeachersCount() throws ConnectionException, ServiceException {
        log.info("In TeacherServiceImpl (getAllTeachersCount)");
        try (DBConnection dbConnection = DBConnection.createConnection()) {
            log.info("Initializing TeacherDao");
            TeacherDao teacherDao = new TeacherDaoImpl(dbConnection.getConnection());
            log.info("Control transferring to Dao");
            int result = teacherDao.getAllTeachersCount();
            log.info("Closing connection");
            return result;
        } catch (DAOException daoException) {
            log.error("Error!", daoException);
            throw new ServiceException(daoException.getMessage(), daoException);
        }
    }

    @Override
    public void raiseToTeacher(int userID) throws ConnectionException, ServiceException {
        log.info("In TeacherServiceImpl (raiseToTeacher, userID: " + userID + ")");
        DBConnection dbConnection = DBConnection.createConnection();
        log.info("Initializing StudentDao");
        StudentDao studentDao = new StudentDaoImpl(dbConnection.getConnection());
        log.info("Initializing TeacherDao");
        TeacherDao teacherDao = new TeacherDaoImpl(dbConnection.getConnection());
        log.info("Initializing UserDao");
        UserDao userDao = new UserDaoImpl(dbConnection.getConnection());
        log.info("Starting transaction Dao");
        dbConnection.startTransaction();
        log.info("Control transferring to Dao");
        try {
            Student student = studentDao.selectStudentByUserID(userID);
            teacherDao.raiseToTeacher(student);
            studentDao.deleteEntityByID(student.getId());
            userDao.updateUserRole("TEACHER", userID);
            log.info("Committing transaction");
            dbConnection.commit();
        } catch (DAOException daoException) {
            log.error("Error! " + daoException);
            log.info("Rollback a transaction");
            dbConnection.rollback();
            throw new ServiceException();
        } finally {
            log.info("Closing connection");
            dbConnection.close();
        }
    }

    @Override
    public Map<String, ArrayList<String>> selectAllTeachersNameAndSurname() throws ConnectionException, ServiceException {
        log.info("In TeacherServiceImpl (selectAllTeachersNameAndSurname)");
        try (DBConnection dbConnection = DBConnection.createConnection()) {
            log.info("Initializing TeacherDao");
            TeacherDao teacherDao = new TeacherDaoImpl(dbConnection.getConnection());
            log.info("Control transferring to Dao");
            Map<String, ArrayList<String>> result = teacherDao.selectAllTeachersNameAndSurname();
            log.info("Closing connection");
            return result;
        } catch (DAOException daoException) {
            log.error("Error!", daoException);
            throw new ServiceException(daoException.getMessage(), daoException);
        }
    }

    @Override
    public Map<String, ArrayList<String>> selectFinishedTeachersData(int studentID) throws ConnectionException, ServiceException {
        log.info("In TeacherServiceImpl (selectFinishedTeachersData, studentID: " + studentID + ")");
        try (DBConnection dbConnection = DBConnection.createConnection()) {
            log.info("Initializing TeacherDao");
            TeacherDao teacherDao = new TeacherDaoImpl(dbConnection.getConnection());
            log.info("Control transferring to Dao");
            Map<String, ArrayList<String>> result = teacherDao.selectFinishedTeachersData(studentID);
            log.info("Closing connection");
            return result;
        } catch (DAOException daoException) {
            log.error("Error!", daoException);
            throw new ServiceException(daoException.getMessage(), daoException);
        }
    }

    @Override
    public Collection<String> selectTeachersCourseID(ArrayList<Teacher> teachers) throws ConnectionException, ServiceException {
        log.info("In TeacherServiceImpl (selectTeachersCourseID, teachers: " + teachers + ")");
        try (DBConnection dbConnection = DBConnection.createConnection()) {
            log.info("Initializing TeacherDao");
            TeacherDao teacherDao = new TeacherDaoImpl(dbConnection.getConnection());
            Collection<String> courseID = new ArrayList<>();
            log.info("Control transferring to Dao");
            for (Teacher teacher : teachers) {
                courseID.add(String.valueOf(teacherDao.selectTeacherCourseID(teacher.getId())));
            }
            log.info("Closing connection");
            return courseID;
        } catch (DAOException daoException) {
            log.error("Error!", daoException);
            throw new ServiceException(daoException.getMessage(), daoException);
        }
    }

    @Override
    public Collection<Integer> selectTeacherStudentsID(int teacherID) throws ConnectionException, ServiceException {
        log.info("In TeacherServiceImpl (selectTeacherStudentsID, teacherID: " + teacherID + ")");
        try (DBConnection dbConnection = DBConnection.createConnection()) {
            log.info("Initializing TeacherDao");
            TeacherDao teacherDao = new TeacherDaoImpl(dbConnection.getConnection());
            log.info("Control transferring to Dao");
            Collection<Integer> list = teacherDao.selectTeacherStudentsID(teacherID);
            log.info("Closing connection");
            return list;
        } catch (DAOException daoException) {
            log.error("Error!", daoException);
            throw new ServiceException(daoException.getMessage(), daoException);
        }
    }
}
