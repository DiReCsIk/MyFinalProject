package com.MyServlet.Controller;

import com.MyServlet.Command.Command;
import com.MyServlet.Command.CommandContainer;
import com.MyServlet.Command.UpdateUserInfoCommand;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/Controller")
public class Controller extends HttpServlet {
    private static final Logger log = Logger.getLogger(Controller.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.info("In Controller doGet method");
        String commandName = request.getParameter("command");
        Command command = CommandContainer.getCommand(commandName);
        String respPage = "error_404.jsp";
        try {
            log.info("Try to execute " + commandName);
            respPage = command.execute(request, response);
        } catch (Exception exception) {
            log.error("Error! ", exception);
            exception.printStackTrace();
        }
        log.info("Forwarding to " + respPage);
        request.getRequestDispatcher(respPage).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.info("In Controller doPost method");
        String commandName = request.getParameter("command");
        Command command = CommandContainer.getCommand(commandName);
        String respPage = "error_404.jsp";
        try {
            log.info("Try to execute " + commandName);
            respPage = command.execute(request, response);
        } catch (Exception exception) {
            //TODO
            log.error("Error! ", exception);
            System.out.println(exception.getMessage());
        }
        log.info("Redirecting to " + respPage);
        response.sendRedirect(respPage);
    }
}
