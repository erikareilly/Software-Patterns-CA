package com.example.softwarepatternsca;

public class Payment {

    String name;
    int cvv, expiry;
    long number;

    public Payment(){

    }

    public Payment(String name, long number, int cvv, int expiry){
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

    public long getNumber() {
        return number;
    }

    public void setNumber(long number) {
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
