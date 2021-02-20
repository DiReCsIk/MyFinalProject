package com.MyServlet.DBManager.Service;

import com.MyServlet.Entity.User;

import java.util.ArrayList;
import java.util.Map;

public interface UserService extends DefaultService<User> {
    Map<String, ArrayList<String>> getUsersData(String type, int pageNumber, int rowCount) throws Exception;

    User selectUserByEmail(String email) throws Exception;
}
