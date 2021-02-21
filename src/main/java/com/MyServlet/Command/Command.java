package com.MyServlet.Command;

import com.MyServlet.Exception.CommandException;
import com.MyServlet.Exception.ConnectionException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Command {
    String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException, ConnectionException;
}
