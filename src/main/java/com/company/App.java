package com.company;
import static spark.Spark.*;

import java.util.*;

import com.fasterxml.uuid.Generators;
import com.google.gson.Gson;
import spark.Request;
import spark.Response;
import static spark.Spark.externalStaticFileLocation;
import static spark.Spark.staticFiles;
class Airbag{
    String name;
    boolean value;

    public Airbag(String name, boolean zazn) {
        this.name = name;
        this.value = zazn;
    }

    @Override
    public String toString() {
        return "Airbag{" +
                "name='" + name + '\'' +
                ", value=" + value +
                '}';
    }
}
class Car{
    UUID id=null;

    public void setId(UUID id) {
        this.id = id;
    }

    String model;
    int rok;
    ArrayList<Airbag> data;
    String color;

    public Car(String model, int rok, ArrayList<Airbag> poduszki, String kolor) {
        this.model = model;
        this.rok = rok;
        this.data = poduszki;
        this.color = kolor;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setRok(int rok) {
        this.rok = rok;
    }

    public void setPoduszki(ArrayList<Airbag> poduszki) {
        this.data = poduszki;
    }

    public void setKolor(String kolor) {
        this.color = kolor;
    }

    @Override
    public String toString() {
        return "Car{" +
                "id='" + id +'\'' +
                "model='" + model + '\'' +
                ", rok=" + rok +
                ", poduszki=" + Arrays.deepToString(data.toArray()) +
                ", kolor='" + color + '\'' +
                '}';
    }
}
public class App {
    private static Gson gson = new Gson();
    private static ArrayList<Car> cars = new ArrayList<>();
    public static void main(String[] args) {
        externalStaticFileLocation("C:\\appfolder\\src\\main\\resources\\public");
        staticFiles.location("/public");
        post("/add", (req, res) -> AddFunction(req,res));
//        post("/deleteAll", (req, res) -> deleteAllFunction(req,res));
//        post("/deleteId/:id", (req, res) -> deleteIdFunction(req,res));
//        post("/deleteSelected", (req, res) -> deleteSelectedFunction(req,res));
    }
    private static String AddFunction(Request req,Response res){
        UUID uuid = Generators.randomBasedGenerator().generate();
        Car car = gson.fromJson(req.body(), Car.class);
        car.setId(uuid);
        cars.add(car);
        res.type("application/json");
        return gson.toJson(car.toString());
    }
}
