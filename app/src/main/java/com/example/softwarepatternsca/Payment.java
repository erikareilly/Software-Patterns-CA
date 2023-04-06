package com.example.softwarepatternsca;

public class Payment {

    String name;
    int number, cvv, expiry;

    public Payment(){

    }

    public Payment(String name, int number, int cvv, int expiry){
        this.name=name;
        this.number=number;
        this.cvv=cvv;
        this.expiry=expiry;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getCvv() {
        return cvv;
    }

    public void setCvv(int cvv) {
        this.cvv = cvv;
    }

    public int getExpiry() {
        return expiry;
    }

    public void setExpiry(int expiry) {
        this.expiry = expiry;
    }
}
