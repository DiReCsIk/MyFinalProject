package com.MyServlet.Command;

import com.MyServlet.DBManager.Service.CourseService;
import com.MyServlet.DBManager.Service.Impl.CourseServiceImpl;
import com.MyServlet.DBManager.Service.Impl.TeacherServiceImpl;
import com.MyServlet.DBManager.Service.TeacherService;
import com.MyServlet.Entity.Course;
import com.MyServlet.Entity.User;
import com.MyServlet.Exception.CommandException;
import com.MyServlet.Exception.ConnectionException;
import com.MyServlet.Exception.ServiceException;
import com.MyServlet.Util.Pages;
import com.MyServlet.Util.UserRole;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Date;

/**
 * Represents CreateCourseCommand. Implements command.
 */
public class CreateCourseCommand implements Command {
    private static final Logger log = Logger.getLogger(CreateCourseCommand.class.getName());

    /**
     * This command create course in database.
     *
     * @param request - HttpServletRequest
     * @param response - HttpServletResponse
     *
     * @throws CommandException - if trouble in service
     * @throws ConnectionException - if trouble with db connection
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException, ConnectionException {
        log.info("In CreateCourseCommand");
        User administrator = (User) request.getSession().getAttribute("user");
        log.info("Validating user");
        if (!User.validateUser(administrator, UserRole.ADMINISTRATOR)) {
            log.info("Fail. User is not valid");
            return Pages.MAIN_PAGE;
        }
        String name = request.getParameter("name");
        String theme = request.getParameter("theme");
        Date startDate = Date.valueOf(request.getParameter("startDate"));
        Date endDate = Date.valueOf(request.getParameter("endDate"));
        int teacherID = Integer.parseInt(request.getParameter("teacherID"));
        TeacherService teacherService = new TeacherServiceImpl();
        try {
            log.info("Checking that the teacher with ID " + teacherID + " is free");
            if (teacherService.selectTeacherCourseID(teacherID) != 0) {
                log.info("Fail. Teacher is not free");
                request.getSession().setAttribute("exception", "Teacher is not free");
                return Pages.CREATE_COURSE;
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
        } catch (ServiceException serviceException) {
            log.error("Error!", serviceException);
            throw new CommandException(serviceException.getMessage(), serviceException);
        }
        return Pages.ADMIN_COURSES;
    }
}
