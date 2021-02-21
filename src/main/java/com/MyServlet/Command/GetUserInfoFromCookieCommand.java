package com.MyServlet.Command;

import com.MyServlet.DBManager.Service.AdministratorService;
import com.MyServlet.DBManager.Service.Impl.AdministratorServiceImpl;
import com.MyServlet.DBManager.Service.Impl.StudentServiceImpl;
import com.MyServlet.DBManager.Service.Impl.TeacherServiceImpl;
import com.MyServlet.DBManager.Service.Impl.UserServiceImpl;
import com.MyServlet.DBManager.Service.StudentService;
import com.MyServlet.DBManager.Service.TeacherService;
import com.MyServlet.DBManager.Service.UserService;
import com.MyServlet.Entity.Teacher;
import com.MyServlet.Exception.CommandException;
import com.MyServlet.Exception.ConnectionException;
import com.MyServlet.Exception.ServiceException;
import com.MyServlet.Util.Pages;
import org.apache.log4j.Logger;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Arrays;

public class GetUserInfoFromCookieCommand implements Command {
    private static final Logger log = Logger.getLogger(GetUserInfoFromCookieCommand.class.getName());

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException, ConnectionException {
        log.info("In GetUserInfoFromCookieCommand");
        Cookie[] cookies = request.getCookies();
        if (Arrays.stream(cookies).noneMatch(cookie -> cookie.getName().equals("userRole"))
                || Arrays.stream(cookies).noneMatch(cookie -> cookie.getName().equals("userID"))) {
            log.info("Cookies are empty");
            return Pages.SIGN_IN_PAGE;
        }
        HttpSession session = request.getSession();
        String role = Arrays.stream(cookies).filter(cookie -> cookie.getName().equals("userRole")).findFirst().get().getValue();
        session.setAttribute("userRole", role);
        int userID = Integer.parseInt(Arrays.stream(cookies).filter(cookie -> cookie.getName().equals("userID")).findFirst().get().getValue());
        UserService userService = new UserServiceImpl();
        try {
            session.setAttribute("email", userService.selectEntityByID(userID).getEmail());
            switch (role) {
                case "teacher":
                    TeacherService teacherService = new TeacherServiceImpl();
                    Teacher teacher = teacherService.selectTeacherByUserID(userID);
                    session.setAttribute("courseID", teacherService.selectTeacherCourseID(teacher.getId()));
                    session.setAttribute("studentsID", teacherService.selectTeacherStudentsID(teacher.getId()));
                    session.setAttribute("user", teacherService.selectTeacherByUserID(userID));
                    log.info("User is teacher");
                    break;
                case "administrator":
                    AdministratorService administratorService = new AdministratorServiceImpl();
                    session.setAttribute("user", administratorService.selectAdministratorByUserID(userID));
                    log.info("User is administrator");
                    break;
                default:
                    StudentService studentService = new StudentServiceImpl();
                    session.setAttribute("user", studentService.selectStudentByUserID(userID));
                    log.info("User is student");
            }
            log.info("GetUserInfoFromCookieCommand successful");
        } catch (ServiceException serviceException) {
            log.error("Error!", serviceException);
            throw new CommandException(serviceException.getMessage(), serviceException);
        }
        return Pages.MAIN_PAGE;
    }
}
