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

public class GetAvailableCoursesCommand implements Command{
    private static final Logger log = Logger.getLogger(GetAvailableCoursesCommand.class.getName());

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        log.info("In GetAvailableCourses");
/*
        Student student = (Student) request.getSession().getAttribute("user");
*/
        User student = (User) request.getSession().getAttribute("user");
        if (student != null && !student.getUserRole().equals(UserRole.STUDENT)) {
            log.info("Fail. User is not a student");
            return "/page/main.jsp";
        }
        int rowCount = (int) request.getSession().getAttribute("rowCount");
        TeacherService teacherService = new TeacherServiceImpl();
        CourseService courseService = new CourseServiceImpl();
        ArrayList<Integer> teachersID = new ArrayList<>();
        String sortBy = request.getParameter("sortBy");
        String courseName = request.getParameter("courseName");
        if (courseName == null){
            courseName = "%";
        }
        if (sortBy == null) {
            sortBy = "Name";
        }
        int pageNumber = 1;
        if (request.getParameter("pageNumber") != null) {
            pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
        }
        if (pageNumber <= 0) {
            log.info("Page number < 0. Resetting page number to 0");
            pageNumber = 1;
        }
        ArrayList<Course> courseList;
        int courseCount;
        ArrayList<String> coursesNameList;
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
        request.setAttribute("coursesSelectName", coursesNameList);
        request.setAttribute("courseName", courseName);
        request.setAttribute("teacherData", teacherService.selectTeacherNameAndSurnameByID(teachersID));
        request.setAttribute("maxPage", (int) Math.ceil((double) courseCount / rowCount));
        request.setAttribute("pageNumber", pageNumber);
        request.setAttribute("courseType", "available");
        request.setAttribute("courseList", courseList);
        request.setAttribute("sortBy", sortBy);
        log.info("GetAvailableCoursesCommand success");
        return "/page/availableCourses.jsp";
    }
}
