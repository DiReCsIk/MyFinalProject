package com.MyServlet.Command;

import com.MyServlet.DBManager.Service.AdministratorService;
import com.MyServlet.DBManager.Service.Impl.AdministratorServiceImpl;
import com.MyServlet.DBManager.Service.Impl.StudentServiceImpl;
import com.MyServlet.DBManager.Service.Impl.TeacherServiceImpl;
import com.MyServlet.DBManager.Service.StudentService;
import com.MyServlet.DBManager.Service.TeacherService;
import com.MyServlet.Entity.Student;
import com.MyServlet.Entity.User;
import com.MyServlet.Util.UserRole;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RiseUserPositionCommand implements Command {
    private static final Logger log = Logger.getLogger(RiseUserPositionCommand.class.getName());

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        log.info("In RiseUserPositionCommand");
        log.info("Validating user");
        User administrator = (User) request.getSession().getAttribute("user");
        if (administrator == null || !administrator.getUserRole().equals(UserRole.ADMINISTRATOR)) {
            log.info("Fail. User is not an administrator");
            return "/page/main.jsp";
        }
        int userID = Integer.parseInt(request.getParameter("userID"));
        TeacherService teacherService = new TeacherServiceImpl();
        if (request.getParameter("userRaise").equals("admin")) {
            if(teacherService.selectTeacherCourseID(teacherService.selectTeacherByUserID(userID).getId()) == 0) {
                log.info("Updating the selected user to administrator");
                AdministratorService administratorService = new AdministratorServiceImpl();
                if (administratorService.selectAdministratorByUserID(userID) == null) {
                    administratorService.raiseToAdministrator(userID);
                }
                log.info("RiseUserPositionCommand successful");
            } else {
                request.getSession().setAttribute("exception", "First you need to remove teacher course");
                log.info("Fail. Teacher is not free");
            }
            return "Controller?command=getTeachersInfo";
        }
        log.info("Checking student ban status");
        StudentService studentService = new StudentServiceImpl();
        if (studentService.selectStudentByUserID(userID).isBanStatus()) {
            request.getSession().setAttribute("exception", "First you need to unblock the user");
            log.info("Fail. User is banned");
        } else {
            log.info("Updating the selected user to teacher");
            if (teacherService.selectTeacherByUserID(userID) == null) {
                teacherService.raiseToTeacher(userID);
            }
            log.info("RiseUserPositionCommand successful");
        }
        return "Controller?command=getStudentsInfo&pageNumber=" + request.getParameter("pageNumber");
    }
}
