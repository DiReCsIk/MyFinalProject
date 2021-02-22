package com.MyFinalProject.DAOTest;

import com.MyServlet.DBManager.Dao.CourseDao;
import com.MyServlet.DBManager.Dao.Impl.CourseDaoImpl;
import com.MyServlet.DBManager.Dao.Impl.StudentDaoImpl;
import com.MyServlet.DBManager.Dao.StudentDao;
import com.MyServlet.Entity.Course;
import com.MyServlet.Entity.Student;
import com.MyServlet.Exception.DAOException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CourseDaoTest extends AbstractDaoTest {
    private CourseDao courseDao;
    private StudentDao studentDao;

    @Before
    public void CREATE_TABLES() throws SQLException {
        super.createTables(CREATE_COURSE_TABLE, CREATE_STUDENT_COURSE_TABLE, CREATE_STUDENT_TABLE);
        courseDao = new CourseDaoImpl(connection);
        studentDao = new StudentDaoImpl(connection);
    }

    @Test
    public void GET_ALL_ENTITIES_TEST() throws DAOException {
        Course course = new Course(1, "Theme", "Name", Date.valueOf("2021-02-20"),
                Date.valueOf("2021-02-24"), 4, 4, 4);
        Course secondCourse = new Course(2, "Theme", "Name", Date.valueOf("2021-02-20"),
                Date.valueOf("2021-02-24"), 4, 4, 4);
        List<Course> courses = new ArrayList<>();
        courses.add(course);
        courses.add(secondCourse);
        courseDao.insertEntity(course);
        courseDao.insertEntity(secondCourse);
        Assert.assertEquals(courses, courseDao.getAllEntities());
    }

    @Test
    public void SELECT_ENTITY_BY_ID_TEST() throws DAOException {
        Course course = new Course(2, "Theme", "Name", Date.valueOf("2021-02-20"),
                Date.valueOf("2021-02-24"), 4, 4, 0);
        courseDao.insertEntity(course);
        Assert.assertNotEquals(course, courseDao.selectEntityByID(2));
    }

    @Test
    public void GET_ALL_COURSE_COUNT_TEST() throws DAOException {
        Course course = new Course(2, "Theme", "Name", Date.valueOf("2021-02-20"),
                Date.valueOf("2021-02-24"), 4, 4, 4);
        courseDao.insertEntity(course);
        Assert.assertEquals(1, courseDao.selectCourseCount());
    }

    @Test
    public void DELETE_ENTITY_BY_ID_TEST() throws DAOException {
        Course course = new Course(2, "Theme", "Name", Date.valueOf("2021-02-20"),
                Date.valueOf("2021-02-24"), 4, 4, 4);
        courseDao.insertEntity(course);
        courseDao.deleteEntityByID(1);
        Assert.assertEquals(0, courseDao.selectCourseCount());
    }

    @Test
    public void UPDATE_ENTITY_TEST() throws DAOException {
        Course course = new Course(2, "Theme", "Name", Date.valueOf("2021-02-20"),
                Date.valueOf("2021-02-24"), 4, 4, 4);
        Course secondCourse = new Course(3, "Theme", "Name", Date.valueOf("2021-02-20"),
                Date.valueOf("2021-02-24"), 4, 4, 0);
        courseDao.insertEntity(course);
        courseDao.updateEntity(secondCourse);
        Assert.assertNotEquals(secondCourse, courseDao.selectEntityByID(1));
    }

    @Test
    public void SELECT_ALL_FINISHED_TEACHER_COURSES_TEST() throws DAOException {
        Course course = new Course(2, "Theme", "Name", Date.valueOf("2021-02-20"),
                Date.valueOf("2021-01-24"), 4, 4, 4);
        courseDao.insertEntity(course);
        Assert.assertEquals("[]", courseDao.selectAllFinishedTeacherCourses(10, 4).toString());
    }

    @Test
    public void SELECT_ALL_COURSES_NAME_BY_STUDENT_ID_TEST() throws DAOException {
        Assert.assertEquals("[]", courseDao.selectAllCoursesNameByStudentID(1).toString());
    }

    @Test
    public void UPDATE_STUDENT_MARK_TEST() throws DAOException {
        Student student = new Student("Name", "Surname", false, Date.valueOf("2021-01-01"), 2);
        Course course = new Course(1, "Theme", "Name", Date.valueOf("2021-02-20"),
                Date.valueOf("2021-01-24"), 4, 4, 4);
        courseDao.insertEntity(course);
        studentDao.insertEntity(student);
        courseDao.insertStudentToCourse(studentDao.selectStudentByUserID(2).getId(), 1);
        courseDao.updateStudentMark("56", studentDao.selectStudentByUserID(2).getId(), 1);
        Assert.assertEquals(courseDao.selectCourseCount(), 1);
    }

    @Test
    public void SELECT_ALL_STUDENT_AVAILABLE_COURSES_TEST() throws DAOException {
        Assert.assertEquals("[]", courseDao.selectAllStudentAvailableCourses("Test").toString());
    }

    @Test
    public void SELECT_ALL_AVAILABLE_COURSES_TEST() throws DAOException {
        Assert.assertEquals("[]", courseDao.selectAllAvailableCourses(1, "Test").toString());
    }

    @Test
    public void SELECT_ALL_STUDENT_FINISHED_COURSES_TEST() throws DAOException {
        Assert.assertEquals("[]", courseDao.selectAllStudentFinishedCourses(1).toString());
    }

    @Test
    public void SELECT_ALL_STUDENT_IN_PROGRESS_COURSES_TEST() throws DAOException {
        Assert.assertEquals("[]", courseDao.selectAllStudentInProgressCourses(1).toString());
    }

    @Test
    public void SELECT_ALL_STUDENT_NOT_STARTED_COURSES_TEST() throws DAOException {
        Assert.assertEquals("[]", courseDao.selectAllStudentNotStartedCourses(1).toString());
    }

    @Test
    public void SELECT_FINISHED_COURSES_COUNT() throws DAOException {
        Assert.assertEquals(0, courseDao.selectFinishedCoursesCount(1));
    }

    @Test
    public void SELECT_IN_PROGRESS_COURSES_COUNT() throws DAOException {
        Assert.assertEquals(0, courseDao.selectInProgressCoursesCount(1));
    }

    @Test
    public void SELECT_NOT_STARTED_COURSES_COUNT() throws DAOException {
        Assert.assertEquals(0, courseDao.selectNotStartedCoursesCount(1));
    }

    @Test
    public void SELECT_STUDENT_COUNT_ON_COURSE() throws DAOException {
        Course course = new Course(1, "Theme", "Name", Date.valueOf("2021-02-20"),
                Date.valueOf("2021-01-24"), 4, 4, 4);
        courseDao.insertEntity(course);
        Student student = new Student("Name", "Surname", false, Date.valueOf("2021-01-01"), 2);
        studentDao.insertEntity(student);
        courseDao.insertStudentToCourse(1, 1);
        Assert.assertEquals(1, courseDao.selectStudentsCountOnCourse(1));
    }

    @Test
    public void SELECT_ALL_COURSES_NAME() throws DAOException {
        courseDao = new CourseDaoImpl(connection);
        Assert.assertEquals("[]", courseDao.selectAllCoursesName().toString());
    }

    @Test
    public void SELECT_STUDENT_MARK_ON_COURSE() throws DAOException {
        Course course = new Course(1, "Theme", "Name", Date.valueOf("2021-02-20"),
                Date.valueOf("2021-01-24"), 4, 4, 4);
        courseDao.insertEntity(course);
        Student student = new Student("Name", "Surname", false, Date.valueOf("2021-01-01"), 2);
        studentDao.insertEntity(student);
        courseDao.insertStudentToCourse(1, 1);
        courseDao.updateStudentMark("56", 1, 1);
        Assert.assertEquals((Integer) 56, courseDao.selectStudentMarkOnCourse(1, 1));
    }

    @Test
    public void GET_ALL_ENTITIES_WITH_PAGINATION_TEST() throws DAOException {
        Course course = new Course(1, "Theme", "Name", Date.valueOf("2021-02-20"),
                Date.valueOf("2021-02-24"), 4, 4, 4);
        Course secondCourse = new Course(2, "Theme", "Name", Date.valueOf("2021-02-20"),
                Date.valueOf("2021-02-24"), 4, 4, 4);
        List<Course> courses = new ArrayList<>();
        courses.add(course);
        courses.add(secondCourse);
        courseDao.insertEntity(course);
        courseDao.insertEntity(secondCourse);
        Assert.assertEquals(courses, courseDao.getAllEntities(1, 5));
    }

    @Test
    public void SELECT_ALL_FINISHED_COURSES_COUNT() throws DAOException {
        Course course = new Course(1, "Theme", "Name", Date.valueOf("2021-02-20"),
                Date.valueOf("2021-01-24"), 4, 4, 4);
        courseDao.insertEntity(course);
        Assert.assertEquals(0, courseDao.selectAllFinishedTeacherCoursesCount(1, 5));
    }


    @After
    public void clearTables() {
        super.clearTables(DROP_COURSE_TABLE, DROP_STUDENT_COURSE_TABLE, DROP_STUDENT_TABLE);
    }
}
