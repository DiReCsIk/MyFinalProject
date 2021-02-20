package com.MyServlet.Filter;

import com.MyServlet.DBManager.Service.AdministratorService;
import com.MyServlet.DBManager.Service.Impl.AdministratorServiceImpl;
import com.MyServlet.DBManager.Service.Impl.StudentServiceImpl;
import com.MyServlet.DBManager.Service.Impl.TeacherServiceImpl;
import com.MyServlet.DBManager.Service.StudentService;
import com.MyServlet.DBManager.Service.TeacherService;
import com.MyServlet.Entity.Administrator;
import com.MyServlet.Entity.Student;
import com.MyServlet.Entity.Teacher;
import com.MyServlet.Entity.User;
import com.MyServlet.Util.UserRole;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/*")
public class UpdateUserFilter implements Filter {
    private TeacherService teacherService;
    private StudentService studentService;
    private AdministratorService administratorService;

    @Override
    public void init(FilterConfig filterConfig) {
        teacherService = new TeacherServiceImpl();
        studentService = new StudentServiceImpl();
        administratorService = new AdministratorServiceImpl();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user != null) {
            try {
                switch (user.getUserRole()) {
                    case STUDENT:
                        Student student = studentService.selectEntityByID(user.getId());
                        if(student.isBanStatus()) {
                            HttpServletResponse response = (HttpServletResponse) servletResponse;
                            session.invalidate();
                            session = request.getSession();
                            session.setAttribute("exception", "This user is banned");
                            request.getRequestDispatcher("/Controller?command=getUserInfo ").forward(request, response);
                            return;
                        }
                        student.setUserRole(UserRole.valueOf("STUDENT"));
                        session.setAttribute("user", student);
                        break;
                    case TEACHER:
                        Teacher teacher = teacherService.selectEntityByID(user.getId());
                        teacher.setUserRole(UserRole.valueOf("TEACHER"));
                        session.setAttribute("user", teacher);
                        break;
                    case ADMINISTRATOR:
                        Administrator administrator = administratorService.selectEntityByID(user.getId());
                        administrator.setUserRole(UserRole.ADMINISTRATOR);
                        session.setAttribute("user", administrator);
                }
            } catch (Exception exception) {
                exception.printStackTrace();
                throw new ServletException();
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
        /*User user = (User) session.getAttribute("user");
        System.out.println("user filter");
        if (user != null) {
            try {
                user = userService.getUserById(user.getId());
                session.setAttribute("user",user);
            } catch (ServiceException exception) {
                //logging
                throw new ServletException(exception);
            }*/
    }
}