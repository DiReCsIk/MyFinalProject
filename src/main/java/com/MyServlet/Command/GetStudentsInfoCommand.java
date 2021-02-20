package com.MyServlet.Command;

import com.MyServlet.DBManager.Service.Impl.StudentServiceImpl;
import com.MyServlet.DBManager.Service.Impl.UserServiceImpl;
import com.MyServlet.DBManager.Service.StudentService;
import com.MyServlet.DBManager.Service.UserService;
import com.MyServlet.Entity.Student;
import com.MyServlet.Entity.User;
import com.MyServlet.Util.UserRole;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class GetStudentsInfoCommand implements Command{
    private static final Logger log = Logger.getLogger(GetStudentsInfoCommand.class.getName());

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        log.info("In GetStudentsInfoCommand");
        log.info("Validating user");
        User administrator = (User) request.getSession().getAttribute("user");
        if (administrator == null || !administrator.getUserRole().equals(UserRole.ADMINISTRATOR)) {
            log.info("Fail. User is not an administrator");
            return "/page/main.jsp";
        }
        int pageNumber = 1;
        if(request.getParameter("pageNumber") != null){
            pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
        }
        if (pageNumber <= 0) {
            log.info("Page number < 0. Resetting page number to 0");
            pageNumber = 1;
        }
        int rowCount = (int)request.getSession().getAttribute("rowCount");
        StudentService studentService = new StudentServiceImpl();
        UserService userService = new UserServiceImpl();
        log.info("Getting students info");
        LinkedHashMap<String, ArrayList<String>> userData = (LinkedHashMap<String, ArrayList<String>>) userService.getUsersData("STUDENT", pageNumber, rowCount);
        ArrayList<Student> studentDataList = (ArrayList<Student>) studentService.getAllEntitiesByUserID(userData.get("userID"));
        int studentsCount = studentService.getAllStudentsCount();
        request.getSession().setAttribute("rowCount", 5);
        request.setAttribute("pageNumber", pageNumber);
        request.setAttribute("userData", userData);
        request.setAttribute("dataList", studentDataList);
        request.setAttribute("userType", "student");
        request.setAttribute("maxPage", (int) Math.ceil((double) studentsCount / rowCount));
        log.info("GetStudentsInfoCommand successful");
        return "/page/adminUserManage.jsp";
    }
}
