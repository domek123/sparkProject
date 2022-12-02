package com.company;

import com.fasterxml.uuid.Generators;

import java.sql.Array;
import java.util.*;
class Airbag{
    String name;
    boolean value;

    public Airbag(String name, boolean zazn) {
        this.name = name;
        this.value = zazn;
    }

    public String getName() {
        return name;
    }

    public boolean isValue() {
        return value;
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
    private UUID id;
    private String model;
    private String rok;
    private ArrayList<Airbag> airbags;
    private String color;
    private String data;
    private int cena;
    private int vat;
    private ArrayList<String> images = new ArrayList();
    private boolean invoice;


    public ArrayList<String> getImages() {
        return images;
    }
    public String getData() {
        return data;
    }

    public int getCena() {
        return cena;
    }

    public int getVat() {
        return vat;
    }


    public void setInvoice(boolean invoice) {
        this.invoice = invoice;
    }

    public Car(String model, String rok, ArrayList<Airbag> poduszki, String kolor) {
        id = Generators.randomBasedGenerator().generate();
        this.model = model;
        this.rok = rok;
        this.airbags = poduszki;
        this.color = kolor;

        Random rand = new Random();
        int[] day = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30};
        int[] month = {1,2,3,4,5,6,7,8,9,10,11,12};
        int[] year = {2000,2001,2002,2003,2004,2005};
        data = "" + day[rand.nextInt(30)] + "/" + month[rand.nextInt(12)] + "/" + year[rand.nextInt(6)];
        cena = rand.nextInt(20000)+10000;
        int[] vats = {0,7,22};
        vat = vats[rand.nextInt(3)];

        invoice = false;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    public String getModel() {
        return model;
    }

    public String getRok() {
        return rok;
    }

    public ArrayList<Airbag> getAirbags() {
        return airbags;
    }

    public String getColor() {
        return color;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setRok(String rok) {
        this.rok = rok;
    }

    public void AddToImageArray(ArrayList<String> list){
        for(String url:list){
            if(!images.contains(url)){
                images.add(url);
            }
        }


    }
    @Override
    public String toString() {
        return "Car{" +
            "id='" + id +'\'' +
            "model='" + model + '\'' +
            ", rok=" + rok +
            ", poduszki=" + Arrays.deepToString(airbags.toArray()) +
            ", kolor='" + color + '\'' +
            '}';
    }
}