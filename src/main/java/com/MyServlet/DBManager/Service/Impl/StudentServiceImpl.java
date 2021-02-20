package com.MyServlet.DBManager.Service.Impl;

import com.MyServlet.DBManager.DBConnection;
import com.MyServlet.DBManager.Dao.Impl.StudentDaoImpl;
import com.MyServlet.DBManager.Dao.Impl.TeacherDaoImpl;
import com.MyServlet.DBManager.Dao.Impl.UserDaoImpl;
import com.MyServlet.DBManager.Dao.StudentDao;
import com.MyServlet.DBManager.Dao.TeacherDao;
import com.MyServlet.DBManager.Dao.UserDao;
import com.MyServlet.DBManager.Service.StudentService;
import com.MyServlet.Entity.Student;
import com.MyServlet.Entity.Teacher;
import com.MyServlet.Entity.User;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Collection;

public class StudentServiceImpl implements StudentService {
    private static final Logger log = Logger.getLogger(StudentServiceImpl.class.getName());

    @Override
    public Collection<Student> getAllEntities() throws Exception {
        log.info("In StudentServiceImpl (getAllEntities)");
        DBConnection dbConnection = DBConnection.createConnection();
        StudentDao studentDao = new StudentDaoImpl(dbConnection.getConnection());
        log.info("Control transferring to Dao");
        Collection<Student> allEntities = studentDao.getAllEntities();
        log.info("Closing connection");
        dbConnection.closeConnection();
        return allEntities;
    }

    @Override
    public Student selectEntityByID(int id) throws Exception {
        log.info("In StudentServiceImpl (selectEntityByID, id: " + id + ")");
        DBConnection dbConnection = DBConnection.createConnection();
        log.info("Initializing StudentDao");
        StudentDao studentDao = new StudentDaoImpl(dbConnection.getConnection());
        log.info("Control transferring to Dao");
        Student student = studentDao.selectEntityByID(id);
        log.info("Closing connection");
        dbConnection.closeConnection();
        return student;
    }

    @Override
    public Student selectStudentByUserID(int userID) throws Exception {
        log.info("In StudentServiceImpl (selectStudentByUserID, userID: " + userID + ")");
        DBConnection dbConnection = DBConnection.createConnection();
        log.info("Initializing StudentDao");
        StudentDao studentDao = new StudentDaoImpl(dbConnection.getConnection());
        log.info("Control transferring to Dao");
        Student student = studentDao.selectStudentByUserID(userID);
        log.info("Closing connection");
        dbConnection.closeConnection();
        return student;
    }

    @Override
    public Collection<Student> getAllEntitiesByUserID(ArrayList<String> usersID) throws Exception {
        log.info("In StudentServiceImpl (getAllEntitiesByUserID, usersID: " + usersID.toString() + ")");
        DBConnection dbConnection = DBConnection.createConnection();
        log.info("Initializing StudentDao");
        StudentDao studentDao = new StudentDaoImpl(dbConnection.getConnection());
        ArrayList<Student> studentList = new ArrayList<>();
        log.info("Control transferring to Dao");
        for (String userID : usersID) {
            studentList.add(studentDao.selectStudentByUserID(Integer.parseInt(userID)));
        }
        log.info("Closing connection");
        dbConnection.closeConnection();
        return studentList;
    }

    @Override
    public int getAllStudentsCount() throws Exception {
        log.info("In StudentServiceImpl (getAllStudentsCount)");
        DBConnection dbConnection = DBConnection.createConnection();
        log.info("Initializing StudentDao");
        StudentDao studentDao = new StudentDaoImpl(dbConnection.getConnection());
        log.info("Control transferring to Dao");
        int result = studentDao.getAllStudentsCount();
        log.info("Closing connection");
        dbConnection.closeConnection();
        return result;
    }

    @Override
    public void unbanStudent(int userID) throws Exception {
        log.info("In StudentServiceImpl (unbanStudent, userID: " + userID + ")");
        DBConnection dbConnection = DBConnection.createConnection();
        log.info("Initializing StudentDao");
        StudentDao studentDao = new StudentDaoImpl(dbConnection.getConnection());
        log.info("Control transferring to Dao");
        studentDao.unbanStudent(userID);
        log.info("Closing connection");
        dbConnection.closeConnection();
    }

    @Override
    public void banStudent(int userID) throws Exception {
        log.info("In StudentServiceImpl (banStudent, userID: " + userID + ")");
        DBConnection dbConnection = DBConnection.createConnection();
        log.info("Initializing StudentDao");
        StudentDao studentDao = new StudentDaoImpl(dbConnection.getConnection());
        log.info("Control transferring to Dao");
        studentDao.banStudent(userID);
        log.info("Closing connection");
        dbConnection.closeConnection();
    }

    @Override
    public void declineToStudent(int userID) throws Exception {
        log.info("In StudentServiceImpl (declineToStudent, userID: " + userID + ")");
        DBConnection dbConnection = DBConnection.createConnection();
        log.info("Initializing TeacherDao");
        TeacherDao teacherDao = new TeacherDaoImpl(dbConnection.getConnection());
        log.info("Initializing StudentDao");
        StudentDao studentDao = new StudentDaoImpl(dbConnection.getConnection());
        log.info("Initializing UserDao");
        UserDao userDao = new UserDaoImpl(dbConnection.getConnection());
        log.info("Starting transaction Dao");
        dbConnection.startTransaction();
        try {
            log.info("Control transferring to Dao");
            Teacher teacher = teacherDao.selectTeacherByUserID(userID);
            studentDao.declineToStudent(teacher);
            teacherDao.deleteEntityByID(teacher.getId());
            userDao.updateUserRole("STUDENT", userID);
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
    public void updateEntity(Student student) throws Exception {
        log.info("In StudentServiceImpl (updateEntity, student: " + student.toString() + ")");
        DBConnection dbConnection = DBConnection.createConnection();
        log.info("Initializing StudentDao");
        StudentDao studentDao = new StudentDaoImpl(dbConnection.getConnection());
        log.info("Control transferring to Dao");
        studentDao.updateEntity(student);
        log.info("Closing connection");
        dbConnection.closeConnection();
    }

    @Override
    public void insertEntity(Student entity) throws Exception {
        log.info("In StudentServiceImpl (insertEntity, entity: " + entity.toString() + ")");
        DBConnection dbConnection = DBConnection.createConnection();
        log.info("Initializing StudentDao");
        StudentDao studentDao = new StudentDaoImpl(dbConnection.getConnection());
        UserDao userDao = new UserDaoImpl(dbConnection.getConnection());
        log.info("Starting transaction Dao");
        dbConnection.startTransaction();
        User user = new User(entity.getEmail(), entity.getPassword(), entity.getUserRole());
        try {
            log.info("Control transferring to Dao");
            userDao.insertEntity(user);
            user = userDao.selectUserByEmail(entity.getEmail());
            entity.setUserID(user.getId());
            studentDao.insertEntity(entity);
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
    public void deleteEntityByID(int id) throws Exception {
        log.info("In StudentServiceImpl (deleteEntityByID, id: " + id + ")");
        DBConnection dbConnection = DBConnection.createConnection();
        log.info("Initializing StudentDao");
        StudentDao studentDao = new StudentDaoImpl(dbConnection.getConnection());
        log.info("Control transferring to Dao");
        studentDao.deleteEntityByID(id);
        log.info("Closing connection");
        dbConnection.closeConnection();
    }
}
