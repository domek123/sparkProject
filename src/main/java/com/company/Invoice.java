package com.company;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Invoice {
    private String time;
    private String title;
    private String seller;
    private String buyer;
    ArrayList<Car> list;

    public String getSeller() {
        return seller;
    }

    public String getBuyer() {
        return buyer;
    }

    public ArrayList<Car> getList() {
        return list;
    }

    public String getTime() {
        return time;
    }

    public String getTitle() {
        return title;
    }

    public Invoice(String seller, String buyer, ArrayList<Car> list) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd_MM_yyyy_HH_mm_ss");
        Date date = new Date();
        this.time = formatter.format(date);
        this.title = CreateTitle(this.time);
        this.seller = seller;
        this.buyer = buyer;
        this.list = list;
    }

    private String CreateTitle(String date){
        return "VAT_"+date;
    }
    public int SumPrice(){
        return list.stream().mapToInt(car -> car.getCena()).sum();
    }
    public double CalcVat(Car car){
        return (car.getCena() + car.getCena() * car.getVat() * 0.01);
    }

}
