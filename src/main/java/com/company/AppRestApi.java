package com.company;

import com.company.controller.UserServiceImpl;
import com.company.model.User;

import static spark.Spark.*;
import spark.Request;
import spark.Response;
import com.google.gson.Gson;
public class AppRestApi {
    private static Gson gson = new Gson();
    private static UserServiceImpl userService = new UserServiceImpl();
    public static void main(String[] args) {
        port(7777);
        get("/api/users", AppRestApi::GetUsers);
        get("/api/users/:id", AppRestApi::GetUser);
        post("/api/users", AppRestApi::AddUser);
        put("/api/users", AppRestApi::editUser);
        options("/api/users/:id", AppRestApi::CheckUser);
        delete("/api/users/:id", AppRestApi::DeleteUser);
    }

    private static String DeleteUser(Request request, Response response) {
        userService.deleteUser(request.params(":id"));
        return "usunieto usera";
    }

    private static String CheckUser(Request request, Response response) {
        if(userService.userExist(request.params(":id"))){
            return "istnieje";
        }else{
            return "nie istnieje";
        }
    }

    private static String editUser(Request request, Response response) {
        User user = gson.fromJson(request.body(), User.class);
        return gson.toJson(userService.editUser(user).toString());
    }

    private static String AddUser(Request request, Response response) {
        User user = gson.fromJson(request.body(), User.class);
        userService.addUser(user);
        return "dodano usera";
    }

    private static String GetUser(Request request, Response response) {
        System.out.println(request.params(":id"));
        return gson.toJson(userService.getUser(request.params(":id")).toString());
    }

    private static String GetUsers(Request request, Response response) {
        return gson.toJson(userService.getUsers());
    }
}
