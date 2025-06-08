package com.example.app_ban_hang.Model;

public class Province {
    private int code;
    private String name;

    public Province() {
        // Default constructor cần cho Gson
    }

    public Province(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name; // hiển thị trong Spinner
    }
}
