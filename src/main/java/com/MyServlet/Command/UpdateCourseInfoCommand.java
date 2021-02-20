package com.MyServlet.Command;

import com.MyServlet.DBManager.Service.CourseService;
import com.MyServlet.DBManager.Service.Impl.CourseServiceImpl;
import com.MyServlet.Entity.Course;
import com.MyServlet.Entity.User;
import com.MyServlet.Util.UserRole;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Date;

public class UpdateCourseInfoCommand implements Command {
    private static final Logger log = Logger.getLogger(UpdateCourseInfoCommand.class.getName());

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        log.info("In UpdateCourseInfoCommand");
        log.info("Validating user");
        User administrator = (User)request.getSession().getAttribute("user");
        if(administrator == null || !administrator.getUserRole().equals(UserRole.ADMINISTRATOR)){
            log.info("User is not an administrator");
            return "/page/main.jsp";
        }
        String theme = request.getParameter("theme");
        String name = request.getParameter("name");
        Date startDate = Date.valueOf(request.getParameter("startDate"));
        Date endDate = Date.valueOf(request.getParameter("endDate"));
        int teacherID = Integer.parseInt(request.getParameter("teacherID"));
        int courseID = Integer.parseInt(request.getParameter("courseID"));
        CourseService courseService = new CourseServiceImpl();
        log.info("Checking course availability");
        if(courseService.selectEntityByID(courseID) == null){
            log.info("Fail. Course is not exist");
            request.getSession().setAttribute("exception", "This course has been removed");
            return "/page/updateCourse.jsp";
        }
        Course course = new Course();
        course.setId(courseID);
        course.setName(name);
        course.setTheme(theme);
        course.setStartDate(startDate);
        course.setEndDate(endDate);
        course.setTeacherID(teacherID);
        courseService.updateEntity(course);
        log.info("UpdateCourseInfoCommand successful");
        return "Controller?command=getAdminCourses";
    }
}
