package com.MyServlet.Command;

import com.MyServlet.DBManager.Service.Impl.TeacherServiceImpl;
import com.MyServlet.DBManager.Service.TeacherService;
import com.MyServlet.Exception.CommandException;
import com.MyServlet.Exception.ConnectionException;
import com.MyServlet.Exception.ServiceException;
import com.MyServlet.Util.Pages;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class AboutUsCommand implements Command {
    private static final Logger log = Logger.getLogger(AboutUsCommand.class.getName());

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException, ConnectionException {
        log.info("In AboutsUsCommand");
        TeacherService teacherService = new TeacherServiceImpl();
        try {
            LinkedHashMap<String, ArrayList<String>> threeBestTeachers = (LinkedHashMap<String, ArrayList<String>>) teacherService.selectThreeBestTeachers();
            ArrayList<Integer> teachersCourseCount = new ArrayList<>();
            for (String id : threeBestTeachers.get("id")) {
                teachersCourseCount.add(teacherService.selectTeacherCourseCount(Integer.parseInt(id)));
            }
            request.setAttribute("teachersMap", threeBestTeachers);
            request.setAttribute("teachersCourseCount", teachersCourseCount);
        } catch (ServiceException serviceException) {
            log.error("Error!", serviceException);
            throw new CommandException(serviceException.getMessage(), serviceException);
        }
        log.info("Success in AboutUsCommand");
        return Pages.ABOUT_US;
    }
}
