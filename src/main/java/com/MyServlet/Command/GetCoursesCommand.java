package com.MyServlet.Command;

import com.MyServlet.DBManager.Service.CourseService;
import com.MyServlet.DBManager.Service.Impl.CourseServiceImpl;
import com.MyServlet.DBManager.Service.Impl.TeacherServiceImpl;
import com.MyServlet.DBManager.Service.TeacherService;
import com.MyServlet.Entity.Course;
import com.MyServlet.Entity.Student;
import com.MyServlet.Entity.User;
import com.MyServlet.Util.Sorter;
import com.MyServlet.Util.UserRole;
import com.mysql.cj.Session;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.LinkedHashMap;


public class GetCoursesCommand implements Command {
    private static final Logger log = Logger.getLogger(GetCoursesCommand.class.getName());

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        log.info("In GetCoursesCommand");
        HttpSession session = request.getSession();  log.info("Validating user");
        User student = (User) session.getAttribute("user");
        if (student == null || !student.getUserRole().equals(UserRole.STUDENT)) {
            log.info("Fail. User is not a student");
            return "/page/main.jsp";
        }
        int pageNumber = 1;
        if (request.getParameter("pageNumber") != null) {
            pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
        }
        if (pageNumber <= 0) {
            log.info("Page number < 0. Resetting page number to 0");
            pageNumber = 1;
        }
        int rowCount = (int) session.getAttribute("rowCount");
        TeacherService teacherService = new TeacherServiceImpl();
        String courseType = request.getParameter("courseType");
        if (courseType == null) {
            courseType = "notStarted";
        }
        CourseService courseService = new CourseServiceImpl();
        String sortBy = request.getParameter("sortBy");
        if (sortBy == null) {
            sortBy = "Name";
        }
        ArrayList<Integer> teachersID = new ArrayList<>();
        ArrayList<Course> courseList;
        int courseCount;
        log.info("Getting course list and course count");
        switch (courseType) {
            case "notStarted":
                log.info("Course type is 'not started'");
                courseList = (ArrayList<Course>) courseService.selectAllStudentNotStartedCourses(student.getId(), pageNumber, rowCount);
                courseCount = courseService.selectNotStartedCoursesCount(student.getId());
                break;
            case "finished":
                log.info("Course type is 'finished'");
                courseList = (ArrayList<Course>) courseService.selectAllStudentFinishedCourses(student.getId(), pageNumber, rowCount);
                courseCount = courseService.selectFinishedCoursesCount(student.getId());
                break;
            default:
                log.info("Course type is 'in progress'");
                courseList = (ArrayList<Course>) courseService.selectAllStudentInProgressCourses(student.getId(), pageNumber, rowCount);
                courseCount = courseService.selectInProgressCoursesCount(student.getId());
        }
        log.info("Sorting course list");
        courseList = (ArrayList<Course>) Sorter.sortCourseList(courseList, sortBy);
        ArrayList<Integer> coursesID = new ArrayList<>();
        for (Course course : courseList) {
            if (courseType.equals("finished")) {
                coursesID.add(course.getId());
            }
            teachersID.add(course.getTeacherID());
        }
        if (courseType.equals("finished")) {
            log.info("Adding students mark");
            LinkedHashMap<String, ArrayList<String>> teachers = (LinkedHashMap<String, ArrayList<String>>) teacherService.selectFinishedTeachersData(student.getId());
            request.setAttribute("teachers", teachers.get("data"));
            request.setAttribute("teachersID", teachers.get("id"));
            request.setAttribute("marks", courseService.selectAllStudentMarks(coursesID, student.getId()));
        }
        session.setAttribute("rowCount", 5);
        request.setAttribute("teacherData", teacherService.selectTeacherNameAndSurnameByID(teachersID));
        request.setAttribute("maxPage", (int) Math.ceil((double) courseCount / rowCount));
        request.setAttribute("pageNumber", pageNumber);
        request.setAttribute("courseType", courseType);
        request.setAttribute("courseList", courseList);
        request.setAttribute("sortBy", sortBy);
        log.info("GetCoursesCommand successful");
        return "/page/studentCourses.jsp";
    }
}
