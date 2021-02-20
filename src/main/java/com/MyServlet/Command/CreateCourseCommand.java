package com.MyServlet.Command;

import com.MyServlet.DBManager.Service.CourseService;
import com.MyServlet.DBManager.Service.Impl.CourseServiceImpl;
import com.MyServlet.DBManager.Service.Impl.TeacherServiceImpl;
import com.MyServlet.DBManager.Service.TeacherService;
import com.MyServlet.Entity.Course;
import com.MyServlet.Entity.User;
import com.MyServlet.Util.UserRole;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Date;

public class CreateCourseCommand implements Command {
    private static final Logger log = Logger.getLogger(CreateCourseCommand.class.getName());

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        log.info("In CreateCourseCommand");
        User administrator = (User) request.getSession().getAttribute("user");
        log.info("Validating user");
        if (administrator == null || !administrator.getUserRole().equals(UserRole.ADMINISTRATOR)) {
            log.info("Fail. User is not an administrator");
            return "/page/main.jsp";
        }
        String name = request.getParameter("name");
        String theme = request.getParameter("theme");
        Date startDate = Date.valueOf(request.getParameter("startDate"));
        Date endDate = Date.valueOf(request.getParameter("endDate"));
        int teacherID = Integer.parseInt(request.getParameter("teacherID"));
        TeacherService teacherService = new TeacherServiceImpl();
        log.info("Checking that the teacher with ID " + teacherID + " is free");
        if (teacherService.selectTeacherCourseID(teacherID) != 0) {
            log.info("Fail. Teacher is not free");
            request.getSession().setAttribute("exception", "Teacher is not free");
            return "Controller?command=adminCreateCourse";
        }
        log.info("Inserting course in database");
        CourseService courseService = new CourseServiceImpl();
        Course course = new Course();
        course.setName(name);
        course.setTheme(theme);
        course.setStartDate(startDate);
        course.setEndDate(endDate);
        course.setTeacherID(teacherID);
        course.setStudentCount(0);
        courseService.insertEntity(course);
        log.info("CreateCourseCommand successful");
        return "Controller?command=getAdminCourses";
    }
}
