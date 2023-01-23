package com.company.controller;

import com.company.model.Photo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

import java.io.File;


import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class PhotoServiceImp implements PhotoService{

    @Override
    public JsonElement getAll() {

        return null;
    }

    @Override
    public String getPhotoById(int id) {
        File f = new File("photos");
        ArrayList<Photo> cars = new ArrayList<>();
        int i = 0;
        for (File file : f.listFiles()) {
            Photo car = new Photo(i,file.getName(),file.getPath());
            cars.add(car);
        }
        return cars.get(id).toString();
    }

    @Override
    public JsonElement getPhotoByName(String name) {
        return null;
    }

    @Override
    public Boolean delPhoto(int id) {
        return null;
    }

    @Override
    public OutputStream getImage(int id) {
        return null;
    }

    @Override
    public Boolean renPhoto(int id, String name) {
        return null;
    }
}
