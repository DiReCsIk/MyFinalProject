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

public class SignInCommand implements Command {
    private static final Logger log = Logger.getLogger(SignInCommand.class.getName());

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException, ConnectionException {
        log.info("In SignInCommand");
        HttpSession session = request.getSession();
        UserService userService = new UserServiceImpl();
        try {
            User user = userService.selectUserByEmail(request.getParameter("email"));
            log.info("Validating data");
            if (user == null || !user.getPassword().equals(request.getParameter("password"))) {
                session.setAttribute("exception", "Incorrect password or email");
                log.info("Fail. Incorrect data");
                return Pages.SIGN_IN_PAGE;
            }
            session.setAttribute("email", request.getParameter("email"));
            switch (user.getUserRole()) {
                case STUDENT:
                    StudentService studentService = new StudentServiceImpl();
                    Student student = studentService.selectStudentByUserID(user.getId());
                    student.setUserRole(UserRole.valueOf("STUDENT"));
                    session.setAttribute("user", student);
                    log.info("User is student");
                    break;
                case TEACHER:
                    TeacherService teacherService = new TeacherServiceImpl();
                    Teacher teacher = teacherService.selectTeacherByUserID(user.getId());
                    teacher.setUserRole(UserRole.valueOf("TEACHER"));
                    session.setAttribute("user", teacher);
                    log.info("User is teacher");
                    break;
                case ADMINISTRATOR:
                    AdministratorService administratorService = new AdministratorServiceImpl();
                    Administrator administrator = administratorService.selectAdministratorByUserID(user.getId());
                    administrator.setUserRole(UserRole.valueOf("ADMINISTRATOR"));
                    log.info("User is administrator");
                    session.setAttribute("user", administrator);
            }
            if (request.getParameter("rememberBox") != null) {
                log.info("Adding userRole and userID cookies");
                Cookie roleCookie = new Cookie("userRole", String.valueOf(user.getUserRole()));
                Cookie userIDCookie = new Cookie("userID", user.getId() + "");
                response.addCookie(roleCookie);
                response.addCookie(userIDCookie);
            }
            log.info("SignInCommand successful");
            if (session.getAttribute("toCourse") != null) {
                session.removeAttribute("toCourse");
                return Pages.GET_AVAILABLE_COURSE;
            }
        } catch (ServiceException serviceException) {
            log.error("Error!", serviceException);
            throw new CommandException(serviceException.getMessage(), serviceException);
        }
        return Pages.MAIN_PAGE;
    }
}
