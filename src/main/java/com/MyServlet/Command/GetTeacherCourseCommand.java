package com.MyServlet.Command;

import com.MyServlet.DBManager.Service.CourseService;
import com.MyServlet.DBManager.Service.Impl.CourseServiceImpl;
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
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Map;

/**
 * Represents GetTeacherCourseCommand. Implements command.
 */
public class GetTeacherCourseCommand implements Command {
    private static final Logger log = Logger.getLogger(GetTeacherCourseCommand.class.getName());

    /**
     * This command retrieves all information about teacher course, students on his course
     * and send teacher to his course.
     *
     * @param request - HttpServletRequest
     * @param response - HttpServletResponse
     *
     * @throws CommandException - if trouble in service
     * @throws ConnectionException - if trouble with db connection
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException, ConnectionException {
        log.info("In GetTeacherCourseCommand");
        HttpSession session = request.getSession();
        TeacherService teacherService = new TeacherServiceImpl();
        log.info("Validating user");
        User teacher = (User) session.getAttribute("user");
        User administrator = (User) session.getAttribute("user");
        if (!User.validateUser(teacher, UserRole.TEACHER)) {
            log.info("Fail. User is not valid");
            return Pages.MAIN_PAGE;
        }
        int pageNumber = request.getParameter("pageNumber") == null ? 1 : Integer.parseInt(request.getParameter("pageNumber"));
        int rowCount = session.getAttribute("rowCount") == null ? 5 : (int) session.getAttribute("rowCount");
        CourseService courseService = new CourseServiceImpl();
        try {
            log.info("Getting teacher course data");
            int courseID = teacherService.selectTeacherCourseID(teacher.getId());
            Map<String, ArrayList<String>> map = teacherService.selectStudentsDataOnTeacherCourse(teacher.getId(), pageNumber, rowCount);
            int studentsCount = courseService.selectStudentsCountOnCourse(courseID);
            int maxPage = (int) Math.ceil((double) studentsCount / rowCount);
            session.setAttribute("rowCount", rowCount);
            request.setAttribute("studentsName", map.get("name"));
            request.setAttribute("studentsSurname", map.get("surName"));
            request.setAttribute("studentsBirthDay", map.get("birthDay"));
            request.setAttribute("studentsMark", map.get("mark"));
            request.setAttribute("studentsID", map.get("id"));
            request.setAttribute("pageNumber", pageNumber);
            request.setAttribute("maxPage", maxPage);
            log.info("GetTeacherCourseCommand successful");
        } catch (ServiceException serviceException) {
            log.error("Error!", serviceException);
            throw new CommandException(serviceException.getMessage(), serviceException);
        }
        return Pages.TEACHER_COURSE_PAGE;
    }
}
