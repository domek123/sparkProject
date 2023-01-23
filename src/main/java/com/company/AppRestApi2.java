package com.company;

import com.company.controller.PhotoServiceImp;
import com.company.response.ResponseEntity;
import com.company.response.ResponseStatus;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import spark.Request;
import spark.Response;

import static spark.Spark.*;

public class AppRestApi2 {
    private static final PhotoServiceImp photoService = new PhotoServiceImp();
    public static void main(String[] args) {
        port(7777);
//        get("/api/cars",App::);
        get("/api/cars/:id",AppRestApi2::getPhotoByID);
//        get("/api/cars/:name",App::);
//        get("/api/users/:id",App::);
//        put("/api/users/:id",App::);
//        delete("/api/users/:id",App::);
    }

    static String getPhotoByID(Request req, Response res){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        res.header("Access-Control-Allow-Origin", "*");
        res.type("application/json");
        String photoJson = photoService.getPhotoById(Integer.parseInt(req.params("id")));
        if(photoJson != null) {
            return gson.toJson(new ResponseEntity(
                ResponseStatus.SUCCESS,
                "photo found",
                gson.toJsonTree(photoJson)
            ));
        }else{
            return gson.toJson(new ResponseEntity(
                ResponseStatus.ERROR,
                "photo not found with id = " + req.params("id")
            ));
        }
    }
}
