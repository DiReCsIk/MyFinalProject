package com.MyServlet.Controller;

import com.MyServlet.Command.Command;
import com.MyServlet.Command.CommandContainer;
import com.MyServlet.Exception.CommandException;
import com.MyServlet.Exception.ConnectionException;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * This is the controller servlet.
 * It processes all commands that fall into it.
 * In CommandException case - redirect to 404 error page.
 * In ConnectionException ase - redirect to 505 error page.
 *
 */
@WebServlet("/Controller")
public class Controller extends HttpServlet {
    private static final Logger log = Logger.getLogger(Controller.class);
    /**
     * This is doGet method. Execute all HTTP GET requests.
     *
     * @param request - HttpServletRequest
     * @param response - HttpServletResponse
     *
     * @throws ServletException - if trouble with servlet
     * @throws IOException - if trouble with IO operation
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.info("In Controller doGet method");
        String commandName = request.getParameter("command");
        try {
            Command command = CommandContainer.getCommand(commandName);
            log.info("Try to execute " + commandName);
            String respPage = command.execute(request, response);
            log.info("Forwarding to " + respPage);
            request.getRequestDispatcher(respPage).forward(request, response);
        } catch (CommandException commandException) {
            log.error("Error! ", commandException);
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
        catch (ConnectionException connectionException) {
            log.error("Error! ", connectionException);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * This is doPost method. Execute all HTTP POST requests.
     *
     * @param request - HttpServletRequest
     * @param response - HttpServletResponse
     *
     * @throws IOException - if trouble with IO operation
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.info("In Controller doPost method");
        String commandName = request.getParameter("command");
        try {
            Command command = CommandContainer.getCommand(commandName);
            log.info("Try to execute " + commandName);
            String respPage = command.execute(request, response);
            log.info("Redirecting to " + respPage);
            response.sendRedirect(respPage);
        } catch (CommandException commandException) {
            log.error("Error! ", commandException);
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
        catch (ConnectionException connectionException) {
            log.error("Error! ", connectionException);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
