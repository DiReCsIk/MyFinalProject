package com.MyServlet.Command;
//TODO регистрация и куки
import com.MyServlet.DBManager.Service.Impl.StudentServiceImpl;
import com.MyServlet.DBManager.Service.Impl.UserServiceImpl;
import com.MyServlet.DBManager.Service.StudentService;
import com.MyServlet.DBManager.Service.UserService;
import com.MyServlet.Entity.Student;
import com.MyServlet.Util.UserRole;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Date;

public class SignUpCommand implements Command {
    private static final Logger log = Logger.getLogger(SignUpCommand.class.getName());

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        log.info("In SignUpCommand");
        HttpSession session = request.getSession();
        log.info("Validating user");
        if(session.getAttribute("user") != null){
            log.info("Fail. User is already signed in");
            return "/page/main.jsp";
        }
        String passwordRegex = "(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$";
        String nameRegex = "^[A-Za-z]+$";
        String mailRegex = "[A-Za-z\\d]+@([a-z]+\\.)+[a-z]{2,4}";
        String name = request.getParameter("name");
        String surName = request.getParameter("surName");
        Date birthDate = Date.valueOf(request.getParameter("birthDate"));
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        UserService userService = new UserServiceImpl();
        if (!name.matches(nameRegex) || !surName.matches(nameRegex) || !password.matches(passwordRegex)
                || !email.matches(mailRegex)) {
            session.setAttribute("exception", "Data doesn't match regex!");
        } else if (userService.selectUserByEmail(request.getParameter("email")) != null) {
            session.setAttribute("exception", "Current mail already exists!");
        } else {
            StudentService studentService = new StudentServiceImpl();
            Student student = new Student();
            student.setName(name);
            student.setSurName(surName);
            student.setBirthDate(birthDate);
            student.setUserRole(UserRole.STUDENT);
            student.setEmail(email);
            student.setPassword(password);
            log.info("Creating new user");
            studentService.insertEntity(student);
            log.info("SignUpCommand successful");
            return "page/main.jsp";
        }
        log.info("Incorrect data. Sign up failure");
        return "page/signUp.jsp";
    }
}
