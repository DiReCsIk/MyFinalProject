package com.MyServlet.Command;

import com.MyServlet.DBManager.Service.CourseService;
import com.MyServlet.DBManager.Service.Impl.CourseServiceImpl;
import com.MyServlet.DBManager.Service.Impl.StudentServiceImpl;
import com.MyServlet.DBManager.Service.Impl.TeacherServiceImpl;
import com.MyServlet.DBManager.Service.StudentService;
import com.MyServlet.DBManager.Service.TeacherService;
import com.MyServlet.Entity.Teacher;
import com.MyServlet.Entity.User;
import com.MyServlet.Util.UserRole;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Map;

public class GetTeacherCourseCommand implements Command {
    private static final Logger log = Logger.getLogger(GetTeacherCourseCommand.class.getName());

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        log.info("In GetTeacherCourseCommand");
        HttpSession session = request.getSession();
        TeacherService teacherService = new TeacherServiceImpl();
        log.info("Validating user");
        User teacher = (User) session.getAttribute("user");
        if (teacher == null || !teacher.getUserRole().equals(UserRole.TEACHER)) {
            log.info("Fail. User is not a teacher");
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
        CourseService courseService = new CourseServiceImpl();
        log.info("Getting teacher course data");
        int courseID = teacherService.selectTeacherCourseID(teacher.getId());
        int rowCount = (int) session.getAttribute("rowCount");
        Map<String, ArrayList<String>> map = teacherService.selectStudentsDataOnTeacherCourse(teacher.getId(), pageNumber, rowCount);
        int studentsCount = courseService.selectStudentsCountOnCourse(courseID);
        int maxPage = (int) Math.ceil((double) studentsCount / rowCount);
        request.setAttribute("studentsName", map.get("name"));
        request.setAttribute("studentsSurname", map.get("surName"));
        request.setAttribute("studentsBirthDay", map.get("birthDay"));
        request.setAttribute("studentsMark", map.get("mark"));
        request.setAttribute("studentsID", map.get("id"));
        request.setAttribute("pageNumber", pageNumber);
        request.setAttribute("maxPage", maxPage);
        log.info("GetTeacherCourseCommand successful");
        return "/page/teacherCourse.jsp";
    }
}
