package com.wills.carl.gamehub.model;

public class Cover {

    String url;
    int width;
    int height;
    String cloudinaryId;


    public Cover(String url, int width, int height){
        this.url = url;
        this.width = width;
        this.height = height;
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getCloudinaryId() {
        return cloudinaryId;
    }

    public void setCloudinaryId(String cloudinaryId) {
        this.cloudinaryId = cloudinaryId;
    }





}
