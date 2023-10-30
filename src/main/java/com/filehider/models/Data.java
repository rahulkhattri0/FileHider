package com.filehider.models;

public class Data {
    private int id;
    private String name;
    private String path;

    public Data(int id,String name,String path){
        this.id = id;
        this.name = name;
        this.path = path;
    }

    public Data(String name,String path){
        this.name = name;
        this.path = path;
    }

    public int getId(){
        return this.id;
    }

    public String getFileName(){
        return this.name;
    }
    public String getPath(){
        return this.path;
    }
}

