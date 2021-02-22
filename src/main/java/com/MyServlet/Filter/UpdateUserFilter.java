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
import org.apache.log4j.Logger;

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
    private static final Logger log = Logger.getLogger(UpdateUserFilter.class.getName());

    @Override
    public void init(FilterConfig filterConfig) {
        log.info("In UpdateUserFilter init");
        log.info("Initializing services");
        teacherService = new TeacherServiceImpl();
        studentService = new StudentServiceImpl();
        administratorService = new AdministratorServiceImpl();
        log.info("Success");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("In UpdateUserFilter doFilter");
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user != null) {
            try {
                switch (user.getUserRole()) {
                    case STUDENT:
                        log.info("User is student. Checking ban status");
                        Student student = studentService.selectEntityByID(user.getId());
                        if(student.isBanStatus()) {
                            HttpServletResponse response = (HttpServletResponse) servletResponse;
                            session.invalidate();
                            session = request.getSession();
                            session.setAttribute("exception", "This user is banned");
                            request.getRequestDispatcher("/Controller?command=getUserInfo ").forward(request, response);
                            log.info("User is banned. return");
                            return;
                        }
                        log.info("Updating data");
                        student.setUserRole(UserRole.valueOf("STUDENT"));
                        session.setAttribute("user", student);
                        break;
                    case TEACHER:
                        log.info("User is teacher. Updating data");
                        Teacher teacher = teacherService.selectEntityByID(user.getId());
                        teacher.setUserRole(UserRole.valueOf("TEACHER"));
                        session.setAttribute("user", teacher);
                        break;
                    case ADMINISTRATOR:
                        log.info("User is administrator. Updating data");
                        Administrator administrator = administratorService.selectEntityByID(user.getId());
                        administrator.setUserRole(UserRole.ADMINISTRATOR);
                        session.setAttribute("user", administrator);
                }
            } catch (Exception exception) {
                log.error("Error!",exception);
                throw new ServletException();
            }
        }
        log.info("UpdateUserFilter success");
        filterChain.doFilter(servletRequest, servletResponse);
    }
}