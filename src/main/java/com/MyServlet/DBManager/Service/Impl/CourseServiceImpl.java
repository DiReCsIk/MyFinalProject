package com.MyServlet.DBManager.Service.Impl;

import com.MyServlet.DBManager.DBConnection;
import com.MyServlet.DBManager.Dao.CourseDao;
import com.MyServlet.DBManager.Dao.Impl.CourseDaoImpl;
import com.MyServlet.DBManager.Service.CourseService;
import com.MyServlet.Entity.Course;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Collection;

public class CourseServiceImpl implements CourseService {
    private static final Logger log = Logger.getLogger(CourseServiceImpl.class.getName());

    @Override
    public Collection<Course> getAllEntities() throws Exception {
        log.info("In CourseServiceImpl (getAllEntities)");
        DBConnection dbConnection = DBConnection.createConnection();
        log.info("Initializing CourseDao");
        CourseDao courseDao = new CourseDaoImpl(dbConnection.getConnection());
        log.info("Control transferring to Dao");
        Collection<Course> allEntities = courseDao.getAllEntities();
        log.info("Closing connection");
        dbConnection.closeConnection();
        return allEntities;
    }

    @Override
    public Collection<Course> getAllEntities(int pageNumber, int rowCount) throws Exception {
        log.info("In CourseServiceImpl (getAllEntities, pageNumber: " + pageNumber + ", rowCount: " + rowCount + ")");
        DBConnection dbConnection = DBConnection.createConnection();
        log.info("Initializing CourseDao");
        CourseDao courseDao = new CourseDaoImpl(dbConnection.getConnection());
        log.info("Control transferring to Dao");
        Collection<Course> allEntities = courseDao.getAllEntities(pageNumber, rowCount);
        log.info("Closing connection");
        dbConnection.closeConnection();
        return allEntities;
    }

    @Override
    public Course selectEntityByID(int id) throws Exception {
        log.info("In CourseServiceImpl (selectEntityByID, id: " + id + ")");
        DBConnection dbConnection = DBConnection.createConnection();
        log.info("Initializing CourseDao");
        CourseDao courseDao = new CourseDaoImpl(dbConnection.getConnection());
        log.info("Control transferring to Dao");
        Course course = courseDao.selectEntityByID(id);
        log.info("Closing connection");
        dbConnection.closeConnection();
        return course;
    }

    @Override
    public void updateEntity(Course course) throws Exception {
        log.info("In CourseServiceImpl (updateEntity, course: " + course + ")");
        DBConnection dbConnection = DBConnection.createConnection();
        log.info("Initializing CourseDao");
        CourseDao courseDao = new CourseDaoImpl(dbConnection.getConnection());
        log.info("Control transferring to Dao");
        courseDao.updateEntity(course);
        log.info("Closing connection");
        dbConnection.closeConnection();
    }

    @Override
    public void insertEntity(Course entity) throws Exception {
        log.info("In CourseServiceImpl (insertEntity, course: " + entity + ")");
        DBConnection dbConnection = DBConnection.createConnection();
        log.info("Initializing CourseDao");
        CourseDao courseDao = new CourseDaoImpl(dbConnection.getConnection());
        log.info("Control transferring to Dao");
        courseDao.insertEntity(entity);
        log.info("Closing connection");
        dbConnection.closeConnection();
    }

    @Override
    public void deleteEntityByID(int id) throws Exception {
        log.info("In CourseServiceImpl (deleteEntityByID, id: " + id + ")");
        DBConnection dbConnection = DBConnection.createConnection();
        log.info("Initializing CourseDao");
        CourseDao courseDao = new CourseDaoImpl(dbConnection.getConnection());
        log.info("Control transferring to Dao");
        courseDao.deleteEntityByID(id);
        log.info("Closing connection");
        dbConnection.closeConnection();
    }

    @Override
    public Collection<Course> selectAllStudentAvailableCourses(int pageNumber, int rowCount, String courseName) throws Exception {
        log.info("In CourseServiceImpl (selectAllStudentAvailableCourses, pageNumber: " + pageNumber +
                ", rowCount: " + rowCount + ", courseName: " + courseName + ")");
        DBConnection dbConnection = DBConnection.createConnection();
        log.info("Initializing CourseDao");
        CourseDao courseDao = new CourseDaoImpl(dbConnection.getConnection());
        log.info("Control transferring to Dao");
        Collection<Course> collection = courseDao.selectAllStudentAvailableCourses(pageNumber, rowCount, courseName);
        log.info("Closing connection");
        dbConnection.closeConnection();
        return collection;
    }

    @Override
    public Collection<Course> selectAllStudentInProgressCourses(int studentID, int pageNumber, int rowCount) throws Exception {
        log.info("In CourseServiceImpl (selectAllStudentInProgressCourses, studentID: " + studentID +
                ", pageNumber: " + pageNumber + ", rowCount: " + rowCount + ")");
        DBConnection dbConnection = DBConnection.createConnection();
        log.info("Initializing CourseDao");
        CourseDao courseDao = new CourseDaoImpl(dbConnection.getConnection());
        log.info("Control transferring to Dao");
        Collection<Course> collection = courseDao.selectAllStudentInProgressCourses(studentID, pageNumber, rowCount);
        log.info("Closing connection");
        dbConnection.closeConnection();
        return collection;
    }

    @Override
    public Collection<Course> selectAllStudentFinishedCourses(int studentID, int pageNumber, int rowCount) throws Exception {
        log.info("In CourseServiceImpl (selectAllStudentFinishedCourses, studentID: " + studentID +
                ", pageNumber: " + pageNumber + ", rowCount: " + rowCount + ")");
        DBConnection dbConnection = DBConnection.createConnection();
        log.info("Initializing CourseDao");
        CourseDao courseDao = new CourseDaoImpl(dbConnection.getConnection());
        log.info("Control transferring to Dao");
        Collection<Course> collection = courseDao.selectAllStudentFinishedCourses(studentID, pageNumber, rowCount);
        log.info("Closing connection");
        dbConnection.closeConnection();
        return collection;
    }

    @Override
    public Collection<Course> selectAllStudentNotStartedCourses(int studentID, int pageNumber, int rowCount) throws Exception {
        log.info("In CourseServiceImpl (selectAllStudentNotStartedCourses, studentID: " + studentID +
                ", pageNumber: " + pageNumber + ", rowCount: " + rowCount + ")");
        DBConnection dbConnection = DBConnection.createConnection();
        log.info("Initializing CourseDao");
        CourseDao courseDao = new CourseDaoImpl(dbConnection.getConnection());
        log.info("Control transferring to Dao");
        Collection<Course> collection = courseDao.selectAllStudentNotStartedCourses(studentID, pageNumber, rowCount);
        log.info("Closing connection");
        dbConnection.closeConnection();
        return collection;
    }

    @Override
    public Collection<Course> selectAllStudentAvailableCoursesRegistered(int studentID, int pageNumber, int rowCount, String courseName) throws Exception {
        log.info("In CourseServiceImpl (selectAllStudentAvailableCoursesRegistered, studentID: " + studentID +
                ", pageNumber: " + pageNumber + ", rowCount: " + rowCount + " , courseName: +" + courseName + ")");
        DBConnection dbConnection = DBConnection.createConnection();
        log.info("Initializing CourseDao");
        CourseDao courseDao = new CourseDaoImpl(dbConnection.getConnection());
        log.info("Control transferring to Dao");
        Collection<Course> collection = courseDao.selectAllStudentAvailableCoursesRegistered(studentID, pageNumber, rowCount, courseName);
        log.info("Closing connection");
        dbConnection.closeConnection();
        return collection;
    }

    @Override
    public int selectAvailableCoursesCountRegistered(int studentID, String courseName) throws Exception {
        log.info("In CourseServiceImpl (selectAvailableCoursesCountRegistered, studentID: " + studentID +
                ", courseName: " + courseName + ")");
        DBConnection dbConnection = DBConnection.createConnection();
        log.info("Initializing CourseDao");
        CourseDao courseDao = new CourseDaoImpl(dbConnection.getConnection());
        log.info("Control transferring to Dao");
        int result = courseDao.selectAvailableCoursesCountRegistered(studentID, courseName);
        log.info("Closing connection");
        dbConnection.closeConnection();
        return result;
    }

    @Override
    public int selectAvailableCoursesCount(String courseName) throws Exception {
        log.info("In CourseServiceImpl (selectAvailableCoursesCount, courseName: " + courseName + ")");
        DBConnection dbConnection = DBConnection.createConnection();
        log.info("Initializing CourseDao");
        CourseDao courseDao = new CourseDaoImpl(dbConnection.getConnection());
        log.info("Control transferring to Dao");
        int result = courseDao.selectAvailableCoursesCount(courseName);
        log.info("Closing connection");
        dbConnection.closeConnection();
        return result;
    }

    @Override
    public int selectFinishedCoursesCount(int studentID) throws Exception {
        log.info("In CourseServiceImpl (selectFinishedCoursesCount, studentID: " + studentID + ")");
        DBConnection dbConnection = DBConnection.createConnection();
        log.info("Initializing CourseDao");
        CourseDao courseDao = new CourseDaoImpl(dbConnection.getConnection());
        log.info("Control transferring to Dao");
        int result = courseDao.selectFinishedCoursesCount(studentID);
        log.info("Closing connection");
        dbConnection.closeConnection();
        return result;
    }

    @Override
    public int selectInProgressCoursesCount(int studentID) throws Exception {
        log.info("In CourseServiceImpl (selectInProgressCoursesCount, studentID: " + studentID + ")");
        DBConnection dbConnection = DBConnection.createConnection();
        log.info("Initializing CourseDao");
        CourseDao courseDao = new CourseDaoImpl(dbConnection.getConnection());
        log.info("Control transferring to Dao");
        int result = courseDao.selectInProgressCoursesCount(studentID);
        log.info("Closing connection");
        dbConnection.closeConnection();
        return result;
    }

    @Override
    public int selectNotStartedCoursesCount(int studentID) throws Exception {
        log.info("In CourseServiceImpl (selectNotStartedCoursesCount, studentID: " + studentID + ")");
        DBConnection dbConnection = DBConnection.createConnection();
        log.info("Initializing CourseDao");
        CourseDao courseDao = new CourseDaoImpl(dbConnection.getConnection());
        log.info("Control transferring to Dao");
        int result = courseDao.selectNotStartedCoursesCount(studentID);
        log.info("Closing connection");
        dbConnection.closeConnection();
        return result;
    }

    @Override
    public void updateStudentMark(String mark, int studentID, int courseID) throws Exception {
        log.info("In CourseServiceImpl (updateStudentMark, mark: " + mark + ",studentID: " + studentID +
                ", courseID: " + courseID + ")");
        DBConnection dbConnection = DBConnection.createConnection();
        log.info("Initializing CourseDao");
        CourseDao courseDao = new CourseDaoImpl(dbConnection.getConnection());
        log.info("Control transferring to Dao");
        courseDao.updateStudentMark(mark, studentID, courseID);
        log.info("Closing connection");
        dbConnection.closeConnection();
    }

    @Override
    public int selectStudentsCountOnCourse(int courseID) throws Exception {
        log.info("In CourseServiceImpl (selectStudentsCountOnCourse, courseID: " + courseID + ")");
        DBConnection dbConnection = DBConnection.createConnection();
        log.info("Initializing CourseDao");
        CourseDao courseDao = new CourseDaoImpl(dbConnection.getConnection());
        log.info("Control transferring to Dao");
        int result = courseDao.selectStudentsCountOnCourse(courseID);
        log.info("Closing connection");
        dbConnection.closeConnection();
        return result;
    }

    @Override
    public void insertStudentToCourse(int studentID, int courseID) throws Exception {
        log.info("In CourseServiceImpl (selectStudentsCountOnCourse, studentID: " + studentID +
                ", courseID: " + courseID + ")");
        DBConnection dbConnection = DBConnection.createConnection();
        log.info("Initializing CourseDao");
        CourseDao courseDao = new CourseDaoImpl(dbConnection.getConnection());
        log.info("Control transferring to Dao");
        courseDao.insertStudentToCourse(studentID, courseID);
        log.info("Closing connection");
        dbConnection.closeConnection();
    }

    @Override
    public Collection<Integer> selectAllStudentMarks(ArrayList<Integer> coursesID, int studentID) throws Exception {
        log.info("In CourseServiceImpl (selectAllStudentMarks, coursesID: " + coursesID.toString() +
                ", studentID: " + studentID + ")");
        DBConnection dbConnection = DBConnection.createConnection();
        CourseDao courseDao = new CourseDaoImpl(dbConnection.getConnection());
        log.info("Initializing CourseDao");
        ArrayList<Integer> result = new ArrayList<>();
        log.info("Control transferring to Dao");
        for (Integer integer : coursesID) {
            result.add(courseDao.selectStudentMarkOnCourse(studentID, integer));
        }
        log.info("Closing connection");
        dbConnection.closeConnection();
        return result;
    }

    @Override
    public int selectCourseCount() throws Exception {
        log.info("In CourseServiceImpl (selectCourseCount)");
        DBConnection dbConnection = DBConnection.createConnection();
        log.info("Initializing CourseDao");
        CourseDao courseDao = new CourseDaoImpl(dbConnection.getConnection());
        log.info("Control transferring to Dao");
        int result = courseDao.selectCourseCount();
        log.info("Closing connection");
        dbConnection.closeConnection();
        return result;
    }

    @Override
    public Collection<Course> selectAllFinishedTeacherCourses(int studentID, int teacherID, int pageNumber, int rowCount) throws Exception {
        log.info("In CourseServiceImpl (selectAllFinishedTeacherCourses, studentID: " + studentID + ", teacherID: " +
               teacherID + ", pageNumber: " + pageNumber + ", rowCount: " + rowCount + ")");
        DBConnection dbConnection = DBConnection.createConnection();
        log.info("Initializing CourseDao");
        CourseDao courseDao = new CourseDaoImpl(dbConnection.getConnection());
        log.info("Control transferring to Dao");
        ArrayList<Course> result = (ArrayList<Course>) courseDao.selectAllFinishedTeacherCourses(studentID, teacherID, pageNumber, rowCount);
        log.info("Closing connection");
        dbConnection.closeConnection();
        return result;
    }

    @Override
    public Collection<String> selectAllCoursesNameByStudentID(int studentID) throws Exception {
        log.info("In CourseServiceImpl (selectAllCoursesNameByStudentID, studentID: " + studentID + ")");
        DBConnection dbConnection = DBConnection.createConnection();
        log.info("Initializing CourseDao");
        CourseDao courseDao = new CourseDaoImpl(dbConnection.getConnection());
        log.info("Control transferring to Dao");
        Collection<String> result = courseDao.selectAllCoursesNameByStudentID(studentID);
        log.info("Closing connection");
        dbConnection.closeConnection();
        return result;
    }

    @Override
    public Collection<String> selectAllCoursesName() throws Exception {
        log.info("In CourseServiceImpl (selectAllCoursesName)");
        DBConnection dbConnection = DBConnection.createConnection();
        log.info("Initializing CourseDao");
        CourseDao courseDao = new CourseDaoImpl(dbConnection.getConnection());
        log.info("Control transferring to Dao");
        Collection<String> result = courseDao.selectAllCoursesName();
        log.info("Closing connection");
        dbConnection.closeConnection();
        return result;
    }

    @Override
    public int selectAllFinishedTeacherCoursesCount(int studentID, int teacherID) throws Exception {
        log.info("In CourseServiceImpl (selectAllFinishedTeacherCoursesCount, studentID: " + studentID +
                ", teacherID: " + teacherID + ")");
        DBConnection dbConnection = DBConnection.createConnection();
        log.info("Initializing CourseDao");
        CourseDao courseDao = new CourseDaoImpl(dbConnection.getConnection());
        log.info("Control transferring to Dao");
        int result = courseDao.selectAllFinishedTeacherCoursesCount(studentID, teacherID);
        log.info("Closing connection");
        dbConnection.closeConnection();
        return result;
    }
}
