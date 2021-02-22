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
 * Represents GetCoursesCommand. Implements command.
 */
public class GetCoursesCommand implements Command {
    private static final Logger log = Logger.getLogger(GetCoursesCommand.class.getName());

    /**
     * This command retrieves all information about in progress, finished, not started courses for student.
     *
     * @param request - HttpServletRequest
     * @param response - HttpServletResponse
     *
     * @throws CommandException - if trouble in service
     * @throws ConnectionException - if trouble with db connection
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException, ConnectionException {
        log.info("In GetCoursesCommand");
        HttpSession session = request.getSession();
        log.info("Validating user");
        TeacherService teacherService = new TeacherServiceImpl();
        CourseService courseService = new CourseServiceImpl();
        User student = (User) session.getAttribute("user");
        if (!User.validateUser(student, UserRole.STUDENT)) {
            log.info("Fail. User isn't valid");
            return Pages.MAIN_PAGE;
        }
        String sortBy;
        int pageNumber = request.getParameter("pageNumber") == null ? 1 : Integer.parseInt(request.getParameter("pageNumber"));
        int rowCount = session.getAttribute("rowCount") == null ? 5 : (int) session.getAttribute("rowCount");
        if (session.getAttribute("lang") != null && session.getAttribute("lang").equals("ru")) {
            sortBy = request.getParameter("sortBy") == null ? "Имя" : request.getParameter("sortBy");
        } else {
            sortBy = request.getParameter("sortBy") == null ? "Name" : request.getParameter("sortBy");
        }
        String courseType = request.getParameter("courseType") == null ? "notStarted" : request.getParameter("courseType");
        ArrayList<Integer> teachersID = new ArrayList<>();
        ArrayList<Course> courseList;
        try {
            log.info("Getting course list and course count");
            switch (courseType) {
                case "notStarted":
                    log.info("Course type is 'not started'");
                    courseList = (ArrayList<Course>) courseService.selectAllStudentNotStartedCourses(student.getId());
                    break;
                case "finished":
                    log.info("Course type is 'finished'");
                    courseList = (ArrayList<Course>) courseService.selectAllStudentFinishedCourses(student.getId());
                    LinkedHashMap<String, ArrayList<String>> teachers = (LinkedHashMap<String, ArrayList<String>>) teacherService.selectFinishedTeachersData(student.getId());
                    request.setAttribute("teachers", teachers.get("data"));
                    request.setAttribute("teachersID", teachers.get("id"));
                    break;
                default:
                    log.info("Course type is 'in progress'");
                    courseList = (ArrayList<Course>) courseService.selectAllStudentInProgressCourses(student.getId());
            }
            log.info("Sorting course list");
            courseList = (ArrayList<Course>) Sorter.sortCourseList(courseList, sortBy);
            int courseCount = courseList.size();
            ArrayList<Integer> coursesID = new ArrayList<>();
            ArrayList<Course> courseArrayList = new ArrayList<>(courseList.subList(rowCount * (pageNumber - 1), Math.min(courseList.size(), rowCount * (pageNumber - 1) + rowCount)));
            for (Course course : courseArrayList) {
                if (courseType.equals("finished")) {
                    coursesID.add(course.getId());
                }
                teachersID.add(course.getTeacherID());
            }
            if (courseType.equals("finished")) {
                log.info("Adding students mark");
                request.setAttribute("marks", courseService.selectAllStudentMarks(coursesID, student.getId()));
            }
            session.setAttribute("rowCount", rowCount);
            request.setAttribute("teacherData", teacherService.selectTeacherNameAndSurnameByID(teachersID));
            request.setAttribute("maxPage", (int) Math.ceil((double) courseCount / rowCount));
            request.setAttribute("pageNumber", pageNumber);
            request.setAttribute("courseType", courseType);
            request.setAttribute("courseList", courseArrayList);
            request.setAttribute("sortBy", sortBy);
            log.info("GetCoursesCommand successful");
        } catch (ServiceException serviceException) {
            log.error("Error!", serviceException);
            throw new CommandException(serviceException.getMessage(), serviceException);
        }
        return Pages.STUDENT_COURSES_PAGE;
    }
}
