package com.MyServlet.Command;

import com.MyServlet.DBManager.Service.Impl.StudentServiceImpl;
import com.MyServlet.DBManager.Service.StudentService;
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
 * Represents SetBanStatusCommand. Implements command.
 */
public class SetBanStatusCommand implements Command {
    private static final Logger log = Logger.getLogger(SetBanStatusCommand.class.getName());

    /**
     * This command set ban status to user.
     *
     * @param request - HttpServletRequest
     * @param response - HttpServletResponse
     *
     * @throws CommandException - if trouble in service
     * @throws ConnectionException - if trouble with db connection
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException, ConnectionException {
        log.info("In SetBanStatusCommand");
        User administrator = (User) request.getSession().getAttribute("user");
        if (!User.validateUser(administrator, UserRole.ADMINISTRATOR)) {
            log.info("Fail. User is not valid");
            return Pages.MAIN_PAGE;
        }
        int userID = Integer.parseInt(request.getParameter("userID"));
        StudentService studentService = new StudentServiceImpl();
        try {
            log.info("Setting new ban status");
            switch (request.getParameter("banStatus")) {
                case "ban":
                    studentService.banStudent(userID);
                    break;
                case "unban":
                    studentService.unbanStudent(userID);
            }
            log.info("SetBanStatusCommand successful");
        } catch (
                ServiceException serviceException) {
            log.error("Error!", serviceException);
            throw new CommandException(serviceException.getMessage(), serviceException);
        }
        return Pages.GET_STUDENTS_INFO + "&pageNumber=" + request.getParameter("pageNumber");
    }
}
