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
                && this.getId() == otherAdministrator.getId() && this.getEmail().equals(otherAdministrator.getEmail())
                && this.getPassword().equals(otherAdministrator.getPassword());
    }

    @Override
    public String toString() {
        return "Administrator{ name='" + name + "', surName='" + surName + "', birthDate=" + birthDate +
                ", userID=" + userID + ", email='" + getEmail() + ", id=" + getId() + '}';
    }
}
