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
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class AdminUpdateCourseCommand implements Command {
    private static final Logger log = Logger.getLogger(AdminUpdateCourseCommand.class.getName());

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        log.info("In AdminUpdateCourseCommand");
        log.info("Validating user");
        User administrator = (User) request.getSession().getAttribute("user");
        if (administrator == null || !administrator.getUserRole().equals(UserRole.ADMINISTRATOR)) {
            log.info("Fail. User is not an administrator");
            return "/page/main.jsp";
        }
        int courseID = Integer.parseInt(request.getParameter("courseID"));
        CourseService courseService = new CourseServiceImpl();
        TeacherService teacherService = new TeacherServiceImpl();
        LinkedHashMap<String, ArrayList<String>> teachers = (LinkedHashMap<String, ArrayList<String>>) teacherService.selectAllTeachersNameAndSurname();
        log.info("Counting free teachers");
        if(teachers.get("data").size() == 0) {
            log.info("Fail. There are no free teachers");
            request.setAttribute("exception", "There are no free teachers");
            return "Controller?command=getAdminCourses";
        }
        Course course = courseService.selectEntityByID(courseID);
        log.info("Validating course");
        if(course == null){
            log.info("Fail. Course does not exist");
            request.setAttribute("exception", "This course has been removed");
            return "Controller?command=getAdminCourses";
        }
        log.info("Setting session attributes");
        request.setAttribute("courseID", courseID);
        request.setAttribute("course", course);
        request.setAttribute("teachers", teachers.get("data"));
        request.setAttribute("teachersID", teachers.get("id"));
        log.info("AdminUpdateCourseCommand successful");
        return "/page/updateCourse.jsp";
    }
}
