package com.MyServlet.Entity;

import java.io.Serializable;
import java.sql.Date;

public class Administrator extends User implements Serializable {
    private String name;
    private String surName;
    private Date birthDate;
    private int userID;

    public int getUserID() {
        return userID;
    }

    public void setUserID(int id) {
        userID = id;
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
        if (!(other instanceof Administrator)) {
            return false;
        }
        if (this == other) {
            return true;
        }
        Administrator otherAdministrator = (Administrator) other;
        return this.name.equals(otherAdministrator.name) && this.surName.equals(otherAdministrator.surName)
                && this.birthDate.equals(otherAdministrator.birthDate) && this.userID == otherAdministrator.userID
                && this.getId() == otherAdministrator.getId();
    }

    @Override
    public String toString() {
        return "Name: " + name +
                " SurName: " + surName +
                " BirthDate: " + birthDate +
                " UserID=" + userID +
                " Email:" + getEmail() +
                " ID:" + getId();
    }

    public Administrator(){

    }

    public Administrator(int id, String name, String surName, Date birthDate, int userID){
        setId(id);
        this.name = name;
        this.surName = surName;
        this.birthDate = birthDate;
        this.userID = userID;
    }
}
