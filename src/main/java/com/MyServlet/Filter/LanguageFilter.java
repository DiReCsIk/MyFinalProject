package com.MyServlet.Filter;

import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

@WebFilter("/*")
public class LanguageFilter implements Filter {
    private static final Logger log = Logger.getLogger(LanguageFilter.class.getName());

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("In LanguageFilter");
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        Cookie[] cookie = req.getCookies();
        Arrays.stream(cookie)
                .filter(c -> "lang".equals(c.getName()))
                .findFirst()
                .ifPresent(lang -> {
                    log.info("Getting locale from cookie");
                    req.getSession().setAttribute("lang", lang.getValue());
                });
        if (req.getParameter("lang") != null) {
            String lang = req.getParameter("lang");
            log.info("Checking request");
            req.getSession().setAttribute("lang", lang);
            resp.addCookie(new Cookie("lang", lang));
        }
        log.info("LanguageFilter success");
        filterChain.doFilter(servletRequest,servletResponse);
    }
}
