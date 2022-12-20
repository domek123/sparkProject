package com.company;
import static spark.Spark.*;

import com.google.gson.*;

import java.io.*;
import java.nio.file.*;
import java.util.*;

import com.fasterxml.uuid.Generators;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import java.util.List;
import java.util.stream.Collectors;
import spark.Request;
import spark.Response;

import static spark.Spark.externalStaticFileLocation;
import static spark.Spark.staticFiles;

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
        post("/invoice", App::InvoiceFunction);
        get("/invoices", App::InvoicesFunction);
        post("/allCarInvoice", App::AllCarInvoice);
        post("/yearCarInvoice", App::YearCarInvoice);
        post("/priceCarInvoice", App::PriceCarInvoice);
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
    private static String InvoiceFunction(Request req,Response res) throws DocumentException, IOException {
        Id id = gson.fromJson(req.body(),Id.class);
        Car carToPDF = null;
        for(Car car : cars){
            if(car.getId().toString().equals(id.getId())){
                car.setInvoice(true);
                carToPDF = car;
            }
        }
        HashMap<String, BaseColor> map = new HashMap() {{
            put("red", BaseColor.RED);
            put("blue", BaseColor.BLUE);
            put("yellow", BaseColor.YELLOW);
            put("green", BaseColor.GREEN);
            put("magenta", BaseColor.MAGENTA);
            put("orange", BaseColor.ORANGE);
        }};

        Document document = new Document(); // dokument pdf
        String path = "invoices/"+id.getId()+".pdf"; // lokalizacja zapisu
        PdfWriter.getInstance(document, new FileOutputStream(path));

        document.open();
        Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
        Chunk chunk = new Chunk("FAKTURA dla: " + id.getId(), font);
        Paragraph p = new Paragraph("", font);
        document.add(p);
        document.add(chunk);
        Chunk chunk2 = new Chunk("model: " + carToPDF.getModel(), font);
        document.add(chunk2);
        p = new Paragraph("", font);
        document.add(p);
        font = FontFactory.getFont(FontFactory.COURIER, 16, map.get(carToPDF.getColor()));
        Chunk chunk3 = new Chunk("kolor: " + carToPDF.getColor(), font);
        document.add(chunk3);
        p = new Paragraph("", font);
        document.add(p);
        font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
        Chunk chunk4 = new Chunk("rok: " + carToPDF.getRok(), font);
        document.add(chunk4);        p = new Paragraph("", font);
        document.add(p);
        for(Airbag airbag : carToPDF.getAirbags()){
            Chunk chunk5 = new Chunk("poduszka: " + airbag.getName() + " - " + airbag.isValue(), font);
            document.add(chunk5);
            p = new Paragraph("", font);
            document.add(p);
        }
        Image img = Image.getInstance("photos/samochod.jpg");
        document.add(img);

        document.close();
        res.type("application/json");
        return gson.toJson(cars);
    }
    public static String InvoicesFunction(Request req,Response res) throws IOException {
        String id = req.queryParams("id");
        System.out.println(id);
        res.type("application/octet-stream");
        res.header("Content-Disposition", "attachment; filename=plik.pdf");

        OutputStream outputStream = res.raw().getOutputStream();
        Path p1 = Paths.get("invoices/"+ id + ".pdf");
        outputStream.write(Files.readAllBytes(p1));
        return "";
    }
    public static String AllCarInvoice(Request req,Response res) throws DocumentException, FileNotFoundException {
        res.type("application/json");
        return gson.toJson(Invoices.GenerateAllCarsInvoice(cars));
    }
    public static String YearCarInvoice(Request req,Response res) throws DocumentException, FileNotFoundException {
        Year year = gson.fromJson(req.body(),Year.class);
        List<Car> list = cars.stream().filter(car-> car.getRok().equals(year.getYear())).collect(Collectors.toList());
        ArrayList<Car> l = new ArrayList<>(list);
        res.type("application/json");
        return gson.toJson(Invoices.GenerateYearCarsInvoice(l,year.getYear()));
    }
    public static String PriceCarInvoice(Request req,Response res) throws DocumentException, FileNotFoundException {
        Prices prices = gson.fromJson(req.body(),Prices.class);
        List<Car> list = cars.stream().filter(car-> car.getCena() > Integer.parseInt(prices.getMin()) && car.getCena() < Integer.parseInt(prices.getMax())).collect(Collectors.toList());
        ArrayList<Car> l = new ArrayList<>(list);
        res.type("application/json");
        return gson.toJson(Invoices.GeneratePriceCarsInvoice(l,prices.getMin(),prices.getMax()));
    }
}
