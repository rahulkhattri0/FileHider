package com.filehider.models;

public class Data {
    private String id;
    private String name;
    private String path;

    public Data(String id,String name,String path){
        this.id = id;
        this.name = name;
        this.path = path;
    }

    public Data(String name,String path){
        this.name = name;
        this.path = path;
    }

    public String getId(){
        return this.id;
    }

    public String getFileName(){
        return this.name;
    }
    public String getPath(){
        return this.path;
    }
}

