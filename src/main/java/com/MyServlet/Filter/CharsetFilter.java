package com.MyServlet.Filter;

import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter("/*")
public class CharsetFilter implements Filter {
    private String encoding;
    private static final Logger log = Logger.getLogger(CharsetFilter.class.getName());

    @Override
    public void init(FilterConfig filterConfig) {
        log.info("In CharsetFilter init");
        log.info("Setting encoding if null to utf-8");
        encoding = filterConfig.getInitParameter("requestEncoding");
        if (encoding == null) {
            encoding = "UTF-8";
        }
        log.info("Success");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("In CharsetFilter doFilter");
        log.info("Setting currentEncoding if null to utf-8");
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String currentEncoding = request.getCharacterEncoding();
        if (currentEncoding == null) {
            request.setCharacterEncoding(encoding);
        }
        log.info("CharsetFilter success");
        filterChain.doFilter(servletRequest,servletResponse);
    }
}
