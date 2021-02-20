package com.MyServlet.Command;

import com.MyServlet.DBManager.Service.Impl.StudentServiceImpl;
import com.MyServlet.DBManager.Service.StudentService;
import com.MyServlet.Entity.User;
import com.MyServlet.Util.UserRole;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SetBanStatusCommand implements Command {
    private static final Logger log = Logger.getLogger(SetBanStatusCommand.class.getName());

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        log.info("In SetBanStatusCommand");
        log.info("Validating user");
        User administrator = (User) request.getSession().getAttribute("user");
        if (administrator == null || !administrator.getUserRole().equals(UserRole.ADMINISTRATOR)) {
            log.info("Fail. User is not an administrator ");
            return "/page/main.jsp";
        }
        int userID = Integer.parseInt(request.getParameter("userID"));
        StudentService studentService = new StudentServiceImpl();
        log.info("Setting new ban status");
        switch (request.getParameter("banStatus")){
            case "ban":
                studentService.banStudent(userID);
                break;
            case "unban":
                studentService.unbanStudent(userID);
        }
        log.info("SetBanStatusCommand successful");
        return "/Controller?command=getStudentsInfo&pageNumber=" + request.getParameter("pageNumber");
    }
}
