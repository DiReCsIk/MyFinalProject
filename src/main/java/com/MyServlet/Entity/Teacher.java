package com.MyServlet.Entity;

import java.io.Serializable;
import java.sql.Date;

public class Teacher extends User implements Serializable {
    private String name;
    private String surName;
    private Date birthDate;
    private int userID;

    public Teacher(int id, String name, String surname, Date birthDate, int userID) {
        setId(id);
        this.name = name;
        this.surName = surname;
        this.birthDate = birthDate;
        this.userID = userID;
    }

    public Teacher() {

    }

    public int getUserID() { return userID; }

    public void setUserID(int id) { userID = id; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String  getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    @Override
    public int hashCode() {
        return 47 * getId() + getEmail().hashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Teacher)) {
            return false;
        }
        if (this == other) {
            return true;
        }
        Teacher otherTeacher = (Teacher) other;
        return this.name.equals(otherTeacher.name) && this.surName.equals(otherTeacher.surName)
                && this.birthDate.equals(otherTeacher.birthDate) && this.userID == otherTeacher.userID;
    }

    @Override
    public String toString() {
        return "Teacher{ name='" + name + "', surName='" + surName + "', birthDate=" + birthDate +
                ", userID=" + userID + ", email='" + getEmail() + ", id=" + getId() + '}';
    }
}
