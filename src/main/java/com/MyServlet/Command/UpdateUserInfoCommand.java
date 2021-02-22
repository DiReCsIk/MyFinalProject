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
import org.apache.log4j.Logger;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Date;

/**
 * Represents UpdateUserInfoCommand. Implements command.
 */
public class UpdateUserInfoCommand implements Command {
    private static final Logger log = Logger.getLogger(UpdateUserInfoCommand.class.getName());

    /**
     * This command update user info.
     *
     * @param request - HttpServletRequest
     * @param response - HttpServletResponse
     *
     * @throws CommandException - if trouble in service
     * @throws ConnectionException - if trouble with db connection
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException, ConnectionException {
        log.info("In UpdateUserInfoCommand");
        HttpSession session = request.getSession();
        log.info("Validating user");
        if (session.getAttribute("user") == null) {
            log.info("Fail. User is not signed in");
            return Pages.MAIN_PAGE;
        }
        String currentPassword = request.getParameter("currentPassword");
        String email = (String) session.getAttribute("email");
        String name = request.getParameter("name");
        String surName = request.getParameter("surName");
        String newPassword = request.getParameter("newPassword");
        Date birthday = Date.valueOf(request.getParameter("newBirthday"));
        UserService userService = new UserServiceImpl();
        String passwordRegex = "(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$";
        String nameRegex = "^[A-Za-z]+$";
        try {
            User user = userService.selectUserByEmail(email);
            if (!user.getPassword().equals(currentPassword)) {
                log.info("Fail. Incorrect old password");
                session.setAttribute("exception", "Incorrect old password!");
            } else if (!name.matches(nameRegex) || !surName.matches(nameRegex) || (!newPassword.equals("") && !newPassword.matches(passwordRegex))) {
                log.info("Fail. The entered data does not match the regular expression");
                session.setAttribute("exception", "Data doesn't match regex!");
            } else {
                if (!newPassword.equals("")) {
                    log.info("Setting new password");
                    user.setPassword(newPassword);
                    Cookie[] cookies = request.getCookies();
                    for (Cookie cookie : cookies) {
                        if (cookie.getName().equals("userPassword")) {
                            cookie.setValue(newPassword);
                            response.addCookie(cookie);
                            break;
                        }
                    }
                    userService.updateEntity(user);
                }
                log.info("Updating user data");
                switch (((User) session.getAttribute("user")).getUserRole()) {
                    case ADMINISTRATOR:
                        AdministratorService administratorService = new AdministratorServiceImpl();
                        Administrator administrator = (Administrator) session.getAttribute("user");
                        administrator.setName(name);
                        administrator.setSurName(surName);
                        administrator.setBirthDate(birthday);
                        administratorService.updateEntity(administrator);
                        break;
                    case TEACHER:
                        TeacherService teacherService = new TeacherServiceImpl();
                        Teacher teacher = (Teacher) session.getAttribute("user");
                        teacher.setName(name);
                        teacher.setSurName(surName);
                        teacher.setBirthDate(birthday);
                        teacherService.updateEntity(teacher);
                        break;
                    default:
                        StudentService studentService = new StudentServiceImpl();
                        Student student = (Student) session.getAttribute("user");
                        student.setName(name);
                        student.setSurName(surName);
                        student.setBirthDate(birthday);
                        studentService.updateEntity(student);
                        break;
                }
            }
            log.info("UpdateUserInfoCommand successful");
        } catch (ServiceException serviceException) {
            log.error("Error!", serviceException);
            throw new CommandException(serviceException.getMessage(), serviceException);
        }
        return Pages.MY_DATA_PAGE;
    }
}
