package com.MyServlet.Command;

import com.MyServlet.DBManager.Service.Impl.TeacherServiceImpl;
import com.MyServlet.DBManager.Service.Impl.UserServiceImpl;
import com.MyServlet.DBManager.Service.TeacherService;
import com.MyServlet.DBManager.Service.UserService;
import com.MyServlet.Entity.Teacher;
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
 * Represents GetTeachersInfoCommand. Implements command.
 */
public class GetTeachersInfoCommand implements Command {
    private static final Logger log = Logger.getLogger(GetTeachersInfoCommand.class.getName());

    /**
     * This command retrieves all information about teachers
     *
     * @param request - HttpServletRequest
     * @param response - HttpServletResponse
     *
     * @throws CommandException - if trouble in service
     * @throws ConnectionException - if trouble with db connection
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException, ConnectionException {
        log.info("In GetTeachersInfoCommand");
        HttpSession session = request.getSession();
        log.info("Validating user");
        User administrator = (User) request.getSession().getAttribute("user");
        if (!User.validateUser(administrator, UserRole.ADMINISTRATOR)) {
            log.info("Fail. User is not valid");
            return Pages.MAIN_PAGE;
        }
        int pageNumber = request.getParameter("pageNumber") == null ? 1 : Integer.parseInt(request.getParameter("pageNumber"));
        int rowCount = session.getAttribute("rowCount") == null ? 5 : (int) session.getAttribute("rowCount");
        if(pageNumber <= 0){
            pageNumber = 1;
        }
        TeacherService teacherService = new TeacherServiceImpl();
        UserService userService = new UserServiceImpl();
        try {
            log.info("Getting teachers data");
            LinkedHashMap<String, ArrayList<String>> userData = (LinkedHashMap<String, ArrayList<String>>) userService.getUsersData("TEACHER", pageNumber, rowCount);
            ArrayList<Teacher> teacherDataList = (ArrayList<Teacher>) teacherService.getAllEntitiesByUserID(userData.get("userID"));
            ArrayList<String> teacherCourseStatus = (ArrayList<String>) teacherService.selectTeachersCourseID(teacherDataList);
            int teachersCount = teacherService.getAllTeachersCount();
            session.setAttribute("rowCount", rowCount);
            request.setAttribute("pageNumber", pageNumber);
            request.setAttribute("courseStatus", teacherCourseStatus);
            request.setAttribute("userData", userData);
            request.setAttribute("dataList", teacherDataList);
            request.setAttribute("userType", "teacher");
            request.setAttribute("maxPage", (int) Math.ceil((double) teachersCount / rowCount));
            log.info("GetTeachersInfoCommand success");
        } catch (ServiceException serviceException) {
            log.error("Error!", serviceException);
            throw new CommandException(serviceException.getMessage(), serviceException);
        }
        return Pages.ADMIN_USERS_PAGE;
    }
}
