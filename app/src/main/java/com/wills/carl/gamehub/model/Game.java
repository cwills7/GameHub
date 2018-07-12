package com.wills.carl.gamehub.model;

import java.util.ArrayList;

public class Game {

    int id;
    String name;
    String summary;
    ArrayList<Platforms> platforms;
    Cover images;

    public Game(int id, String name, String summary, ArrayList<Platforms> platforms, Cover images){
        this.id = id;
        this.name = name;
        this.summary = summary;
        this.platforms = platforms;
        this.images = images;
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

    public ArrayList<Platforms> getPlatforms() {
        return platforms;
    }

    public void setPlatforms(ArrayList<Platforms> platforms) {
        this.platforms = platforms;
    }

    public Cover getImages() {
        return images;
    }

    public void setImages(Cover images) {
        this.images = images;
    }


}
