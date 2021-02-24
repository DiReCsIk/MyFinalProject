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
import com.MyServlet.Util.Sorter;
import com.MyServlet.Util.UserRole;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Represents GetTeacherFinishedCoursesCommand. Implements command.
 */
public class GetTeacherFinishedCoursesCommand implements Command {
    private static final Logger log = Logger.getLogger(GetTeacherFinishedCoursesCommand.class.getName());

    /**
     * This command retrieves all information about courses, where
     * teacher id like {@code teacherID}.
     *
     * @param request - HttpServletRequest
     * @param response - HttpServletResponse
     *
     * @throws CommandException - if trouble in service
     * @throws ConnectionException - if trouble with db connection
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException, ConnectionException {
        log.info("In GetTeacherFinishedCoursesCommand");
        HttpSession session = request.getSession();
        log.info("Validating user");
        User student = (User) request.getSession().getAttribute("user");
        if (!User.validateUser(student, UserRole.STUDENT)) {
            log.info("Fail. User is not valid");
            return Pages.MAIN_PAGE;
        }
        int pageNumber = request.getParameter("pageNumber") == null ? 1 : Integer.parseInt(request.getParameter("pageNumber"));
        int rowCount = session.getAttribute("rowCount") == null ? 5 : (int) session.getAttribute("rowCount");
        int teacherID = Integer.parseInt(request.getParameter("teacherID"));
        if(pageNumber <= 0){
            pageNumber = 1;
        }
        String sortBy = request.getParameter("sortBy") == null ? "Name" : request.getParameter("sortBy");
        CourseService courseService = new CourseServiceImpl();
        TeacherService teacherService = new TeacherServiceImpl();
        try {
            log.info("Getting teacher finished courses");
            ArrayList<Course> courseList = (ArrayList<Course>) courseService.selectAllFinishedTeacherCourses(student.getId(), teacherID);
            courseList = (ArrayList<Course>) Sorter.sortCourseList(courseList, sortBy);
            ArrayList<Integer> coursesID = new ArrayList<>();
            ArrayList<Integer> teachersID = new ArrayList<>();
            ArrayList<Course> courseArrayList = new ArrayList<>(courseList.subList(rowCount * (pageNumber - 1), Math.min(courseList.size(), rowCount * (pageNumber - 1) + rowCount)));
            for (Course course : courseArrayList) {
                coursesID.add(course.getId());
                teachersID.add(course.getTeacherID());
            }
            LinkedHashMap<String, ArrayList<String>> teachers = (LinkedHashMap<String, ArrayList<String>>) teacherService.selectFinishedTeachersData(student.getId());
            request.setAttribute("teachersID", teachers.get("id"));
            request.setAttribute("teacherNameAndSurname", request.getParameter("teacherNameAndSurname"));
            request.setAttribute("teachers", teachers.get("data"));
            request.setAttribute("marks", courseService.selectAllStudentMarks(coursesID, student.getId()));
            request.setAttribute("teacherData", teacherService.selectTeacherNameAndSurnameByID(teachersID));
            request.setAttribute("maxPage", (int) Math.ceil((double) courseList.size() / rowCount));
            request.setAttribute("pageNumber", pageNumber);
            request.setAttribute("courseType", "finished");
            request.setAttribute("courseList", courseArrayList);
            request.setAttribute("sortBy", sortBy);
            log.info("GetTeacherFinishedCoursesCommand successful");
        } catch (ServiceException serviceException) {
            log.error("Error!", serviceException);
            throw new CommandException(serviceException.getMessage(), serviceException);
        }
        return Pages.STUDENT_COURSES_PAGE;
    }
}
