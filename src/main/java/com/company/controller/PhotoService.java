package com.company.controller;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import java.io.OutputStream;

public interface PhotoService {
    JsonElement getAll();
    String getPhotoById(int id);
    JsonElement getPhotoByName(String name);
    Boolean delPhoto(int id);
    OutputStream getImage(int id);
    Boolean renPhoto(int id, String name);
}
