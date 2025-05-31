package com.example.app_ban_hang.Model;

public class users {
    private static int user_id;
    private String user_name;
    private String user_email;
    private String user_password;
    private int phone;
    private String address;

    public users(int user_id, String user_name, String user_email, String user_password, int phone, String address) {
        users.user_id = user_id;
        this.user_name = user_name;
        this.user_email = user_email;
        this.user_password = user_password;
        this.phone = phone;
        this.address = address;
    }

    public static int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        users.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getUser_password() {
        return user_password;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
