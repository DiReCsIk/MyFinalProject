package com.MyServlet.Command;

import com.MyServlet.DBManager.Service.CourseService;
import com.MyServlet.DBManager.Service.Impl.CourseServiceImpl;
import com.MyServlet.DBManager.Service.Impl.TeacherServiceImpl;
import com.MyServlet.DBManager.Service.TeacherService;
import com.MyServlet.Entity.Course;
import com.MyServlet.Entity.Teacher;
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

/**
 * Represents AdminUpdateCourseCommand. Implements command.
 */
public class AdminUpdateCourseCommand implements Command {
    private static final Logger log = Logger.getLogger(AdminUpdateCourseCommand.class.getName());

    /**
     * This command update course in database.
     *
     * @param request - HttpServletRequest
     * @param response - HttpServletResponse
     *
     * @throws CommandException - if trouble in service
     * @throws ConnectionException - if trouble with db connection
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException, ConnectionException {
        log.info("In AdminUpdateCourseCommand");
        log.info("Validating user");
        User administrator = (User) request.getSession().getAttribute("user");
        if (!User.validateUser(administrator, UserRole.ADMINISTRATOR)) {
            log.info("Fail. User is not valid");
            return Pages.MAIN_PAGE;
        }
        int courseID = Integer.parseInt(request.getParameter("courseID"));
        CourseService courseService = new CourseServiceImpl();
        TeacherService teacherService = new TeacherServiceImpl();
        try {
            LinkedHashMap<String, ArrayList<String>> teachers = (LinkedHashMap<String, ArrayList<String>>) teacherService.selectAllTeachersNameAndSurname();
            log.info("Validating course");
            Course course = courseService.selectEntityByID(courseID);
            if (course == null) {
                log.info("Fail. Course does not exist");
                request.setAttribute("exception", "This course has been removed");
                return Pages.ADMIN_COURSES;
            }
            Teacher teacher = teacherService.selectEntityByID(course.getTeacherID());
            teachers.get("id").add(String.valueOf(teacher.getId()));
            teachers.get("data").add(teacher.getName() + " " + teacher.getSurName());
            log.info("Setting session attributes");
            request.setAttribute("courseID", courseID);
            request.setAttribute("course", course);
            request.setAttribute("teachers", teachers.get("data"));
            request.setAttribute("teachersID", teachers.get("id"));
            log.info("AdminUpdateCourseCommand successful");
        } catch (ServiceException serviceException) {
            log.error("Error!", serviceException);
            throw new CommandException(serviceException.getMessage(), serviceException);
        }
        return Pages.UPDATE_COURSE_PAGE;
    }
}
