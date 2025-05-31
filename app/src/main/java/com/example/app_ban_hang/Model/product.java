package com.example.app_ban_hang.Model;

import java.math.BigInteger;

public class product {
    private int product_id;
    private int category_id;
    private String product_name;
    private String product_description;
    private float product_price;
    private int product_imgRes;

    public product(){}

    public product(int product_id,int category_id, String product_name, String product_description, int product_price, int product_imgRes) {
        this.product_id = product_id;
        this.category_id = category_id;
        this.product_name = product_name;
        this.product_description = product_description;
        this.product_price = product_price;
        this.product_imgRes = product_imgRes;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_description() {
        return product_description;
    }

    public void setProduct_description(String product_description) {
        this.product_description = product_description;
    }

    public float getProduct_price() {
        return product_price;
    }

    public void setProduct_price(int product_price) {
        this.product_price = product_price;
    }

    public int getProduct_imgRes() {
        return product_imgRes;
    }

    public void setProduct_imgRes(int product_imgRes) {
        this.product_imgRes = product_imgRes;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public void setProduct_price(float product_price) {
        this.product_price = product_price;
    }
}
