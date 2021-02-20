package com.MyServlet.Command;

import org.apache.log4j.Logger;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SignOutCommand implements Command {
    private static final Logger log = Logger.getLogger(SignOutCommand.class.getName());

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        log.info("In SignOutCommand");
        log.info("Invalidating session");
        request.getSession().invalidate();
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("userID") || cookie.getName().equals("userRole")) {
                    cookie.setMaxAge(0);
                    log.info("Invalidating cookies");
                    response.addCookie(cookie);
                }
            }
        }
        log.info("SignOutCommand successful");
        return "/page/main.jsp";
    }
}
