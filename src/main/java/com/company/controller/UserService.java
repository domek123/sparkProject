package com.company.controller;

import java.util.HashMap;
import com.company.model.User;

public interface UserService {
    void addUser(User user);
    HashMap<String,User> getUsers();
    User getUser(String id);
    User editUser(User user);
    void deleteUser(String id);
    Boolean userExist(String id);
}
