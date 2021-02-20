package com.MyServlet.Command;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class InitAdminUserCommand implements Command {
    private static final Logger log = Logger.getLogger(InitAdminUserCommand.class.getName());

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        log.info("In InitAdminUserCommand");
        HttpSession session = request.getSession();
        log.info("Setting session attributes");
        session.setAttribute("rowCount", 5);
        log.info("InitAdminUserCommand successful");
        return new GetStudentsInfoCommand().execute(request, response);
    }
}
