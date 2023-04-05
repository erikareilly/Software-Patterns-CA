package com.example.softwarepatternsca;

public class User {

    public String na, su, em, ph;
    public boolean admin;


    public User(){

    }

    public User(String na, String su, String em, String ph,boolean admin){
        this.na=na;
        this.su=su;
        this.em=em;
        this.ph=ph;
        this.admin=admin;
    }
}
