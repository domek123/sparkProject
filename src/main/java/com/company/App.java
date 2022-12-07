package com.company;
import static spark.Spark.*;

import com.google.gson.*;
import java.util.*;

import com.fasterxml.uuid.Generators;
import spark.Request;
import spark.Response;
import static spark.Spark.externalStaticFileLocation;
import static spark.Spark.staticFiles;

class Id{
    String id;

    public String getId() {
        return id;
    }
}
class ModifyCar{
    String id;
    String model;
    String rok;

    public String getId() {
        return id;
    }

    public String getModel() {
        return model;
    }

    public String getRok() {
        return rok;
    }
}

public class App {
    private static Gson gson = new Gson();
    private static ArrayList<Car> cars = new ArrayList<>();
    public static void main(String[] args) {
        externalStaticFileLocation("C:\\appfolder\\src\\main\\resources\\public");
        staticFiles.location("/public");
        post("/add", (req, res) -> AddFunction(req,res));
        post("/json", (req, res) -> GetCarsFunction(req,res));
        post("/delete", (req, res) -> deleteIdFunction(req,res));
        post("/update", (req, res) -> UpdateFunction(req,res));
    }
    private static String AddFunction(Request req,Response res){
        UUID uuid = Generators.randomBasedGenerator().generate();
        Car car = gson.fromJson(req.body(), Car.class);
        car.setId(uuid);
        cars.add(car);
        res.type("application/json");
        return gson.toJson(car.toString());
    }
    private static String GetCarsFunction(Request req,Response res){
        return gson.toJson(cars);
    }
    private static String UpdateFunction(Request req, Response response){
        ModifyCar modifiedCar = gson.fromJson(req.body(),ModifyCar.class);
        ArrayList<Car> c = (ArrayList<Car>) cars.clone();
        for(Car car : c){
            if(car.getId().toString().equals(modifiedCar.getId())){
                car.setModel(modifiedCar.getModel());
                car.setRok(modifiedCar.getRok());
            }
        }
        response.type("application/json");
        return gson.toJson(cars);
    }
    private static String deleteIdFunction(Request req, Response response){
        Id id = gson.fromJson(req.body(),Id.class);
        ArrayList<Car> c = (ArrayList<Car>) cars.clone();
        for(Car car : c){
            if(car.getId().toString().equals(id.getId())){
                cars.remove(car);
            }
        }
        response.type("application/json");
        return gson.toJson(cars);
    }
}
