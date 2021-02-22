package com.MyServlet.Entity;

import com.MyServlet.Util.UserRole;

import java.io.Serializable;

public class User extends Entity implements Serializable {

    private String email;
    private String password;
    private UserRole userRole;

    public User(String email, String password, UserRole userRole) {
        this.email = email;
        this.password = password;
        this.userRole = userRole;
    }

    public User() { }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    @Override
    public int hashCode() {
        return 47 * getId() + getEmail().hashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof User)) {
            return false;
        }
        if (this == other) {
            return true;
        }
        User otherUser = (User) other;
        return this.getId() == otherUser.getId() && this.getEmail().equals(otherUser.getEmail()) && this.getPassword().equals(otherUser.getPassword());
    }

    @Override
    public String toString() {
        return "User{ email='" + getEmail() + ", id=" + getId() + '}';
    }

    public static boolean validateUser(User user, UserRole userRole){
        return user != null && user.getUserRole().equals(userRole);
    }
}
