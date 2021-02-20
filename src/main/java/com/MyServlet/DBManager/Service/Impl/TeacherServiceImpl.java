package com.MyServlet.DBManager.Service.Impl;

import com.MyServlet.DBManager.DBConnection;
import com.MyServlet.DBManager.Dao.AdministratorDao;
import com.MyServlet.DBManager.Dao.Impl.AdministratorDaoImpl;
import com.MyServlet.DBManager.Dao.Impl.StudentDaoImpl;
import com.MyServlet.DBManager.Dao.Impl.TeacherDaoImpl;
import com.MyServlet.DBManager.Dao.Impl.UserDaoImpl;
import com.MyServlet.DBManager.Dao.StudentDao;
import com.MyServlet.DBManager.Dao.TeacherDao;
import com.MyServlet.DBManager.Dao.UserDao;
import com.MyServlet.DBManager.Service.TeacherService;
import com.MyServlet.Entity.Administrator;
import com.MyServlet.Entity.Student;
import com.MyServlet.Entity.Teacher;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class TeacherServiceImpl implements TeacherService {
    private static final Logger log = Logger.getLogger(AdministratorServiceImpl.class.getName());

    @Override
    public Collection<Teacher> getAllEntities() throws Exception {
        log.info("In TeacherServiceImpl (getAllEntities)");
        DBConnection dbConnection = DBConnection.createConnection();
        log.info("Initializing TeacherDao");
        TeacherDao teacherDao = new TeacherDaoImpl(dbConnection.getConnection());
        log.info("Control transferring to Dao");
        Collection<Teacher> allEntities = teacherDao.getAllEntities();
        log.info("Closing connection");
        dbConnection.closeConnection();
        return allEntities;
    }

    @Override
    public Teacher selectEntityByID(int id) throws Exception {
        log.info("In TeacherServiceImpl (selectEntityByID, id: " + id + ")");
        DBConnection dbConnection = DBConnection.createConnection();
        log.info("Initializing TeacherDao");
        TeacherDao teacherDao = new TeacherDaoImpl(dbConnection.getConnection());
        log.info("Control transferring to Dao");
        Teacher teacher = teacherDao.selectEntityByID(id);
        log.info("Closing connection");
        dbConnection.closeConnection();
        return teacher;
    }

    @Override
    public void updateEntity(Teacher teacher) throws Exception {
        log.info("In TeacherServiceImpl (updateEntity, teacher: " + teacher + ")");
        DBConnection dbConnection = DBConnection.createConnection();
        log.info("Initializing TeacherDao");
        TeacherDao teacherDao = new TeacherDaoImpl(dbConnection.getConnection());
        log.info("Control transferring to Dao");
        teacherDao.updateEntity(teacher);
        log.info("Closing connection");
        dbConnection.closeConnection();
    }

    @Override
    public void insertEntity(Teacher entity) throws Exception {
        log.info("In TeacherServiceImpl (insertEntity, entity: " + entity + ")");
        DBConnection dbConnection = DBConnection.createConnection();
        log.info("Initializing TeacherDao");
        TeacherDao teacherDao = new TeacherDaoImpl(dbConnection.getConnection());
        log.info("Control transferring to Dao");
        teacherDao.insertEntity(entity);
        log.info("Closing connection");
        dbConnection.closeConnection();
    }

    @Override
    public void deleteEntityByID(int id) throws Exception {
        log.info("In TeacherServiceImpl (deleteEntityByID, id: " + id + ")");
        DBConnection dbConnection = DBConnection.createConnection();
        log.info("Initializing TeacherDao");
        TeacherDao teacherDao = new TeacherDaoImpl(dbConnection.getConnection());
        log.info("Control transferring to Dao");
        teacherDao.deleteEntityByID(id);
        log.info("Closing connection");
        dbConnection.closeConnection();
    }

    @Override
    public Teacher selectTeacherByUserID(int userID) throws Exception {
        log.info("In TeacherServiceImpl (selectTeacherByUserID, userID: " + userID + ")");
        DBConnection dbConnection = DBConnection.createConnection();
        log.info("Initializing TeacherDao");
        TeacherDao teacherDao = new TeacherDaoImpl(dbConnection.getConnection());
        log.info("Control transferring to Dao");
        Teacher teacher = teacherDao.selectTeacherByUserID(userID);
        log.info("Closing connection");
        dbConnection.closeConnection();
        return teacher;
    }

    @Override
    public int selectTeacherCourseID(int teacherID) throws Exception {
        log.info("In TeacherServiceImpl (selectTeacherCourseID, teacherID: " + teacherID + ")");
        DBConnection dbConnection = DBConnection.createConnection();
        log.info("Initializing TeacherDao");
        TeacherDao teacherDao = new TeacherDaoImpl(dbConnection.getConnection());
        log.info("Control transferring to Dao");
        int courseID = teacherDao.selectTeacherCourseID(teacherID);
        log.info("Closing connection");
        dbConnection.closeConnection();
        return courseID;
    }

    @Override
    public Map<String, ArrayList<String>> selectStudentsDataOnTeacherCourse(int teacherID, int pageNumber, int rowCount) throws Exception {
        log.info("In TeacherServiceImpl (selectTeacherNameAndSurnameByID, teacherID: " + teacherID + ", pageNumber: " +
                pageNumber + ", rowCount: " + rowCount + ")");
        DBConnection dbConnection = DBConnection.createConnection();
        log.info("Initializing TeacherDao");
        TeacherDao teacherDao = new TeacherDaoImpl(dbConnection.getConnection());
        log.info("Control transferring to Dao");
        Map<String, ArrayList<String>> map = teacherDao.selectStudentsDataOnTeacherCourse(teacherID, pageNumber, rowCount);
        log.info("Closing connection");
        dbConnection.closeConnection();
        return map;
    }

    @Override
    public Collection<String> selectTeacherNameAndSurnameByID(ArrayList<Integer> teachersID) throws Exception{
        log.info("In TeacherServiceImpl (selectTeacherNameAndSurnameByID, teachersID: " + teachersID.toString() + ")");
        DBConnection dbConnection = DBConnection.createConnection();
        log.info("Initializing TeacherDao");
        TeacherDao teacherDao = new TeacherDaoImpl(dbConnection.getConnection());
        Collection<String> result = new ArrayList<>();
        log.info("Control transferring to Dao");
        for (Integer integer : teachersID) {
            result.add(teacherDao.selectTeacherNameAndSurnameByID(integer));
        }
        log.info("Closing connection");
        dbConnection.closeConnection();
        return result;
    }

    @Override
    public Collection<Teacher> getAllEntitiesByUserID(ArrayList<String> usersID) throws Exception {
        log.info("In TeacherServiceImpl (getAllEntitiesByUserID, usersID: " + usersID.toString() + ")");
        DBConnection dbConnection = DBConnection.createConnection();
        log.info("Initializing TeacherDao");
        TeacherDao teacherDao = new TeacherDaoImpl(dbConnection.getConnection());
        Collection<Teacher> teacherList = new ArrayList<>();
        log.info("Control transferring to Dao");
        for (String userID : usersID) {
            teacherList.add(teacherDao.selectTeacherByUserID(Integer.parseInt(userID)));
        }
        log.info("Closing connection");
        dbConnection.closeConnection();
        return teacherList;
    }

    @Override
    public int getAllTeachersCount() throws Exception {
        log.info("In TeacherServiceImpl (getAllTeachersCount)");
        DBConnection dbConnection = DBConnection.createConnection();
        log.info("Initializing TeacherDao");
        TeacherDao teacherDao = new TeacherDaoImpl(dbConnection.getConnection());
        log.info("Control transferring to Dao");
        int result = teacherDao.getAllTeachersCount();
        log.info("Closing connection");
        dbConnection.closeConnection();
        return result;
    }

    @Override
    public void raiseToTeacher(int userID) throws Exception {
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
        } catch (Exception exception){
            log.error("Error! " + exception);
            log.info("Rollback a transaction");
            dbConnection.rollback();
            throw new Exception();
        }
        finally {
            log.info("Closing connection");
            dbConnection.closeConnection();
        }
    }

    @Override
    public void declineToTeacher(int userID) throws Exception {
        log.info("In TeacherServiceImpl (declineToTeacher, userID: " + userID + ")");
        DBConnection dbConnection = DBConnection.createConnection();
        log.info("Initializing TeacherDao");
        TeacherDao teacherDao = new TeacherDaoImpl(dbConnection.getConnection());
        log.info("Initializing AdministratorDao");
        AdministratorDao administratorDao = new AdministratorDaoImpl(dbConnection.getConnection());
        log.info("Initializing UserDao");
        UserDao userDao = new UserDaoImpl(dbConnection.getConnection());
        log.info("Starting transaction Dao");
        dbConnection.startTransaction();
        log.info("Control transferring to Dao");
        try {
            Administrator administrator = administratorDao.selectAdministratorByUserID(userID);
            teacherDao.declineToTeacher(administrator);
            administratorDao.deleteEntityByID(administrator.getId());
            userDao.updateUserRole("TEACHER", userID);
            log.info("Committing transaction");
            dbConnection.commit();
        } catch (Exception exception){
            log.error("Error! " + exception);
            log.info("Rollback a transaction");
            dbConnection.rollback();
            throw new Exception();
        }
        finally {
            log.info("Closing connection");
            dbConnection.closeConnection();
        }
    }

    @Override
    public Map<String, ArrayList<String>> selectAllTeachersNameAndSurname() throws Exception {
        log.info("In TeacherServiceImpl (selectAllTeachersNameAndSurname)");
        DBConnection dbConnection = DBConnection.createConnection();
        log.info("Initializing TeacherDao");
        TeacherDao teacherDao = new TeacherDaoImpl(dbConnection.getConnection());
        log.info("Control transferring to Dao");
        Map<String, ArrayList<String>> result = teacherDao.selectAllTeachersNameAndSurname();
        log.info("Closing connection");
        dbConnection.closeConnection();
        return result;
    }

    @Override
    public Map<String, ArrayList<String>> selectFinishedTeachersData(int studentID) throws Exception {
        log.info("In TeacherServiceImpl (selectFinishedTeachersData, studentID: " + studentID + ")");
        DBConnection dbConnection = DBConnection.createConnection();
        log.info("Initializing TeacherDao");
        TeacherDao teacherDao = new TeacherDaoImpl(dbConnection.getConnection());
        log.info("Control transferring to Dao");
        Map<String, ArrayList<String>> result = teacherDao.selectFinishedTeachersData(studentID);
        log.info("Closing connection");
        dbConnection.closeConnection();
        return result;
    }

    @Override
    public Collection<String> selectTeachersCourseID(ArrayList<Teacher> teachers) throws Exception {
        log.info("In TeacherServiceImpl (selectTeachersCourseID, teachers: " + teachers + ")");
        DBConnection dbConnection = DBConnection.createConnection();
        log.info("Initializing TeacherDao");
        TeacherDao teacherDao = new TeacherDaoImpl(dbConnection.getConnection());
        Collection<String> courseID = new ArrayList<>();
        log.info("Control transferring to Dao");
        for (Teacher teacher : teachers) {
            courseID.add(String.valueOf(teacherDao.selectTeacherCourseID(teacher.getId())));
        }
        log.info("Closing connection");
        dbConnection.closeConnection();
        return courseID;
    }

    @Override
    public Collection<Integer> selectTeacherStudentsID(int teacherID) throws Exception {
        log.info("In TeacherServiceImpl (selectTeacherStudentsID, teacherID: " + teacherID + ")");
        DBConnection dbConnection = DBConnection.createConnection();
        log.info("Initializing TeacherDao");
        TeacherDao teacherDao = new TeacherDaoImpl(dbConnection.getConnection());
        log.info("Control transferring to Dao");
        Collection<Integer> list = teacherDao.selectTeacherStudentsID(teacherID);
        log.info("Closing connection");
        dbConnection.closeConnection();
        return list;
    }
}
