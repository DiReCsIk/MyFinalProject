package com.MyServlet.DBManager.Service.Impl;

import com.MyServlet.DBManager.DBConnection;
import com.MyServlet.DBManager.Dao.CourseDao;
import com.MyServlet.DBManager.Dao.Impl.CourseDaoImpl;
import com.MyServlet.DBManager.Service.CourseService;
import com.MyServlet.Entity.Course;
import com.MyServlet.Exception.ConnectionException;
import com.MyServlet.Exception.DAOException;
import com.MyServlet.Exception.ServiceException;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Collection;

public class CourseServiceImpl implements CourseService {
    private static final Logger log = Logger.getLogger(CourseServiceImpl.class.getName());

    @Override
    public Collection<Course> getAllEntities() throws ConnectionException, ServiceException {
        log.info("In CourseServiceImpl (getAllEntities)");
        try (DBConnection dbConnection = DBConnection.createConnection()) {
            log.info("Initializing CourseDao");
            CourseDao courseDao = new CourseDaoImpl(dbConnection.getConnection());
            log.info("Control transferring to Dao");
            Collection<Course> allEntities = courseDao.getAllEntities();
            log.info("Closing connection");
            return allEntities;
        } catch (DAOException daoException) {
            log.error("Error!", daoException);
            throw new ServiceException(daoException.getMessage(), daoException);
        }
    }

    @Override
    public Collection<Course> getAllEntities(int pageNumber, int rowCount) throws ConnectionException, ServiceException {
        log.info("In CourseServiceImpl (getAllEntities, pageNumber: " + pageNumber + ", rowCount: " + rowCount + ")");
        try (DBConnection dbConnection = DBConnection.createConnection()) {
            log.info("Initializing CourseDao");
            CourseDao courseDao = new CourseDaoImpl(dbConnection.getConnection());
            log.info("Control transferring to Dao");
            Collection<Course> allEntities = courseDao.getAllEntities(pageNumber, rowCount);
            log.info("Closing connection");
            return allEntities;
        } catch (DAOException daoException) {
            log.error("Error!", daoException);
            throw new ServiceException(daoException.getMessage(), daoException);
        }
    }

    @Override
    public Course selectEntityByID(int id) throws ConnectionException, ServiceException {
        log.info("In CourseServiceImpl (selectEntityByID, id: " + id + ")");
        try (DBConnection dbConnection = DBConnection.createConnection()) {
            log.info("Initializing CourseDao");
            CourseDao courseDao = new CourseDaoImpl(dbConnection.getConnection());
            log.info("Control transferring to Dao");
            Course course = courseDao.selectEntityByID(id);
            log.info("Closing connection");
            return course;
        } catch (DAOException daoException) {
            log.error("Error!", daoException);
            throw new ServiceException(daoException.getMessage(), daoException);
        }
    }

    @Override
    public void updateEntity(Course course) throws ConnectionException, ServiceException {
        log.info("In CourseServiceImpl (updateEntity, course: " + course + ")");
        try (DBConnection dbConnection = DBConnection.createConnection()) {
            log.info("Initializing CourseDao");
            CourseDao courseDao = new CourseDaoImpl(dbConnection.getConnection());
            log.info("Control transferring to Dao");
            courseDao.updateEntity(course);
            log.info("Closing connection");
        } catch (DAOException daoException) {
            log.error("Error!", daoException);
            throw new ServiceException(daoException.getMessage(), daoException);
        }
    }

    @Override
    public void insertEntity(Course entity) throws ConnectionException, ServiceException {
        log.info("In CourseServiceImpl (insertEntity, course: " + entity + ")");
        try (DBConnection dbConnection = DBConnection.createConnection()) {
            log.info("Initializing CourseDao");
            CourseDao courseDao = new CourseDaoImpl(dbConnection.getConnection());
            log.info("Control transferring to Dao");
            courseDao.insertEntity(entity);
            log.info("Closing connection");
        } catch (DAOException daoException) {
            log.error("Error!", daoException);
            throw new ServiceException(daoException.getMessage(), daoException);
        }
    }

    @Override
    public void deleteEntityByID(int id) throws ConnectionException, ServiceException {
        log.info("In CourseServiceImpl (deleteEntityByID, id: " + id + ")");
        try (DBConnection dbConnection = DBConnection.createConnection()) {
            log.info("Initializing CourseDao");
            CourseDao courseDao = new CourseDaoImpl(dbConnection.getConnection());
            log.info("Control transferring to Dao");
            courseDao.deleteEntityByID(id);
            log.info("Closing connection");
        } catch (DAOException daoException) {
            log.error("Error!", daoException);
            throw new ServiceException(daoException.getMessage(), daoException);
        }
    }

    @Override
    public Collection<Course> selectAllStudentAvailableCourses(String courseName) throws ConnectionException, ServiceException {
        log.info("In CourseServiceImpl (selectAllStudentAvailableCourses, courseName: " + courseName + ")");
        try (DBConnection dbConnection = DBConnection.createConnection()) {
            log.info("Initializing CourseDao");
            CourseDao courseDao = new CourseDaoImpl(dbConnection.getConnection());
            log.info("Control transferring to Dao");
            Collection<Course> collection = courseDao.selectAllStudentAvailableCourses(courseName);
            log.info("Closing connection");
            return collection;
        } catch (DAOException daoException) {
            log.error("Error!", daoException);
            throw new ServiceException(daoException.getMessage(), daoException);
        }
    }

    @Override
    public Collection<Course> selectAllStudentInProgressCourses(int studentID) throws ConnectionException, ServiceException {
        log.info("In CourseServiceImpl (selectAllStudentInProgressCourses, studentID: " + studentID + ")");
        try (DBConnection dbConnection = DBConnection.createConnection()) {
            log.info("Initializing CourseDao");
            CourseDao courseDao = new CourseDaoImpl(dbConnection.getConnection());
            log.info("Control transferring to Dao");
            Collection<Course> collection = courseDao.selectAllStudentInProgressCourses(studentID);
            log.info("Closing connection");
            return collection;
        } catch (DAOException daoException) {
            log.error("Error!", daoException);
            throw new ServiceException(daoException.getMessage(), daoException);
        }
    }

    @Override
    public Collection<Course> selectAllStudentFinishedCourses(int studentID) throws ConnectionException, ServiceException {
        log.info("In CourseServiceImpl (selectAllStudentFinishedCourses, studentID: " + studentID + ")");
        try (DBConnection dbConnection = DBConnection.createConnection()) {
            log.info("Initializing CourseDao");
            CourseDao courseDao = new CourseDaoImpl(dbConnection.getConnection());
            log.info("Control transferring to Dao");
            Collection<Course> collection = courseDao.selectAllStudentFinishedCourses(studentID);
            log.info("Closing connection");
            return collection;
        } catch (DAOException daoException) {
            log.error("Error!", daoException);
            throw new ServiceException(daoException.getMessage(), daoException);
        }
    }

    @Override
    public Collection<Course> selectAllStudentNotStartedCourses(int studentID) throws ConnectionException, ServiceException {
        log.info("In CourseServiceImpl (selectAllStudentNotStartedCourses, studentID: " + studentID + ")");
        try (DBConnection dbConnection = DBConnection.createConnection()) {
            log.info("Initializing CourseDao");
            CourseDao courseDao = new CourseDaoImpl(dbConnection.getConnection());
            log.info("Control transferring to Dao");
            Collection<Course> collection = courseDao.selectAllStudentNotStartedCourses(studentID);
            log.info("Closing connection");
            return collection;
        } catch (DAOException daoException) {
            log.error("Error!", daoException);
            throw new ServiceException(daoException.getMessage(), daoException);
        }
    }

    @Override
    public Collection<Course> selectAllAvailableCourses(int studentID, String courseName) throws ConnectionException, ServiceException {
        log.info("In CourseServiceImpl (selectAllStudentAvailableCoursesRegistered, studentID: " + studentID + " , courseName: " + courseName + ")");
        try (DBConnection dbConnection = DBConnection.createConnection()) {
            log.info("Initializing CourseDao");
            CourseDao courseDao = new CourseDaoImpl(dbConnection.getConnection());
            log.info("Control transferring to Dao");
            Collection<Course> collection = courseDao.selectAllAvailableCourses(studentID, courseName);
            log.info("Closing connection");
            return collection;
        } catch (DAOException daoException) {
            log.error("Error!", daoException);
            throw new ServiceException(daoException.getMessage(), daoException);
        }
    }

    @Override
    public void updateStudentMark(String mark, int studentID, int courseID) throws ConnectionException, ServiceException {
        log.info("In CourseServiceImpl (updateStudentMark, mark: " + mark + ",studentID: " + studentID +
                ", courseID: " + courseID + ")");
        try (DBConnection dbConnection = DBConnection.createConnection()) {
            log.info("Initializing CourseDao");
            CourseDao courseDao = new CourseDaoImpl(dbConnection.getConnection());
            log.info("Control transferring to Dao");
            courseDao.updateStudentMark(mark, studentID, courseID);
            log.info("Closing connection");
        } catch (DAOException daoException) {
            log.error("Error!", daoException);
            throw new ServiceException(daoException.getMessage(), daoException);
        }
    }

    @Override
    public int selectStudentsCountOnCourse(int courseID) throws ConnectionException, ServiceException {
        log.info("In CourseServiceImpl (selectStudentsCountOnCourse, courseID: " + courseID + ")");
        try (DBConnection dbConnection = DBConnection.createConnection()) {
            log.info("Initializing CourseDao");
            CourseDao courseDao = new CourseDaoImpl(dbConnection.getConnection());
            log.info("Control transferring to Dao");
            int result = courseDao.selectStudentsCountOnCourse(courseID);
            log.info("Closing connection");
            return result;
        } catch (DAOException daoException) {
            log.error("Error!", daoException);
            throw new ServiceException(daoException.getMessage(), daoException);
        }
    }

    @Override
    public void insertStudentToCourse(int studentID, int courseID) throws ConnectionException, ServiceException {
        log.info("In CourseServiceImpl (selectStudentsCountOnCourse, studentID: " + studentID +
                ", courseID: " + courseID + ")");
        try (DBConnection dbConnection = DBConnection.createConnection()) {
            log.info("Initializing CourseDao");
            CourseDao courseDao = new CourseDaoImpl(dbConnection.getConnection());
            log.info("Control transferring to Dao");
            courseDao.insertStudentToCourse(studentID, courseID);
            log.info("Closing connection");
        } catch (DAOException daoException) {
            log.error("Error!", daoException);
            throw new ServiceException(daoException.getMessage(), daoException);
        }
    }

    @Override
    public Collection<Integer> selectAllStudentMarks(ArrayList<Integer> coursesID, int studentID) throws ConnectionException, ServiceException {
        log.info("In CourseServiceImpl (selectAllStudentMarks, coursesID: " + coursesID.toString() +
                ", studentID: " + studentID + ")");
        try (DBConnection dbConnection = DBConnection.createConnection()) {
            CourseDao courseDao = new CourseDaoImpl(dbConnection.getConnection());
            log.info("Initializing CourseDao");
            ArrayList<Integer> result = new ArrayList<>();
            log.info("Control transferring to Dao");
            for (Integer integer : coursesID) {
                result.add(courseDao.selectStudentMarkOnCourse(studentID, integer));
            }
            log.info("Closing connection");
            return result;
        } catch (DAOException daoException) {
            log.error("Error!", daoException);
            throw new ServiceException(daoException.getMessage(), daoException);
        }
    }

    @Override
    public int selectCourseCount() throws ConnectionException, ServiceException {
        log.info("In CourseServiceImpl (selectCourseCount)");
        try (DBConnection dbConnection = DBConnection.createConnection()) {
            log.info("Initializing CourseDao");
            CourseDao courseDao = new CourseDaoImpl(dbConnection.getConnection());
            log.info("Control transferring to Dao");
            int result = courseDao.selectCourseCount();
            log.info("Closing connection");
            return result;
        } catch (DAOException daoException) {
            log.error("Error!", daoException);
            throw new ServiceException(daoException.getMessage(), daoException);
        }
    }

    @Override
    public Collection<Course> selectAllFinishedTeacherCourses(int studentID, int teacherID) throws ConnectionException, ServiceException {
        log.info("In CourseServiceImpl (selectAllFinishedTeacherCourses, studentID: " + studentID + ", teacherID: " +
                teacherID + ")");
        try (DBConnection dbConnection = DBConnection.createConnection()) {
            log.info("Initializing CourseDao");
            CourseDao courseDao = new CourseDaoImpl(dbConnection.getConnection());
            log.info("Control transferring to Dao");
            ArrayList<Course> result = (ArrayList<Course>) courseDao.selectAllFinishedTeacherCourses(studentID, teacherID);
            log.info("Closing connection");
            return result;
        } catch (DAOException daoException) {
            log.error("Error!", daoException);
            throw new ServiceException(daoException.getMessage(), daoException);
        }
    }

    @Override
    public Collection<String> selectAllCoursesNameByStudentID(int studentID) throws ConnectionException, ServiceException {
        log.info("In CourseServiceImpl (selectAllCoursesNameByStudentID, studentID: " + studentID + ")");
        try (DBConnection dbConnection = DBConnection.createConnection()) {
            log.info("Initializing CourseDao");
            CourseDao courseDao = new CourseDaoImpl(dbConnection.getConnection());
            log.info("Control transferring to Dao");
            Collection<String> result = courseDao.selectAllCoursesNameByStudentID(studentID);
            log.info("Closing connection");
            return result;
        } catch (DAOException daoException) {
            log.error("Error!", daoException);
            throw new ServiceException(daoException.getMessage(), daoException);
        }
    }

    @Override
    public Collection<String> selectAllCoursesName() throws ConnectionException, ServiceException {
        log.info("In CourseServiceImpl (selectAllCoursesName)");
        try (DBConnection dbConnection = DBConnection.createConnection()) {
            log.info("Initializing CourseDao");
            CourseDao courseDao = new CourseDaoImpl(dbConnection.getConnection());
            log.info("Control transferring to Dao");
            Collection<String> result = courseDao.selectAllCoursesName();
            log.info("Closing connection");
            return result;
        } catch (DAOException daoException) {
            log.error("Error!", daoException);
            throw new ServiceException(daoException.getMessage(), daoException);
        }
    }
}
