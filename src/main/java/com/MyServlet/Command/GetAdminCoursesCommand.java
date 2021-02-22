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
import javax.servlet.http.HttpSession;
import java.util.ArrayList;

/**
 * Represents GetAdminCoursesCommand. Implements command.
 */
public class GetAdminCoursesCommand implements Command {
    private static final Logger log = Logger.getLogger(GetAdminCoursesCommand.class.getName());

    /**
     * This command retrieves information a list of all courses,
     * teachers in these courses, additional information on courses from the database.
     *
     * @param request - HttpServletRequest
     * @param response - HttpServletResponse
     *
     * @throws CommandException - if trouble in service
     * @throws ConnectionException - if trouble with db connection
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException, ConnectionException {
        log.info("In GetAdminCoursesCommand");
        HttpSession session = request.getSession();
        log.info("Validating user");
        User administrator = (User) request.getSession().getAttribute("user");
        if (!User.validateUser(administrator, UserRole.ADMINISTRATOR)) {
            log.info("Fail. User is not valid");
            return Pages.MAIN_PAGE;
        }
        int pageNumber = request.getParameter("pageNumber") == null ? 1 : Integer.parseInt(request.getParameter("pageNumber"));
        int rowCount = session.getAttribute("rowCount") == null ? 5 : (int) session.getAttribute("rowCount");
        CourseService courseService = new CourseServiceImpl();
        TeacherService teacherService = new TeacherServiceImpl();
        try {
            log.info("Getting course list");
            ArrayList<Course> courses = (ArrayList<Course>) courseService.getAllEntities(pageNumber, rowCount);
            int coursesCount = courseService.selectCourseCount();
            log.info("Getting teacher list");
            ArrayList<Integer> teachersID = new ArrayList<>();
            for (Course course : courses) {
                teachersID.add(course.getTeacherID());
            }
            session.setAttribute("rowCount", rowCount);
            request.setAttribute("teacherData", teacherService.selectTeacherNameAndSurnameByID(teachersID));
            request.setAttribute("pageNumber", pageNumber);
            request.setAttribute("courseList", courses);
            request.setAttribute("maxPage", (int) Math.ceil((double) coursesCount / rowCount));
            log.info("GetAdminCoursesCommand successful");
        } catch (ServiceException serviceException) {
            log.error("Error!", serviceException);
            throw new CommandException(serviceException.getMessage(), serviceException);
        }
        return Pages.ADMIN_COURSES_PAGE;
    }
}
