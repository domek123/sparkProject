package com.company;

import com.fasterxml.uuid.Generators;

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
    UUID id= null;

    public void setId(UUID id) {
        this.id = id;
    }

    String model;
    String rok;
    ArrayList<Airbag> data;
    String color;
    boolean invoice;

    public boolean getInvoice() {
        return invoice;
    }

    public void setInvoice(boolean invoice) {
        this.invoice = invoice;
    }

    public Car(String model, String rok, ArrayList<Airbag> poduszki, String kolor) {
        this.model = model;
        this.rok = rok;
        this.data = poduszki;
        this.color = kolor;
        id = Generators.randomBasedGenerator().generate();
        invoice = true;
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

    public ArrayList<Airbag> getData() {
        return data;
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