package com.MyServlet.Command;

import com.MyServlet.DBManager.Service.CourseService;
import com.MyServlet.DBManager.Service.Impl.CourseServiceImpl;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class InitAvailableCoursesCommand implements Command {
    private static final Logger log = Logger.getLogger(InitAvailableCoursesCommand.class.getName());
    //TODO Почему я храню здесь в сессии. Проверить
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        log.info("In InitAvailableCoursesCommand");
        HttpSession session = request.getSession();
        log.info("Setting session attributes");
        session.setAttribute("rowCount", 5);
        log.info("InitAvailableCoursesCommand successful");
        return new GetAvailableCoursesCommand().execute(request, response);
    }
}
