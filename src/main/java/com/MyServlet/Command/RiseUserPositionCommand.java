package com.MyServlet.Command;

import com.MyServlet.DBManager.Service.AdministratorService;
import com.MyServlet.DBManager.Service.Impl.AdministratorServiceImpl;
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
import javax.servlet.http.HttpSession;

/**
 * Represents RiseUserPositionCommand. Implements command.
 */
public class RiseUserPositionCommand implements Command {
    private static final Logger log = Logger.getLogger(RiseUserPositionCommand.class.getName());

    /**
     * This command rise user to position above.
     *
     * @param request - HttpServletRequest
     * @param response - HttpServletResponse
     *
     * @throws CommandException - if trouble in service
     * @throws ConnectionException - if trouble with db connection
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException, ConnectionException {
        log.info("In RiseUserPositionCommand");
        HttpSession session = request.getSession();
        log.info("Validating user");
        User administrator = (User) request.getSession().getAttribute("user");
        if (!User.validateUser(administrator, UserRole.ADMINISTRATOR)) {
            log.info("Fail. User is not valid");
            return Pages.MAIN_PAGE;
        }
        int userID = Integer.parseInt(request.getParameter("userID"));
        TeacherService teacherService = new TeacherServiceImpl();
        try {
            if (request.getParameter("userRaise").equals("admin")) {
                if (teacherService.selectTeacherCourseID(teacherService.selectTeacherByUserID(userID).getId()) == 0) {
                    log.info("Updating the selected user to administrator");
                    AdministratorService administratorService = new AdministratorServiceImpl();
                    if (administratorService.selectAdministratorByUserID(userID) == null) {
                        administratorService.raiseToAdministrator(userID);
                    }
                    log.info("RiseUserPositionCommand successful");
                } else {
                    session.setAttribute("exception", "First you need to remove teacher course");
                    log.info("Fail. Teacher is not free");
                }
                return Pages.GET_TEACHERS_INFO + "&pageNumber=" + request.getParameter("pageNumber");
            }
            log.info("Checking student ban status");
            StudentService studentService = new StudentServiceImpl();
            if (studentService.selectStudentByUserID(userID).isBanStatus()) {
                session.setAttribute("exception", "First you need to unblock the user");
                log.info("Fail. User is banned");
            } else {
                log.info("Updating the selected user to teacher");
                if (teacherService.selectTeacherByUserID(userID) == null) {
                    teacherService.raiseToTeacher(userID);
                }
                log.info("RiseUserPositionCommand successful");
            }
        } catch (
                ServiceException serviceException) {
            log.error("Error!", serviceException);
            throw new CommandException(serviceException.getMessage(), serviceException);
        }
        return Pages.GET_STUDENTS_INFO + "&pageNumber=" + request.getParameter("pageNumber");
    }
}
