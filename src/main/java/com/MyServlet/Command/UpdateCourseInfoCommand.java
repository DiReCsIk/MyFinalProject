package com.MyServlet.Command;

import com.MyServlet.DBManager.Service.CourseService;
import com.MyServlet.DBManager.Service.Impl.CourseServiceImpl;
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
 * Represents UpdateCourseInfoCommand. Implements command.
 */
public class UpdateCourseInfoCommand implements Command {
    private static final Logger log = Logger.getLogger(UpdateCourseInfoCommand.class.getName());

    /**
     * This command update course info.
     *
     * @param request - HttpServletRequest
     * @param response - HttpServletResponse
     *
     * @throws CommandException - if trouble in service
     * @throws ConnectionException - if trouble with db connection
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException, ConnectionException {
        log.info("In UpdateCourseInfoCommand");
        log.info("Validating user");
        User administrator = (User)request.getSession().getAttribute("user");
        if (!User.validateUser(administrator, UserRole.ADMINISTRATOR)) {
            log.info("Fail. User is not valid");
            return Pages.MAIN_PAGE;
        }
        String theme = request.getParameter("theme");
        String name = request.getParameter("name");
        Date startDate = Date.valueOf(request.getParameter("startDate"));
        Date endDate = Date.valueOf(request.getParameter("endDate"));
        int teacherID = Integer.parseInt(request.getParameter("teacherID"));
        int courseID = Integer.parseInt(request.getParameter("courseID"));
        CourseService courseService = new CourseServiceImpl();
        try {
            log.info("Checking course availability");
            if (courseService.selectEntityByID(courseID) == null) {
                log.info("Fail. Course is not exist");
                request.getSession().setAttribute("exception", "This course has been removed");
                return Pages.UPDATE_COURSE_PAGE;
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
        } catch (ServiceException serviceException){
            log.error("Error!", serviceException);
            throw new CommandException(serviceException.getMessage(), serviceException);
        }
        return Pages.ADMIN_COURSES;
    }
}
