package com.MyServlet.Command;

import com.MyServlet.DBManager.Service.Impl.StudentServiceImpl;
import com.MyServlet.DBManager.Service.Impl.TeacherServiceImpl;
import com.MyServlet.DBManager.Service.StudentService;
import com.MyServlet.DBManager.Service.TeacherService;
import com.MyServlet.Entity.User;
import com.MyServlet.Util.UserRole;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeclineUserPositionCommand implements Command {
    private static final Logger log = Logger.getLogger(DeclineUserPositionCommand.class.getName());

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        log.info("In DeclineUserPositionCommand");
        User administrator = (User) request.getSession().getAttribute("user");
        log.info("Validating user");
        if (administrator == null || !administrator.getUserRole().equals(UserRole.ADMINISTRATOR)) {
            log.info("Fail. User is not an administrator");
            return "/page/main.jsp";
        }
        int userID = Integer.parseInt(request.getParameter("userID"));
        TeacherService teacherService = new TeacherServiceImpl();
        StudentService studentService = new StudentServiceImpl();
        log.info("Checking a user for decline");
        if(teacherService.selectTeacherByUserID(userID) != null && studentService.selectStudentByUserID(userID) == null
                && teacherService.selectTeacherCourseID(teacherService.selectTeacherByUserID(userID).getId()) == 0) {
            log.info("Declining user");
            studentService.declineToStudent(userID);
            log.info("DeclineUserPositionCommand successful");
        } else {
            request.getSession().setAttribute("exception", "First you need to remove teacher course");
            log.info("Fail. Teacher is not free");
        }
        return "Controller?command=getTeachersInfo&pageNumber=" + request.getParameter("pageNumber");
    }
}