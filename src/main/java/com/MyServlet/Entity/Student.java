package com.MyServlet.Entity;

import com.MyServlet.Util.UserRole;

import java.io.Serializable;
import java.sql.Date;

public class Student extends User implements Serializable {
    private String name;
    private String surName;
    private boolean banStatus;
    private Date birthDate;
    private int userID;

    public Student(String email, String password, UserRole userRole, String name, String surName, boolean banStatus, Date birthDate, int userID) {
        super(email, password, userRole);
        this.name = name;
        this.surName = surName;
        this.banStatus = banStatus;
        this.birthDate = birthDate;
        this.userID = userID;
    }

    public Student(String name, String surName, boolean banStatus, Date birthDate, int userID) {
        this.name = name;
        this.surName = surName;
        this.banStatus = banStatus;
        this.birthDate = birthDate;
        this.userID = userID;
    }

    public Student() {

    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getUserID() {
        return userID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public boolean isBanStatus() {
        return banStatus;
    }

    public void setBanStatus(boolean banStatus) {
        this.banStatus = banStatus;
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
        if (!(other instanceof Student)) {
            return false;
        }
        if (this == other) {
            return true;
        }
        Student otherStudent = (Student) other;
        return this.name.equals(otherStudent.name) && this.surName.equals(otherStudent.surName)
                && this.birthDate.equals(otherStudent.birthDate) && this.userID == otherStudent.userID;
    }

    @Override
    public String toString() {
        return "Student{ name='" + name + "', surName='" + surName + "', birthDate=" + birthDate +
                ", userID=" + userID + ", email='" + getEmail() + ", id=" + getId() + '}';
    }
}
