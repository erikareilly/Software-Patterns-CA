package com.example.softwarepatternsca;

public class Orders {

    private String name,state, Country,County,Eircode,HouseNo,OrderDate,OrderTime,Street,TotalAmount;

    public Orders(){

    }

    public Orders(String name, String state, String country, String county, String eircode, String houseNo, String orderDate, String orderTime, String street, String totalAmount) {
        this.name = name;
        this.state = state;
        Country = country;
        County = county;
        Eircode = eircode;
        HouseNo = houseNo;
        OrderDate = orderDate;
        OrderTime = orderTime;
        Street = street;
        TotalAmount = totalAmount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getCounty() {
        return County;
    }

    public void setCounty(String county) {
        County = county;
    }

    public String getEircode() {
        return Eircode;
    }

    public void setEircode(String eircode) {
        Eircode = eircode;
    }

    public String getHouseNo() {
        return HouseNo;
    }

    public void setHouseNo(String houseNo) {
        HouseNo = houseNo;
    }

    public String getOrderDate() {
        return OrderDate;
    }

    public void setOrderDate(String orderDate) {
        OrderDate = orderDate;
    }

    public String getOrderTime() {
        return OrderTime;
    }

    public void setOrderTime(String orderTime) {
        OrderTime = orderTime;
    }

    public String getStreet() {
        return Street;
    }

    public void setStreet(String street) {
        Street = street;
    }

    public String getTotalAmount() {
        return TotalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        TotalAmount = totalAmount;
    }
}
