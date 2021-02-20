package com.MyServlet.Command;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class InitAdminCourseCommand implements Command {
    private static final Logger log = Logger.getLogger(InitAdminCourseCommand.class.getName());

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        log.info("In InitAdminCourseCommand");
        HttpSession session = request.getSession();
        log.info("Setting session attributes");
        session.setAttribute("rowCount", 5);
        log.info("InitAdminCourseCommand successful");
        return new GetAdminCoursesCommand().execute(request, response);
    }
}
