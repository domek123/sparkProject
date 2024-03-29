package com.company.model;

public class Photo {
    private String id;
    private String name;
    private String path;

    public Photo(int id, String name, String path) {
        this.id = String.valueOf(id);
        this.name = name;
        this.path = path;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }
}
