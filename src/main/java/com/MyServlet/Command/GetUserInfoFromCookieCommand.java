package com.MyServlet.Command;

import com.MyServlet.DBManager.Service.AdministratorService;
import com.MyServlet.DBManager.Service.Impl.AdministratorServiceImpl;
import com.MyServlet.DBManager.Service.Impl.StudentServiceImpl;
import com.MyServlet.DBManager.Service.Impl.TeacherServiceImpl;
import com.MyServlet.DBManager.Service.Impl.UserServiceImpl;
import com.MyServlet.DBManager.Service.StudentService;
import com.MyServlet.DBManager.Service.TeacherService;
import com.MyServlet.DBManager.Service.UserService;
import com.MyServlet.Entity.Administrator;
import com.MyServlet.Entity.Student;
import com.MyServlet.Entity.Teacher;
import com.MyServlet.Entity.User;
import com.MyServlet.Exception.CommandException;
import com.MyServlet.Exception.ConnectionException;
import com.MyServlet.Exception.ServiceException;
import com.MyServlet.Util.Pages;
import com.MyServlet.Util.UserRole;
import org.apache.log4j.Logger;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Represents GetUserInfoFromCookieCommand. Implements command.
 */
public class GetUserInfoFromCookieCommand implements Command {
    private static final Logger log = Logger.getLogger(GetUserInfoFromCookieCommand.class.getName());

    /**
     * This command retrieves all information about user from cookies.
     *
     * @param request - HttpServletRequest
     * @param response - HttpServletResponse
     *
     * @throws CommandException - if trouble in service
     * @throws ConnectionException - if trouble with db connection
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException, ConnectionException {
        log.info("In GetUserInfoFromCookieCommand");
        Cookie[] cookies = request.getCookies();
        HttpSession session = request.getSession();
        String userEmail = "";
        String userPassword = "";
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("userEmail")) {
                userEmail = cookie.getValue();
            }
            if (cookie.getName().equals("userPassword")) {
                userPassword = cookie.getValue();
            }
        }
        UserService userService = new UserServiceImpl();
        try {
            log.info("verifying user");
            User user = userService.selectUserByEmail(userEmail);
            if (user != null && userPassword.equals(user.getPassword())) {
                log.info("User is valid");
                switch (user.getUserRole()) {
                    case TEACHER:
                        TeacherService teacherService = new TeacherServiceImpl();
                        Teacher teacher = teacherService.selectTeacherByUserID(user.getId());
                        teacher.setPassword(userPassword);
                        teacher.setEmail(userEmail);
                        teacher.setUserRole(UserRole.TEACHER);
                        session.setAttribute("user", teacher);
                        log.info("User is teacher");
                        break;
                    case ADMINISTRATOR:
                        AdministratorService administratorService = new AdministratorServiceImpl();
                        Administrator administrator = administratorService.selectAdministratorByUserID(user.getId());
                        administrator.setPassword(userPassword);
                        administrator.setEmail(userEmail);
                        administrator.setUserRole(UserRole.ADMINISTRATOR);
                        session.setAttribute("user", administrator);
                        log.info("User is administrator");
                        break;
                    default:
                        StudentService studentService = new StudentServiceImpl();
                        Student student = studentService.selectStudentByUserID(user.getId());
                        student.setPassword(userPassword);
                        student.setEmail(userEmail);
                        student.setUserRole(UserRole.STUDENT);
                        session.setAttribute("user",student );
                        log.info("User is student");
                }
                log.info("Success in GetUserInfoFromCookieCommand");
                session.setAttribute("email", user.getEmail());
                return Pages.MAIN_PAGE;
            }
            log.info("GetUserInfoFromCookieCommand successful");
            Cookie emailCookie = new Cookie("userEmail", "");
            Cookie passwordCookie = new Cookie("userPassword", "");
            emailCookie.setMaxAge(0);
            passwordCookie.setMaxAge(0);
            response.addCookie(emailCookie);
            response.addCookie(passwordCookie);
        } catch (ServiceException serviceException) {
            log.error("Error!", serviceException);
            throw new CommandException(serviceException.getMessage(), serviceException);
        }
        return Pages.SIGN_IN_PAGE;
    }
}
