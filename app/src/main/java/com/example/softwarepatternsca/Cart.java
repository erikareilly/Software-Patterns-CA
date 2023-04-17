package com.example.softwarepatternsca;

public class Cart {

    private String pid, pName, pPrice, discount;

    public Cart(){

    }

    public Cart(String pid, String pName, String pPrice, String discount) {
        this.pid = pid;
        this.pName = pName;
        this.pPrice = pPrice;
        this.discount = discount;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getpPrice() {
        return pPrice;
    }

    public void setpPrice(String pPrice) {
        this.pPrice = pPrice;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }
}
