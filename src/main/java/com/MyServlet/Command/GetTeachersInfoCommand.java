package com.MyServlet.Command;

import com.MyServlet.DBManager.Service.Impl.TeacherServiceImpl;
import com.MyServlet.DBManager.Service.Impl.UserServiceImpl;
import com.MyServlet.DBManager.Service.TeacherService;
import com.MyServlet.DBManager.Service.UserService;
import com.MyServlet.Entity.Teacher;
import com.MyServlet.Entity.User;
import com.MyServlet.Util.UserRole;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class GetTeachersInfoCommand implements Command {
    private static final Logger log = Logger.getLogger(GetTeachersInfoCommand.class.getName());

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        log.info("In GetTeachersInfoCommand");
        User administrator = (User) request.getSession().getAttribute("user");
        log.info("Validating user");
        if (administrator == null || !administrator.getUserRole().equals(UserRole.ADMINISTRATOR)) {
            log.info("Fail. User is not an administrator");
            return "/page/main.jsp";
        }
        HttpSession session = request.getSession();
        int pageNumber = 1;
        if(request.getParameter("pageNumber") != null){
            pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
        }
        if (pageNumber <= 0) {
            log.info("Page number < 0. Resetting page number to 0");
            pageNumber = 1;
        }
        int rowCount = (int)session.getAttribute("rowCount");
        TeacherService teacherService = new TeacherServiceImpl();
        UserService userService = new UserServiceImpl();
        log.info("Getting teachers data");
        LinkedHashMap<String, ArrayList<String>> userData = (LinkedHashMap<String, ArrayList<String>>) userService.getUsersData("TEACHER", pageNumber, rowCount);
        ArrayList<Teacher> teacherDataList = (ArrayList<Teacher>) teacherService.getAllEntitiesByUserID(userData.get("userID"));
        ArrayList<String> teacherCourseStatus = (ArrayList<String>) teacherService.selectTeachersCourseID(teacherDataList);
        int teachersCount = teacherService.getAllTeachersCount();
        request.setAttribute("pageNumber", pageNumber);
        request.setAttribute("courseStatus", teacherCourseStatus);
        request.setAttribute("userData", userData);
        request.setAttribute("dataList", teacherDataList);
        request.setAttribute("userType", "teacher");
        request.setAttribute("maxPage", (int) Math.ceil((double) teachersCount / rowCount));
        log.info("GetTeachersInfoCommand success");
        return "/page/adminUserManage.jsp";
    }
}
