package com.example.app_ban_hang.Model;

public class District {
    private int code;
    private String name;

    public int getCode() { return code; }
    public String getName() { return name; }

    @Override
    public String toString() {
        return name;
    }
}
