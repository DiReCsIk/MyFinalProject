package com.MyServlet.Command;

import com.MyServlet.DBManager.Service.AdministratorService;
import com.MyServlet.DBManager.Service.Impl.AdministratorServiceImpl;
import com.MyServlet.DBManager.Service.Impl.UserServiceImpl;
import com.MyServlet.DBManager.Service.UserService;
import com.MyServlet.Entity.Administrator;
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
 * Represents GetAdministratorsInfoCommand. Implements command.
 */
public class GetAdministratorsInfoCommand implements Command {
    private static final Logger log = Logger.getLogger(GetAdministratorsInfoCommand.class.getName());

    /**
     * This command retrieves all information about administrators,
     * from database.
     *
     * @param request - HttpServletRequest
     * @param response - HttpServletResponse
     *
     * @throws CommandException - if trouble in service
     * @throws ConnectionException - if trouble with db connection
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException, ConnectionException {
        log.info("In GetAdministratorsInfoCommand");
        HttpSession session = request.getSession();
        User administrator = (User) request.getSession().getAttribute("user");
        log.info("Validating user");
        if (!User.validateUser(administrator, UserRole.ADMINISTRATOR)) {
            log.info("Fail. User isn't valid");
            return Pages.MAIN_PAGE;
        }
        int pageNumber = request.getParameter("pageNumber") == null ? 1 : Integer.parseInt(request.getParameter("pageNumber"));
        int rowCount = session.getAttribute("rowCount") == null ? 5 : (int) session.getAttribute("rowCount");
        AdministratorService administratorService = new AdministratorServiceImpl();
        if(pageNumber <= 0){
            pageNumber = 1;
        }
        UserService userService = new UserServiceImpl();
        try {
            log.info("Getting administrators info");
            LinkedHashMap<String, ArrayList<String>> userData = (LinkedHashMap<String, ArrayList<String>>) userService.getUsersData("ADMINISTRATOR", pageNumber, rowCount);
            ArrayList<Administrator> administratorDataList = (ArrayList<Administrator>) administratorService.getAllEntitiesByUserID(userData.get("userID"));
            int administratorsCount = administratorService.getAllAdministratorsCount();
            request.setAttribute("pageNumber", pageNumber);
            request.setAttribute("userData", userData);
            request.setAttribute("dataList", administratorDataList);
            request.setAttribute("userType", "administrator");
            request.setAttribute("maxPage", (int) Math.ceil((double) administratorsCount / rowCount));
            log.info("GetAdministratorsInfoCommand successful");
        } catch (ServiceException serviceException) {
            log.error("Error!", serviceException);
            throw new CommandException(serviceException.getMessage(), serviceException);
        }
        return Pages.ADMIN_USERS_PAGE;
    }
}
