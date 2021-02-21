package com.MyServlet.DBManager.Service;

import com.MyServlet.Entity.User;
import com.MyServlet.Exception.ConnectionException;
import com.MyServlet.Exception.ServiceException;

import java.util.ArrayList;
import java.util.Map;

public interface UserService extends DefaultService<User> {
    Map<String, ArrayList<String>> getUsersData(String type, int pageNumber, int rowCount) throws ConnectionException, ServiceException;

    User selectUserByEmail(String email) throws ConnectionException, ServiceException;
}
