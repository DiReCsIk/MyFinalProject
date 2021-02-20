package com.MyServlet.Command;

import com.MyServlet.DBManager.Service.Impl.TeacherServiceImpl;
import com.MyServlet.DBManager.Service.TeacherService;
import com.MyServlet.Entity.User;
import com.MyServlet.Util.UserRole;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class AdminCreateCourseCommand implements Command {
    private static final Logger log = Logger.getLogger(AdminCreateCourseCommand.class.getName());

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        log.info("In AdminCreateCourseCommand");
        log.info("Validating user");
        User administrator = (User) request.getSession().getAttribute("user");
        if (administrator == null || !administrator.getUserRole().equals(UserRole.ADMINISTRATOR)) {
            log.info("Fail. User is not an administrator");
            return "/page/main.jsp";
        }
        TeacherService teacherService = new TeacherServiceImpl();
        log.info("Setting course data");
        LinkedHashMap<String, ArrayList<String>> teachers = (LinkedHashMap<String, ArrayList<String>>) teacherService.selectAllTeachersNameAndSurname();
        log.info("Counting free teachers");
        if(teachers.get("id").size() == 0){
            log.info("Fail. There are no free teachers");
            request.setAttribute("exception", "There are no free teachers");
            return "Controller?command=getAdminCourses";
        }
        request.setAttribute("teachers", teachers.get("data"));
        request.setAttribute("teachersID", teachers.get("id"));
        log.info("AdminCreateCourseCommand successful");
        return "/page/createCourse.jsp";
    }
}
