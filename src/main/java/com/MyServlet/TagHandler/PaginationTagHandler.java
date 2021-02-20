package com.MyServlet.TagHandler;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

public class PaginationTagHandler extends SimpleTagSupport {
    private int pageNumber;
    private String command;

    @Override
    public void doTag() {
        JspWriter jspWriter = getJspContext().getOut();
        try {
            if (pageNumber != 1) {
                System.out.println("<a href=\"Controller?command=" + command + "&pageNumber=" + (pageNumber - 1) + "\">Previous </a>");
                jspWriter.print("<a href=\"Controller?command=" + command + "&pageNumber=" + (pageNumber - 1) + "\">Previous </a>");
            }
            if (pageNumber != maxPage) {
                jspWriter.print("<a href=\"Controller?command=" + command + "&pageNumber=" + (pageNumber + 1) + "\"> Next</a>");
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
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
