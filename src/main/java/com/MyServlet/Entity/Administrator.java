package com.MyServlet.Entity;

import java.io.Serializable;
import java.sql.Date;

public class Administrator extends User implements Serializable {
    private String name;
    private String surName;
    private Date birthDate;
    private int userID;

    public int getUserID() { return userID; }

    public void setUserID(int id) { userID = id; }

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

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }


}
