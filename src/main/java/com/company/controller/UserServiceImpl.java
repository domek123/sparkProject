package com.company.controller;

import java.util.HashMap;
import com.company.model.User;

public class UserServiceImpl implements UserService{
    HashMap<String, User> users = new HashMap<>();
    @Override
    public void addUser(User user) {
        users.put(user.getId(),user);
    }
    @Override
    public HashMap<String, User> getUsers() {
        return users;
    }
    @Override
    public User getUser(String id) {
        return users.get(id);
    }

    @Override
    public User editUser(User user) {
        users.put(user.getId(),user);
        return users.get(user.getId());
    }

    @Override
    public void deleteUser(String id) {
        users.remove(id);
    }

    @Override
    public Boolean userExist(String id) {
        if(users.get(id)!=null){
            return true;
        }
        return false;
    }
}
