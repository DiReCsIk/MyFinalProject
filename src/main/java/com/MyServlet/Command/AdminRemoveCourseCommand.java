package com.MyServlet.Command;

import com.MyServlet.DBManager.Service.CourseService;
import com.MyServlet.DBManager.Service.Impl.CourseServiceImpl;
import com.MyServlet.Entity.User;
import com.MyServlet.Util.UserRole;
import com.mysql.cj.Session;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AdminRemoveCourseCommand implements Command {
    private static final Logger log = Logger.getLogger(AdminRemoveCourseCommand.class.getName());

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        log.info("In AdminRemoveCourseCommand");
        HttpSession session = request.getSession();
        log.info("Validating user");
        User administrator = (User) session.getAttribute("user");
        if (administrator == null || !administrator.getUserRole().equals(UserRole.ADMINISTRATOR)) {
            log.info("Fail. User is not an administrator");
            return "/page/main.jsp";
        }
        int courseID = Integer.parseInt(request.getParameter("courseID"));
        CourseService courseService = new CourseServiceImpl();
        log.info("Removing course");
        courseService.deleteEntityByID(courseID);
        log.info("AdminRemoveCourseCommand successful");
        return "Controller?command=getAdminCourses&pageNumber=" + request.getParameter("pageNumber");/*new GetAdminCoursesCommand().execute(request, response);*/
    }
}
