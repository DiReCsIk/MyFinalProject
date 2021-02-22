package com.MyServlet.Command;

import com.MyServlet.Util.Pages;
import org.apache.log4j.Logger;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Represents SignOutCommand. Implements command.
 */
public class SignOutCommand implements Command {
    private static final Logger log = Logger.getLogger(SignOutCommand.class.getName());

    /**
     * This command set sign out user from web application.
     *
     * @param request - HttpServletRequest
     * @param response - HttpServletResponse
     *
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        log.info("In SignOutCommand");
        log.info("Invalidating session");
        request.getSession().invalidate();
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("userPassword") || cookie.getName().equals("userEmail") || cookie.getName().equals("lang")) {
                    cookie.setMaxAge(0);
                    log.info("Invalidating cookies");
                    response.addCookie(cookie);
                }
            }
        }
        log.info("SignOutCommand successful");
        return Pages.MAIN_PAGE;
    }
}
