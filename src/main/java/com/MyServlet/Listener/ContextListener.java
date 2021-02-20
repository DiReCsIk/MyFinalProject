package com.MyServlet.Listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.File;
import java.net.URL;
import java.nio.file.Paths;

@WebListener
public class ContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        URL mySource = ContextListener.class.getProtectionDomain().getCodeSource().getLocation();
        ServletContext context = servletContextEvent.getServletContext();
        String rootPath = context.getRealPath("");
        System.setProperty("app.root", rootPath);

    }
}
