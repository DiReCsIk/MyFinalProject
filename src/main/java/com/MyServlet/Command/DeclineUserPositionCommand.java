package com.MyServlet.Command;

import com.MyServlet.DBManager.Service.Impl.StudentServiceImpl;
import com.MyServlet.DBManager.Service.Impl.TeacherServiceImpl;
import com.MyServlet.DBManager.Service.StudentService;
import com.MyServlet.DBManager.Service.TeacherService;
import com.MyServlet.Entity.User;
import com.MyServlet.Exception.CommandException;
import com.MyServlet.Exception.ConnectionException;
import com.MyServlet.Exception.ServiceException;
import com.MyServlet.Util.Pages;
import com.MyServlet.Util.UserRole;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Represents DeclineUserPositionCommand. Implements command.
 */
public class DeclineUserPositionCommand implements Command {
    private static final Logger log = Logger.getLogger(DeclineUserPositionCommand.class.getName());

    /**
     * This command decline user to one position below.
     *
     * @param request - HttpServletRequest
     * @param response - HttpServletResponse
     *
     * @throws CommandException - if trouble in service
     * @throws ConnectionException - if trouble with db connection
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException, ConnectionException {
        log.info("In DeclineUserPositionCommand");
        User administrator = (User) request.getSession().getAttribute("user");
        log.info("Validating user");
        if (!User.validateUser(administrator, UserRole.ADMINISTRATOR)) {
            log.info("Fail. User is not valid");
            return Pages.MAIN_PAGE;
        }
        int userID = Integer.parseInt(request.getParameter("userID"));
        TeacherService teacherService = new TeacherServiceImpl();
        StudentService studentService = new StudentServiceImpl();
        try {
            log.info("Checking a user for decline");
            if (teacherService.selectTeacherByUserID(userID) != null && studentService.selectStudentByUserID(userID) == null
                    && teacherService.selectTeacherCourseID(teacherService.selectTeacherByUserID(userID).getId()) == 0) {
                log.info("Declining user");
                studentService.declineToStudent(userID);
                log.info("DeclineUserPositionCommand successful");
            } else {
                request.getSession().setAttribute("exception", "First you need to remove teacher course");
                log.info("Fail. Teacher is not free");
            }
        } catch (ServiceException serviceException) {
            log.error("Error!", serviceException);
            throw new CommandException(serviceException.getMessage(), serviceException);
        }
        return Pages.GET_TEACHERS_INFO + "&pageNumber=" + request.getParameter("pageNumber");
    }
}
