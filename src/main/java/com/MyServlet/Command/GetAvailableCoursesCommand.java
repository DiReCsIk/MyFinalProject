package com.MyServlet.Command;

import com.MyServlet.DBManager.Service.CourseService;
import com.MyServlet.DBManager.Service.Impl.CourseServiceImpl;
import com.MyServlet.DBManager.Service.Impl.TeacherServiceImpl;
import com.MyServlet.DBManager.Service.TeacherService;
import com.MyServlet.Entity.Course;
import com.MyServlet.Entity.Student;
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

public class GetAvailableCoursesCommand implements Command {
    private static final Logger log = Logger.getLogger(GetAvailableCoursesCommand.class.getName());

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException, ConnectionException {
        log.info("In GetAvailableCourses");
        HttpSession session = request.getSession();
        User student = (User) request.getSession().getAttribute("user");
        if (student != null && !student.getUserRole().equals(UserRole.STUDENT)) {
            log.info("Fail. User is not a student");
            return Pages.MAIN_PAGE;
        }
        int pageNumber = request.getParameter("pageNumber") == null ? 1 : Integer.parseInt(request.getParameter("pageNumber"));
        int rowCount = session.getAttribute("rowCount") == null ? 5 : (int) session.getAttribute("rowCount");
        TeacherService teacherService = new TeacherServiceImpl();
        CourseService courseService = new CourseServiceImpl();
        ArrayList<Integer> teachersID = new ArrayList<>();
        String sortBy;
        if (session.getAttribute("lang") != null && session.getAttribute("lang").equals("ru")) {
            sortBy = request.getParameter("sortBy") == null ? "Имя" : request.getParameter("sortBy");
        } else {
            sortBy = request.getParameter("sortBy") == null ? "Name" : request.getParameter("sortBy");
        }
        String courseName = request.getParameter("courseName") == null ? "%" : request.getParameter("courseName");
        ArrayList<Course> courseList;
        int courseCount;
        ArrayList<String> coursesNameList;
        try {
            if (student != null) {
                log.info("Getting course list and count for signed in user");
                coursesNameList = (ArrayList<String>) courseService.selectAllCoursesNameByStudentID(student.getId());
                courseList = (ArrayList<Course>) courseService.selectAllStudentAvailableCoursesRegistered(student.getId(), pageNumber, rowCount, courseName);
                courseCount = courseService.selectAvailableCoursesCountRegistered(student.getId(), courseName);
            } else {
                log.info("Getting course list and count for not signed in user");
                coursesNameList = (ArrayList<String>) courseService.selectAllCoursesName();
                courseList = (ArrayList<Course>) courseService.selectAllStudentAvailableCourses(pageNumber, rowCount, courseName);
                courseCount = courseService.selectAvailableCoursesCount(courseName);
            }
            log.info("Sorting course list");
            courseList = (ArrayList<Course>) Sorter.sortCourseList(courseList, sortBy);
            log.info("Getting teachersID");
            for (Course course : courseList) {
                teachersID.add(course.getTeacherID());
            }
            session.setAttribute("rowCount", rowCount);
            request.setAttribute("coursesSelectName", coursesNameList);
            request.setAttribute("courseName", courseName);
            request.setAttribute("teacherData", teacherService.selectTeacherNameAndSurnameByID(teachersID));
            request.setAttribute("maxPage", (int) Math.ceil((double) courseCount / rowCount));
            request.setAttribute("pageNumber", pageNumber);
            request.setAttribute("courseType", "available");
            request.setAttribute("courseList", courseList);
            request.setAttribute("sortBy", sortBy);
            log.info("GetAvailableCoursesCommand success");
        } catch (ServiceException serviceException) {
            log.error("Error!", serviceException);
            throw new CommandException(serviceException.getMessage(), serviceException);
        }
        return Pages.AVAILABLE_COURSE_PAGE;
    }
}
