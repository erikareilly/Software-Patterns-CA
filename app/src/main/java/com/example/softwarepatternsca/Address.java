package com.example.softwarepatternsca;

public class Address {

    public String house,street,county,country,eircode;


    public Address(){

    }

    public Address(String house, String street, String county, String country, String eircode){
        this.house=house;
        this.street=street;
        this.county=county;
        this.country=country;
        this.eircode=eircode;
    }

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getEircode() {
        return eircode;
    }

    public void setEircode(String eircode) {
        this.eircode = eircode;
    }
}
