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

public class GetTeacherFinishedCoursesCommand implements Command {
    private static final Logger log = Logger.getLogger(GetTeacherFinishedCoursesCommand.class.getName());

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException, ConnectionException {
        log.info("In GetTeacherFinishedCoursesCommand");
        HttpSession session = request.getSession();
        log.info("Validating user");
        User student = (User) request.getSession().getAttribute("user");
        if (student == null || !student.getUserRole().equals(UserRole.STUDENT)) {
            log.info("Fail. User is not a student ");
            return Pages.MAIN_PAGE;
        }
        int pageNumber = request.getParameter("pageNumber") == null ? 1 : Integer.parseInt(request.getParameter("pageNumber"));
        int rowCount = session.getAttribute("rowCount") == null ? 5 : (int) session.getAttribute("rowCount");
        int teacherID = Integer.parseInt(request.getParameter("teacherID"));
        String sortBy = request.getParameter("sortBy") == null ? "Name" : request.getParameter("sortBy");
        CourseService courseService = new CourseServiceImpl();
        TeacherService teacherService = new TeacherServiceImpl();
        try {
            log.info("Getting teacher finished courses");
            ArrayList<Course> courseList = (ArrayList<Course>) courseService.selectAllFinishedTeacherCourses(student.getId(), teacherID, pageNumber, rowCount);
            int courseCount = courseService.selectAllFinishedTeacherCoursesCount(student.getId(), teacherID);
            courseList = (ArrayList<Course>) Sorter.sortCourseList(courseList, sortBy);
            ArrayList<Integer> coursesID = new ArrayList<>();
            ArrayList<Integer> teachersID = new ArrayList<>();
            for (Course course : courseList) {
                coursesID.add(course.getId());
                teachersID.add(course.getTeacherID());
            }
            LinkedHashMap<String, ArrayList<String>> teachers = (LinkedHashMap<String, ArrayList<String>>) teacherService.selectFinishedTeachersData(student.getId());
            session.setAttribute("rowCount", rowCount);
            request.setAttribute("teachersID", teachers.get("id"));
            request.setAttribute("teacherNameAndSurname", request.getParameter("teacherNameAndSurname"));
            request.setAttribute("teachers", teachers.get("data"));
            request.setAttribute("marks", courseService.selectAllStudentMarks(coursesID, student.getId()));
            request.setAttribute("teacherData", teacherService.selectTeacherNameAndSurnameByID(teachersID));
            request.setAttribute("maxPage", (int) Math.ceil((double) courseCount / rowCount));
            request.setAttribute("pageNumber", pageNumber);
            request.setAttribute("courseType", "finished");
            request.setAttribute("courseList", courseList);
            request.setAttribute("sortBy", sortBy);
            log.info("GetTeacherFinishedCoursesCommand successful");
        } catch (ServiceException serviceException) {
            log.error("Error!", serviceException);
            throw new CommandException(serviceException.getMessage(), serviceException);
        }
        return Pages.STUDENT_COURSES_PAGE;
    }
}
