package com.MyFinalProject.DAOTest;

import com.MyServlet.DBManager.Dao.AdministratorDao;
import com.MyServlet.DBManager.Dao.Impl.AdministratorDaoImpl;
import com.MyServlet.DBManager.Dao.Impl.StudentDaoImpl;
import com.MyServlet.DBManager.Dao.Impl.TeacherDaoImpl;
import com.MyServlet.DBManager.Dao.StudentDao;
import com.MyServlet.DBManager.Dao.TeacherDao;
import com.MyServlet.Entity.Administrator;
import com.MyServlet.Entity.Student;
import com.MyServlet.Entity.Teacher;
import com.MyServlet.Exception.DAOException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TeacherDaoTest extends AbstractDaoTest {
    private TeacherDao teacherDao;
    private AdministratorDao administratorDao;
    private StudentDao studentDao;

    @Before
    public void CREATE_TABLES() throws SQLException {
        super.createTables(CREATE_TEACHER_TABLE, CREATE_ADMINISTRATOR_TABLE, CREATE_COURSE_TABLE, CREATE_STUDENT_TABLE);
        teacherDao = new TeacherDaoImpl(connection);
        administratorDao = new AdministratorDaoImpl(connection);
        studentDao =new StudentDaoImpl(connection);
    }

    @Test
    public void GET_ALL_ENTITIES_TEST() throws DAOException {
        Teacher teacher = new Teacher(1, "Name", "Surname", Date.valueOf("2021-02-20"), 5);
        Teacher secondTeacher = new Teacher(1, "secondName", "Surname", Date.valueOf("2021-02-20"), 5);
        List<Teacher> teachers = new ArrayList<>();
        teacherDao.insertEntity(teacher);
        teacherDao.insertEntity(secondTeacher);
        teachers.add(teacherDao.selectEntityByID(1));
        teachers.add(teacherDao.selectEntityByID(2));
        Assert.assertEquals(teachers, teacherDao.getAllEntities());
    }

    @Test
    public void SELECT_TEACHER_COURSE_ID() throws DAOException {
        Teacher teacher = new Teacher(1, "Name", "Surname", Date.valueOf("2021-02-20"), 5);
        Assert.assertEquals(0, teacherDao.selectTeacherCourseID(teacher.getId()));
    }

    @Test
    public void UPDATE_TEACHER_TEST() throws DAOException {
        Teacher teacher = new Teacher(1, "Name", "Surname", Date.valueOf("2021-02-20"), 5);
        Teacher secondTeacher = new Teacher(1, "secondName", "Surname", Date.valueOf("2021-02-20"), 5);
        teacherDao.insertEntity(teacher);
        teacherDao.updateEntity(secondTeacher);
        Assert.assertEquals(secondTeacher, teacherDao.selectEntityByID(1));
    }

    @Test
    public void DELETE_TEACHER_TEST() throws DAOException {
        Teacher teacher = new Teacher(1, "Name", "Surname", Date.valueOf("2021-02-20"), 5);
        teacherDao.insertEntity(teacher);
        teacherDao.deleteEntityByID(1);
        Assert.assertEquals(0, teacherDao.getAllTeachersCount());
    }
    @Test
    public void SELECT_ALL_TEACHERS_NAME_AND_SURNAME_TEST() throws DAOException {
        Assert.assertEquals("{id=[], data=[]}", teacherDao.selectAllTeachersNameAndSurname().toString());   //////wqeweqewqeqwe
    }
    @Test
    public void DECLINE_TO_TEACHER_TEST() throws DAOException {
        Administrator administrator = new Administrator(1, "Name", "Surname",
                Date.valueOf("2021-02-20"), 5);
        administratorDao.insertEntity(administrator);
        teacherDao.declineToTeacher(administrator);
        Assert.assertEquals(1,teacherDao.getAllTeachersCount());
    }
    @Test
    public void RISE_TO_TEACHER_TEST() throws DAOException {
        Student student = new Student("Name", "Surname", true, Date.valueOf("2021-01-01"), 2);
        studentDao.insertEntity(student);
        teacherDao.raiseToTeacher(student);
        Assert.assertEquals(1,teacherDao.getAllTeachersCount());
    }

    @After
    public void clearTables() {
        super.clearTables(DROP_TEACHER_TABLE, DROP_ADMINISTRATOR_TABLE, DROP_COURSE_TABLE, DROP_STUDENT_TABLE);
    }
}
