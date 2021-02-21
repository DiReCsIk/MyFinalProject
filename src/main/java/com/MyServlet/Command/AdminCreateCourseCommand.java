package com.MyServlet.Command;

import com.MyServlet.DBManager.Service.Impl.TeacherServiceImpl;
import com.MyServlet.DBManager.Service.TeacherService;
import com.MyServlet.Entity.User;
import com.MyServlet.Exception.CommandException;
import com.MyServlet.Exception.ConnectionException;
import com.MyServlet.Exception.ServiceException;
import com.MyServlet.Util.Pages;
import com.MyServlet.Util.UserRole;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class AdminCreateCourseCommand implements Command {
    private static final Logger log = Logger.getLogger(AdminCreateCourseCommand.class.getName());

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException, ConnectionException {
        log.info("In AdminCreateCourseCommand");
        log.info("Validating user");
        User administrator = (User) request.getSession().getAttribute("user");
        if (administrator == null || !administrator.getUserRole().equals(UserRole.ADMINISTRATOR)) {
            log.info("Fail. User is not an administrator");
            return Pages.MAIN_PAGE;
        }
        TeacherService teacherService = new TeacherServiceImpl();
        try {
            log.info("Setting course data");
            LinkedHashMap<String, ArrayList<String>> teachers = (LinkedHashMap<String, ArrayList<String>>) teacherService.selectAllTeachersNameAndSurname();
            log.info("Counting free teachers");
            if (teachers.get("id").size() == 0) {
                log.info("Fail. There are no free teachers");
                request.setAttribute("exception", "There are no free teachers");
                return Pages.ADMIN_COURSES;
            }
            request.setAttribute("teachers", teachers.get("data"));
            request.setAttribute("teachersID", teachers.get("id"));
            log.info("AdminCreateCourseCommand successful");
        } catch (ServiceException serviceException) {
            log.error("Error!", serviceException);
            throw new CommandException(serviceException.getMessage(), serviceException);
        }
        return Pages.CREATE_COURSE_PAGE;
    }
}
