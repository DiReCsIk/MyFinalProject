package com.MyServlet.Entity;

import java.io.Serializable;
import java.sql.Date;

public class Student extends User implements Serializable {
    private String name;
    private String surName;
    private int averageRating;
    private boolean banStatus;
    private Date birthDate;
    private int userID;

    public void setUserID(int userID) {  this.userID = userID; }
    public int getUserID() { return userID; }

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

    public int getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(int averageRating) {
        this.averageRating = averageRating;
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

}
