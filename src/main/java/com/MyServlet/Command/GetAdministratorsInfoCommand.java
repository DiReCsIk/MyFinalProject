package com.MyServlet.Command;

import com.MyServlet.DBManager.Service.AdministratorService;
import com.MyServlet.DBManager.Service.Impl.AdministratorServiceImpl;
import com.MyServlet.DBManager.Service.Impl.UserServiceImpl;
import com.MyServlet.DBManager.Service.UserService;
import com.MyServlet.Entity.Administrator;
import com.MyServlet.Entity.User;
import com.MyServlet.Util.UserRole;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class GetAdministratorsInfoCommand implements Command {
    private static final Logger log = Logger.getLogger(GetAdministratorsInfoCommand.class.getName());

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        log.info("In GetAdministratorsInfoCommand");
        User administrator = (User) request.getSession().getAttribute("user");
        log.info("Validating user");
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
        int rowCount = (int) request.getSession().getAttribute("rowCount");
        AdministratorService administratorService = new AdministratorServiceImpl();
        UserService userService = new UserServiceImpl();
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
        return "/page/adminUserManage.jsp";
    }
}
