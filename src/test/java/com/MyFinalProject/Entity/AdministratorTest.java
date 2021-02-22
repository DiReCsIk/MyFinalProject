package com.MyFinalProject.Entity;

import com.MyServlet.Entity.Administrator;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Date;

public class AdministratorTest {

    @Test
    public void GET_ID_TEST() {
        Administrator administrator = new Administrator();
        Assert.assertEquals(0, administrator.getId());
    }

    @Test
    public void SET_ID_TEST() {
        Administrator administrator = new Administrator();
        int number = 10;
        administrator.setId(number);
        int result = administrator.getId();
        Assert.assertEquals(number, result);
    }

    @Test
    public void GET_USER_ID_TEST() {
        Administrator administrator = new Administrator();
        Assert.assertEquals(0, administrator.getUserID());
    }

    @Test
    public void SET_USER_ID_TEST() {
        Administrator administrator = new Administrator();
        int number = 10;
        administrator.setUserID(number);
        int result = administrator.getUserID();
        Assert.assertEquals(number, result);
    }

    @Test
    public void GET_BIRTHDATE_TEST() {
        Administrator administrator = new Administrator();
        Assert.assertNull(administrator.getBirthDate());
    }

    @Test
    public void SET_BIRTHDATE_TEST() {
        Administrator administrator = new Administrator();
        Date date = Date.valueOf("2021-02-19");
        administrator.setBirthDate(date);
        Date result = administrator.getBirthDate();
        Assert.assertEquals(date, result);
    }

    @Test
    public void SET_NAME_TEST() {
        Administrator administrator = new Administrator();
        String name = "Ivan";
        administrator.setName(name);
        String result = administrator.getName();
        Assert.assertEquals(name, result);
    }

    @Test
    public void GET_NAME_TEST() {
        Administrator administrator = new Administrator();
        Assert.assertNull(administrator.getName());
    }

    @Test
    public void SET_SURNAME_TEST() {
        Administrator administrator = new Administrator();
        String name = "Ivan";
        administrator.setSurName(name);
        String result = administrator.getSurName();
        Assert.assertEquals(name, result);
    }

    @Test
    public void GET_SURNAME_TEST() {
        Administrator administrator = new Administrator();
        Assert.assertNull(administrator.getSurName());
    }

    @Test
    public void EQUALS_WITH_SAME_REFERENCES_TEST() {
        Administrator administrator = new Administrator();
        Administrator otherAdministrator = administrator;
        Assert.assertEquals(administrator, otherAdministrator);
    }

    @Test
    public void EQUALS_WITH_DIFFERENT_OBJECTS_TEST() {
        Administrator administrator = new Administrator();
        Object other = new Object();
        Assert.assertNotEquals(administrator, other);
    }

    @Test
    public void EQUALS_TEST() {
        Administrator administrator = new Administrator(1, "Name", "Surname",
                Date.valueOf("2021-02-20"), 5);
        Administrator otherAdministrator = new Administrator(1, "Name", "Surname",
                Date.valueOf("2021-02-20"), 5);
        Assert.assertEquals(administrator, otherAdministrator);
    }

    @Test
    public void TO_STRING_TEST() {
        Administrator administrator = new Administrator(1, "Name", "Surname",
                Date.valueOf("2021-02-20"), 5);
        Assert.assertEquals("Name: Name SurName: Surname BirthDate: 2021-02-20 UserID=5 Email:null ID:1", administrator.toString());
    }

    @Test
    public void INIT_CONSTRUCTOR_TEST() {
        Administrator administrator = new Administrator(1, "Name", "Surname",
                Date.valueOf("2021-02-20"), 5);
        Assert.assertEquals("Name: Name SurName: Surname BirthDate: 2021-02-20 UserID=5 Email:null ID:1", administrator.toString());
    }
}
