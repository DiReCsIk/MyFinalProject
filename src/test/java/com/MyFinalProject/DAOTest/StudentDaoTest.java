package com.MyFinalProject.DAOTest;

import com.MyServlet.DBManager.Dao.Impl.StudentDaoImpl;
import com.MyServlet.DBManager.Dao.Impl.TeacherDaoImpl;
import com.MyServlet.DBManager.Dao.StudentDao;
import com.MyServlet.DBManager.Dao.TeacherDao;
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

public class StudentDaoTest extends AbstractDaoTest{
    private StudentDao studentDao;
    private TeacherDao teacherDao;

    @Before
    public void CREATE_TABLES() throws SQLException {
        super.createTables(CREATE_STUDENT_TABLE,CREATE_TEACHER_TABLE);
        studentDao = new StudentDaoImpl(connection);
        teacherDao = new TeacherDaoImpl(connection);
    }

    @Test
    public void GET_ALL_ENTITIES_TEST() throws DAOException {
        Student student = new Student("Name", "Surname", false, Date.valueOf("2021-01-01"), 2);
        Student secondStudent = new Student("Name", "Surname", false, Date.valueOf("2021-01-01"), 3);
        List<Student> students = new ArrayList<>();
        studentDao.insertEntity(student);
        studentDao.insertEntity(secondStudent);
        students.add(studentDao.selectEntityByID(1));
        students.add(studentDao.selectEntityByID(2));
        Assert.assertEquals(students, studentDao.getAllEntities());
    }

    @Test
    public void GET_ALL_STUDENTS_COUNT_TEST() throws DAOException {
        Student student = new Student("Name", "Surname", false, Date.valueOf("2021-01-01"), 2);
        Student secondStudent = new Student("Name", "Surname", false, Date.valueOf("2021-01-01"), 3);
        studentDao.insertEntity(student);
        studentDao.insertEntity(secondStudent);
        Assert.assertEquals(2, studentDao.getAllStudentsCount());
    }

    @Test
    public void SET_BAN_STATUS_TEST() throws DAOException {
        Student student = new Student("Name", "Surname", false, Date.valueOf("2021-01-01"), 2);
        studentDao.insertEntity(student);
        studentDao.banStudent(2);
        Assert.assertTrue(studentDao.selectEntityByID(1).isBanStatus());
    }

    @Test
    public void SET_UNBAN_STATUS_TEST() throws DAOException {
        Student student = new Student("Name", "Surname", true, Date.valueOf("2021-01-01"), 2);
        studentDao.insertEntity(student);
        studentDao.unbanStudent(2);
        Assert.assertFalse(studentDao.selectEntityByID(1).isBanStatus());
    }

    @Test
    public void DECLINE_TO_STUDENT_TEST() throws DAOException {
        Teacher teacher = new Teacher(1, "Name", "Surname",
                Date.valueOf("2021-02-20"), 5);
        teacherDao.insertEntity(teacher);
        studentDao.declineToStudent(teacher);
        Assert.assertEquals(1,studentDao.getAllStudentsCount());
    }

    @Test
    public void UPDATE_STUDENT_TEST() throws DAOException {
        Student student = new Student("Name", "Surname", true, Date.valueOf("2021-01-01"), 2);
        Student secondStudent = new Student("newName", "Surname", true, Date.valueOf("2021-01-01"), 2);
        studentDao.insertEntity(student);
        secondStudent.setId(1);
        studentDao.updateEntity(secondStudent);
        Assert.assertEquals(secondStudent, studentDao.selectEntityByID(1));
    }

    @Test
    public void DELETE_STUDENT_TEST() throws DAOException {
        Student student = new Student("Name", "Surname", true, Date.valueOf("2021-01-01"), 2);
        studentDao.insertEntity(student);
        studentDao.deleteEntityByID(1);
        Assert.assertEquals(0, studentDao.getAllStudentsCount());
    }
    @After
    public void clearTables() {
        super.clearTables(DROP_STUDENT_TABLE,DROP_TEACHER_TABLE);
    }
}
