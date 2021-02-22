package com.MyServlet.Command;

import com.MyServlet.DBManager.Service.Impl.StudentServiceImpl;
import com.MyServlet.DBManager.Service.Impl.UserServiceImpl;
import com.MyServlet.DBManager.Service.StudentService;
import com.MyServlet.DBManager.Service.UserService;
import com.MyServlet.Entity.Student;
import com.MyServlet.Entity.User;
import com.MyServlet.Exception.CommandException;
import com.MyServlet.Exception.ConnectionException;
import com.MyServlet.Exception.ServiceException;
import com.MyServlet.Util.Pages;
import com.MyServlet.Util.UserRole;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Represents GetStudentsInfoCommand. Implements command.
 */
public class GetStudentsInfoCommand implements Command {
    private static final Logger log = Logger.getLogger(GetStudentsInfoCommand.class.getName());

    /**
     * This command retrieves all information about students.
     *
     * @param request - HttpServletRequest
     * @param response - HttpServletResponse
     *
     * @throws CommandException - if trouble in service
     * @throws ConnectionException - if trouble with db connection
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException, ConnectionException {
        log.info("In GetStudentsInfoCommand");
        HttpSession session = request.getSession();
        log.info("Validating user");
        User administrator = (User) session.getAttribute("user");
        if (!User.validateUser(administrator, UserRole.ADMINISTRATOR)) {
            log.info("Fail. User is not valid");
            return Pages.MAIN_PAGE;
        }
        int pageNumber = request.getParameter("pageNumber") == null ? 1 : Integer.parseInt(request.getParameter("pageNumber"));
        int rowCount = session.getAttribute("rowCount") == null ? 5 : (int) session.getAttribute("rowCount");
        StudentService studentService = new StudentServiceImpl();
        UserService userService = new UserServiceImpl();
        try {
            log.info("Getting students info");
            LinkedHashMap<String, ArrayList<String>> userData = (LinkedHashMap<String, ArrayList<String>>) userService.getUsersData("STUDENT", pageNumber, rowCount);
            ArrayList<Student> studentDataList = (ArrayList<Student>) studentService.getAllEntitiesByUserID(userData.get("userID"));
            int studentsCount = studentService.getAllStudentsCount();
            session.setAttribute("rowCount", rowCount);
            request.setAttribute("pageNumber", pageNumber);
            request.setAttribute("userData", userData);
            request.setAttribute("dataList", studentDataList);
            request.setAttribute("userType", "student");
            request.setAttribute("maxPage", (int) Math.ceil((double) studentsCount / rowCount));
            log.info("GetStudentsInfoCommand successful");
        } catch (ServiceException serviceException) {
            log.error("Error!", serviceException);
            throw new CommandException(serviceException.getMessage(), serviceException);
        }
        return Pages.ADMIN_USERS_PAGE;
    }
}
