package com.company;

import java.util.ArrayList;

class Crop{
    private String id;
    private String x;
    private String y;
    private String w;
    private String h;

    public String getId() {
        return id;
    }

    public String getX() {
        return x;
    }

    public String getY() {
        return y;
    }

    public String getW() {
        return w;
    }

    public String getH() {
        return h;
    }
}

class Flip{
    private String id;
    private String type;

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }
}

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