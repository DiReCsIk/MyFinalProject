package com.MyServlet.Command;

import com.MyServlet.DBManager.Service.CourseService;
import com.MyServlet.DBManager.Service.Impl.CourseServiceImpl;
import com.MyServlet.Entity.User;
import com.MyServlet.Exception.CommandException;
import com.MyServlet.Exception.ConnectionException;
import com.MyServlet.Exception.ServiceException;
import com.MyServlet.Util.Pages;
import com.MyServlet.Util.UserRole;
import com.mysql.cj.Session;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AdminRemoveCourseCommand implements Command {
    private static final Logger log = Logger.getLogger(AdminRemoveCourseCommand.class.getName());

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException, ConnectionException {
        log.info("In AdminRemoveCourseCommand");
        HttpSession session = request.getSession();
        log.info("Validating user");
        User administrator = (User) session.getAttribute("user");
        if (administrator == null || !administrator.getUserRole().equals(UserRole.ADMINISTRATOR)) {
            log.info("Fail. User is not an administrator");
            return Pages.MAIN_PAGE;
        }
        int courseID = Integer.parseInt(request.getParameter("courseID"));
        CourseService courseService = new CourseServiceImpl();
        try {
            log.info("Removing course");
            courseService.deleteEntityByID(courseID);
            log.info("AdminRemoveCourseCommand successful");
        } catch (ServiceException serviceException) {
            log.error("Error!", serviceException);
            throw new CommandException(serviceException.getMessage(), serviceException);
        }
        return Pages.ADMIN_COURSES + "&pageNumber=" + request.getParameter("pageNumber");
    }
}
