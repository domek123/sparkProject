package com.company;
import static spark.Spark.*;

import com.google.gson.*;

import java.io.*;
import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.util.*;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import java.util.List;
import java.util.stream.Collectors;
import spark.Request;
import spark.Response;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletException;
import javax.servlet.http.Part;

import static spark.Spark.externalStaticFileLocation;
import static spark.Spark.staticFiles;

public class App {
    private static Gson gson = new Gson();
    private static ArrayList<Car> cars = new ArrayList<>();
    private static String uuid;
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
        get("/setUUID",App::SetUUID);
        get("/thumb",App::ReturnThumb);
        post("/upload",App::Upload);
        post("/savePhotos",App::SavePhotos);
        post("/getPhotos",App::GetPhotos);
        post("/getSize",App::GetSize);
        post("/rotate",App::Rotate);
        post("/flip",App::Flip);
        post("/crop",App::Crop);
        get("/image",App::ReturnThumb);
    }

    private static String GetSize(Request request, Response response) throws IOException {
        Id path = gson.fromJson(request.body(),Id.class);
        return Imaging.getSize(path.getId());
    }

    private static String Crop(Request request, Response response) throws IOException {
        Crop crop = gson.fromJson(request.body(),Crop.class);
        return Imaging.Crop(crop.getId(),crop.getX(),crop.getY(),crop.getW(),crop.getH());
    }

    private static String Flip(Request request, Response response) throws IOException {
        Flip flip = gson.fromJson(request.body(),Flip.class);
        return Imaging.Flip(flip.getType(),flip.getId());
    }

    private static String Rotate(Request request, Response response) throws IOException {
        Id path = gson.fromJson(request.body(),Id.class);
        return Imaging.Rotate(path.getId());
    }

    private static String AddFunction(Request req,Response res){
        Car car = gson.fromJson(req.body(), Car.class);
        Car c = new Car(car.getModel(),car.getRok(),car.getAirbags(),car.getColor());
        cars.add(c);
        res.type("application/json");
        return gson.toJson(c.toString());
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
    public static String SetUUID(Request req,Response res){
        String id = req.queryParams("id");
        uuid = id;
        System.out.println(req.queryParams("type"));
        if(req.queryParams("type").equals("upload")){
            res.redirect("/upload/upload.html");
        }else if(req.queryParams("type").equals("gallery")){
            res.redirect("/gallery/gallery.html");
        }

        return "";
    }
    public static String Upload(Request req,Response res) throws ServletException, IOException {
        req.attribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement("/photos"));
        SimpleDateFormat formatter = new SimpleDateFormat("dd_MM_yyyy_HH_mm_ss");
        int index = 0;
        ArrayList<String> arrayList = new ArrayList<>();
        for(Part p : req.raw().getParts()){
            InputStream inputStream = p.getInputStream();
            // inputstream to byte
            byte[] bytes = inputStream.readAllBytes();
            Date date = new Date();
            String fileName = "img_" + formatter.format(date) + "_" + index + ".jpg";
            FileOutputStream fos = new FileOutputStream("photos/" + fileName);
            fos.write(bytes);
            fos.close();
            arrayList.add(fileName);
            index+=1;
        }
        System.out.println("XXX:" + arrayList);
        return gson.toJson(arrayList);
    }
    public static OutputStream ReturnThumb(Request req,Response res) throws IOException {
        System.out.println("obrazek" + req.queryParams("id"));
        res.type("image/jpeg");
        OutputStream outputStream = null;
        outputStream = res.raw().getOutputStream();
        outputStream.write(Files.readAllBytes(Path.of("photos/"+req.queryParams("id"))));
        outputStream.flush();
        return outputStream;
    }
    private static String SavePhotos(Request req, Response res) {
        Photos photos = gson.fromJson(req.body(),Photos.class);
        System.out.println(photos.getData());
        Car c = null;
        for(Car car :cars){
            if(car.getId().toString().equals(uuid)){
                car.AddToImageArray(photos.getData());
                c=car;
            }
        }
        System.out.println(c.getImages());
        return "zapisano";
    }
    private static String GetPhotos(Request req, Response res) {
        ArrayList<String> photoList = new ArrayList<>();
        for(Car car :cars){
            if(car.getId().toString().equals(uuid)){
                photoList = car.getImages();
            }
        }
        return gson.toJson(photoList);
    }


}
