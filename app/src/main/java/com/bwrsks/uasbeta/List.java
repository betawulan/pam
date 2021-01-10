package com.bwrsks.uasbeta;


public class List {
    private String mName;
    private String mImage;

    public List(String name, String image){
        mName = name;
        mImage = image;
    }

    public String getName(){
        return mName;
    }

    public String getImage(){
        return mImage;
    }
}
