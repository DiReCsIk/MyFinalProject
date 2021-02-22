package com.MyServlet.TagHandler;

import com.MyServlet.DBManager.Service.Impl.AdministratorServiceImpl;
import org.apache.log4j.Logger;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

public class PaginationTagHandler extends SimpleTagSupport {
    private static final Logger log = Logger.getLogger(AdministratorServiceImpl.class.getName());
    private int pageNumber;
    private String command;

    @Override
    public void doTag() {
        log.info("In PaginationTagHandler");
        JspWriter jspWriter = getJspContext().getOut();
        try {
            if (pageNumber != 1) {
                System.out.println("<a href=\"Controller?command=" + command + "&pageNumber=" + (pageNumber - 1) + "\">Previous </a>");
                jspWriter.print("<a href=\"Controller?command=" + command + "&pageNumber=" + (pageNumber - 1) + "\">Previous </a>");
                log.info("pageNumber != 1. Printing previous");
            }
            if (pageNumber != maxPage) {
                jspWriter.print("<a href=\"Controller?command=" + command + "&pageNumber=" + (pageNumber + 1) + "\"> Next</a>");
                log.info("pageNumber != maxPage. Printing next");
            }
        } catch (IOException exception) {
            log.info(exception);
        }
        log.info("Pagination success");
    }

    private int maxPage;

    public int getMaxPage() {
        return maxPage;
    }

    public void setMaxPage(int maxPage) {
        this.maxPage = maxPage;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }
}
