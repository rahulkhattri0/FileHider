package com.filehider.models;

public class User {
    private String name;
    private String email;
    private String password;
    public User(String name,String email,String password){
        this.name = name;
        this.email = email;
        this.password = password;
    }
    //getters and setters
    public String getName(){
        return this.name;
    }
    public String getEmail(){
        return this.email;
    }
    public String getPassword(){
        return this.password;
    }
    public void setEmail(String email){
        this.email = email;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setPassword(String password){
        this.password = password;
    }
}
