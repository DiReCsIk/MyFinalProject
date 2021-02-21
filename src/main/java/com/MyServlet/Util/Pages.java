package com.MyServlet.Util;

import com.MyServlet.Controller.Controller;

public final class Pages {
    private Pages() {
    }

    public static final String MAIN_PAGE = "/page/main.jsp";
    public static final String ADMIN_COURSES = "Controller?command=getAdminCourses";
    public static final String CREATE_COURSE_PAGE = "/page/createCourse.jsp";
    public static final String UPDATE_COURSE_PAGE = "/page/updateCourse.jsp";
    public static final String CREATE_COURSE = "Controller?command=adminCreateCourse";
    public static final String GET_TEACHERS_INFO = "Controller?command=getTeachersInfo";
    public static final String GET_AVAILABLE_COURSE = "Controller?command=getAvailableCourses";
    public static final String ADMIN_COURSES_PAGE = "/page/adminCourseManage.jsp";
    public static final String SIGN_IN_PAGE = "page/signIn.jsp";
    public static final String ADMIN_USERS_PAGE = "/page/adminUserManage.jsp";
    public static final String AVAILABLE_COURSE_PAGE = "/page/availableCourses.jsp";
    public static final String STUDENT_COURSES_PAGE = "/page/studentCourses.jsp";
    public static final String TEACHER_COURSE_PAGE = "/page/teacherCourse.jsp";
    public static final String GET_STUDENTS_INFO = "Controller?command=getStudentsInfo";
    public static final String GET_TEACHER_COURSE_INFO = "/Controller?command=getTeacherCourseInfo";
    public static final String MY_DATA_PAGE = "page/myData.jsp";
    public static final String SIGN_UP_PAGE = "page/signUp.jsp";
    public static final String ERROR_404 = "/WEB-INF/error_404.jsp";
}
