package com.MyServlet.Filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter("/*")
public class LanguageFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        if (httpServletRequest.getParameter("lang") != null) {
            httpServletRequest.getSession().setAttribute("lang", httpServletRequest.getParameter("lang"));
        }
        filterChain.doFilter(servletRequest,servletResponse);
    }
}
