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

public class SetStudentMarkCommand implements Command {
    private static final Logger log = Logger.getLogger(SetStudentMarkCommand.class.getName());

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        log.info("In SetStudentMarkCommand");
        log.info("Validating user");
        HttpSession session = request.getSession();
        TeacherService teacherService = new TeacherServiceImpl();
        User teacher = (User) session.getAttribute("user");
        if (teacher == null || !teacher.getUserRole().equals(UserRole.TEACHER)) {
            log.info("Fail. User is not a teacher");
            return "/page/main.jsp";
        }
        int studentID = Integer.parseInt(request.getParameter("studentID"));
        StudentService studentService = new StudentServiceImpl();
        if (studentService.selectEntityByID(studentID) != null) {
            CourseService courseService = new CourseServiceImpl();
            String mark = request.getParameter("newMark");
            int courseID = teacherService.selectTeacherCourseID(teacher.getId());
            log.info("Setting new mark");
            courseService.updateStudentMark(mark, studentID, courseID);
        }
        log.info("SetStudentMarkCommand successful");
        return "/Controller?command=getTeacherCourseInfo";
    }
}
