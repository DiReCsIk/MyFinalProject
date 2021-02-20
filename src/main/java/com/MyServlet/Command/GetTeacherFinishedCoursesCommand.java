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
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class GetTeacherFinishedCoursesCommand implements Command {
    private static final Logger log = Logger.getLogger(GetTeacherFinishedCoursesCommand.class.getName());

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        log.info("In GetTeacherFinishedCoursesCommand");
        log.info("Validating user");
        User student = (User) request.getSession().getAttribute("user");
        if (student == null || !student.getUserRole().equals(UserRole.STUDENT)) {
            log.info("Fail. User is not a student ");
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
        int teacherID = Integer.parseInt(request.getParameter("teacherID"));
        String sortBy = request.getParameter("sortBy");
        int rowCount = (int) request.getSession().getAttribute("rowCount");
        CourseService courseService = new CourseServiceImpl();
        TeacherService teacherService = new TeacherServiceImpl();
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
        return "/page/studentCourses.jsp";
    }
}
