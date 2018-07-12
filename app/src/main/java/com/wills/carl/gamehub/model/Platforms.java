package com.wills.carl.gamehub.model;

public class Platforms {

    int id;
    String name;
    String summary;

    public Platforms(int id, String name, String summary){
        this.id = id;
        this.name = name;
        this.summary = summary;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }





}
