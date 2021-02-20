package com.MyServlet.Command;

import com.MyServlet.DBManager.Service.CourseService;
import com.MyServlet.DBManager.Service.Impl.CourseServiceImpl;
import com.MyServlet.DBManager.Service.Impl.TeacherServiceImpl;
import com.MyServlet.DBManager.Service.TeacherService;
import com.MyServlet.Entity.Course;
import com.MyServlet.Entity.User;
import com.MyServlet.Util.UserRole;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

public class GetAdminCoursesCommand implements Command {
    private static final Logger log = Logger.getLogger(GetAdminCoursesCommand.class.getName());

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        log.info("In GetAdminCoursesCommand");
        log.info("Validating user");
        User administrator = (User) request.getSession().getAttribute("user");
        if (administrator == null || !administrator.getUserRole().equals(UserRole.ADMINISTRATOR)) {
            log.info("Fail. User is not an administrator ");
            return "/page/main.jsp";
        }
        int pageNumber = 1;
        if(request.getParameter("pageNumber") != null){
            pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
        }
        if (pageNumber < 1) {
            log.info("Page number < 0. Resetting page number to 0");
            pageNumber = 1;
        }
        int rowCount = (int) request.getSession().getAttribute("rowCount");
        CourseService courseService = new CourseServiceImpl();
        TeacherService teacherService = new TeacherServiceImpl();
        log.info("Getting course list");
        ArrayList<Course> courses = (ArrayList<Course>) courseService.getAllEntities(pageNumber, rowCount);
        int coursesCount = courseService.selectCourseCount();
        log.info("Getting teacher list");
        ArrayList<Integer> teachersID = new ArrayList<>();
        for (Course course : courses) {
            teachersID.add(course.getTeacherID());
        }
        request.getSession().setAttribute("rowCount", 5);
        request.setAttribute("teacherData", teacherService.selectTeacherNameAndSurnameByID(teachersID));
        request.setAttribute("pageNumber", pageNumber);
        request.setAttribute("courseList", courses);
        request.setAttribute("maxPage", (int) Math.ceil((double) coursesCount / rowCount));
        log.info("GetAdminCoursesCommand successful");
        return "/page/adminCourseManage.jsp";
    }
}
