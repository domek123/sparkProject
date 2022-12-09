package com.company;
import static spark.Spark.*;

import com.google.gson.*;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.*;

import com.fasterxml.uuid.Generators;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
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
        post("/add", App::AddFunction);
        post("/json", App::GetCarsFunction);
        post("/delete", App::deleteIdFunction);
        post("/update", App::UpdateFunction);
        post("/generate", App::GenerateFunction);
        post("invoice", (req, res) -> InvoiceFunction(req,res));
    }
    private static String AddFunction(Request req,Response res){
        Car car = gson.fromJson(req.body(), Car.class);
        car.setId(Generators.randomBasedGenerator().generate());
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
    private static String GenerateFunction(Request req,Response res){
        String[] models = {"fiat","opel","bmw","audi"};
        String[] colors = {"red","blue","yellow","green","magenta","orange"};
        String[] years = {"2000","2001","2002","2003","2004","2005"};
        String[] airbags = {"kierowca","pasażer","boczne","tylne"};
        for(int i =0;i<20;i++){
            ArrayList<Airbag> data = new ArrayList<>();
            for(String name : airbags){
                data.add(new Airbag(name,false));
            }
            Random rand = new Random();
            int idx1 = rand.nextInt(4);
            int idx2 = rand.nextInt(6);
            int idx3 = rand.nextInt(6);
            Car car = new Car(models[idx1],years[idx2],data,colors[idx3]);
            cars.add(car);
        }
        return gson.toJson(cars);
    }
    private static String InvoiceFunction(Request req,Response res) throws DocumentException, FileNotFoundException {
        Id id = gson.fromJson(req.body(),Id.class);
        for(Car car : cars){
            if(car.getId().toString().equals(id.getId())){
                car.setInvoice(true);
            }
        }
        Document document = new Document(); // dokument pdf
        String path = "invoices/"+id.getId()+".pdf"; // lokalizacja zapisu
        PdfWriter.getInstance(document, new FileOutputStream(path));

        document.open();
        Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
        Chunk chunk = new Chunk("tekst", font); // akapit

        document.add(chunk);
        document.close();
        res.type("application/json");
        return gson.toJson(cars);
    }
}
