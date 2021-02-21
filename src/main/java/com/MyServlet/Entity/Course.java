package com.MyServlet.Entity;

import java.sql.Date;

public class Course extends Entity{
    private String theme;
    private String name;
    private Date startDate;
    private Date endDate;
    private int teacherID;
    private int term;
    private int studentCount;

    public int getStudentCount() {
        return studentCount;
    }

    public void setStudentCount(int studentCount) {
        this.studentCount = studentCount;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getTeacherID() {
        return teacherID;
    }

    public void setTeacherID(int teacherID) {
        this.teacherID = teacherID;
    }

    public int getTerm() {
        return term;
    }

    public void setTerm(int term) {
        this.term = term;
    }

    @Override
    public int hashCode() {
        return 47 * getId() + Integer.hashCode(teacherID);
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Course)) {
            return false;
        }
        if (this == other) {
            return true;
        }
        Course otherCourse = (Course) other;
        return this.name.equals(otherCourse.name) && this.teacherID == otherCourse.teacherID
                && this.getStartDate().equals(otherCourse.getStartDate()) && this.getId() == otherCourse.getId()
                && this.getTerm() == otherCourse.getTerm() && this.getEndDate().equals(otherCourse.getEndDate())
                && this.getTheme().equals(otherCourse.getTheme());
    }

    @Override
    public String toString() {
        return "Course{ theme='" + theme + "', name='" + name + "', startDate=" + startDate + ", endDate=" + endDate +
                ", teacherID=" + teacherID + ", term=" + term + ", studentCount=" + studentCount + '}';
    }
}
