package com.company;

import java.util.ArrayList;

class Id{
    private String id;

    public String getId() {
        return id;
    }
}
class Year{
    private String year;

    public String getYear() {
        return year;
    }
}
class Prices{
    private String min;
    private String max;

    public String getMin() {
        return min;
    }

    public String getMax() {
        return max;
    }
}
class ModifyCar{
    private String id;
    private String model;
    private String rok;

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
class Photos{
    private ArrayList<String> data;

    public ArrayList<String> getData() {
        return data;
    }
}