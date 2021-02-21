package com.MyServlet.Command;

import com.MyServlet.DBManager.Service.Impl.CourseServiceImpl;
import com.MyServlet.Entity.Student;
import com.MyServlet.Exception.CommandException;
import com.MyServlet.Exception.ConnectionException;
import com.MyServlet.Exception.ServiceException;
import com.MyServlet.Util.Pages;
import com.MyServlet.Util.UserRole;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class EnrollStudentOnCourseCommand implements Command {
    private static final Logger log = Logger.getLogger(EnrollStudentOnCourseCommand.class.getName());

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException, ConnectionException {
        log.info("In EnrollStudentOnCourseCommand");
        HttpSession session = request.getSession();
        Student student = (Student) session.getAttribute("user");
        log.info("Validating user");
        if (student == null || !student.getUserRole().equals(UserRole.STUDENT)) {
            log.info("Fail. User is not signed in or user is not a student");
            session.setAttribute("exception", "Sign in first");
            session.setAttribute("toCourse", "true");
            return Pages.SIGN_IN_PAGE;
        }
        log.info("Enrolling a user on a course");
        int courseID = Integer.parseInt(request.getParameter("courseID"));
        CourseServiceImpl courseService = new CourseServiceImpl();
        try {
            courseService.insertStudentToCourse(student.getId(), courseID);
            log.info("EnrollStudentOnCourseCommand successful");
        } catch (ServiceException serviceException) {
            log.error("Error!", serviceException);
            throw new CommandException(serviceException.getMessage(), serviceException);
        }
        return Pages.GET_AVAILABLE_COURSE;
    }
}
