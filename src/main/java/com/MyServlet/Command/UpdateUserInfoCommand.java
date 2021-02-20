package com.MyServlet.Command;
//TODO пересмотреь переменніе в сесиях

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
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Date;

public class UpdateUserInfoCommand implements Command {
    private static final Logger log = Logger.getLogger(UpdateUserInfoCommand.class.getName());

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        log.info("In UpdateUserInfoCommand");
        HttpSession session = request.getSession();
        log.info("Validating user");
        if (session.getAttribute("user") == null) {
            log.info("Fail. User is not signed in");
            return "page/main.jsp";
        }
        String currentPassword = request.getParameter("currentPassword");
        String email = (String) session.getAttribute("email");
        String name = request.getParameter("name");
        String surName = request.getParameter("surName");
        String newPassword = request.getParameter("newPassword");
        Date birthday = Date.valueOf(request.getParameter("newBirthday"));
        UserService userService = new UserServiceImpl();
        User user = userService.selectUserByEmail(email);
        String passwordRegex = "(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$";
        String nameRegex = "^[A-Za-z]+$";
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
        return "page/myData.jsp";
    }
}
