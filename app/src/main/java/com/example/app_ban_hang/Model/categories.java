package com.example.app_ban_hang.Model;

public class categories {
    private int category_id;
    private String category_name;
    private int category_imgRes;

    public categories(String category_name, int category_id, int category_imgRes) {
        this.category_name = category_name;
        this.category_id = category_id;
        this.category_imgRes = category_imgRes;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public int getCategory_imgRes() {
        return category_imgRes;
    }

    public void setCategory_imgRes(int category_imgRes) {
        this.category_imgRes = category_imgRes;
    }
}
