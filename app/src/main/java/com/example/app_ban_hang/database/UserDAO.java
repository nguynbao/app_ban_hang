package com.example.app_ban_hang.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.app_ban_hang.Model.users;

import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private SQLiteDatabase db;

    public UserDAO(Context context) {
        database dbHelper = new database(context);
        db = dbHelper.getWritableDatabase();
    }

    // Insert user
    public long insert(users user) {
        ContentValues values = new ContentValues();
        values.put("full_name", user.getUser_name());
        values.put("email", user.getUser_email());
        values.put("password", user.getUser_password());
        values.put("phone", String.valueOf(user.getPhone())); // convert int -> String
        values.put("address_line", user.getAddress());
        return db.insert("users", null, values);
    }

    // Delete user by ID
    public int delete(String userId) {
        return db.delete("users", "user_id=?", new String[]{userId});
    }

    // Update user
    public int update(users user) {
        ContentValues values = new ContentValues();
        values.put("full_name", user.getUser_name());
        values.put("email", user.getUser_email());
        values.put("password", user.getUser_password());
        values.put("phone", String.valueOf(user.getPhone()));
        values.put("address_line", user.getAddress());
        return db.update("users", values, "user_id=?", new String[]{String.valueOf(user.getUser_id())});
    }

    // Get all users
    public List<users> getAll() {
        String sql = "SELECT * FROM users";
        return getData(sql);
    }
    public users getUserById(int id) {
        String sql = "SELECT * FROM users WHERE user_id = ?";
        Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(id)});
        if (cursor != null && cursor.moveToFirst()) {
            users user = new users(
                    cursor.getInt(cursor.getColumnIndexOrThrow("user_id")),
                    cursor.getString(cursor.getColumnIndexOrThrow("full_name")),
                    cursor.getString(cursor.getColumnIndexOrThrow("email")),
                    cursor.getString(cursor.getColumnIndexOrThrow("password")),
                    0, // phone mình chỉnh lại dưới
                    cursor.getString(cursor.getColumnIndexOrThrow("address_line"))
            );

            // Bổ sung cột phone nếu trong database phone đang kiểu TEXT
            try {
                String phoneStr = cursor.getString(cursor.getColumnIndexOrThrow("phone"));
                int phone = phoneStr != null ? Integer.parseInt(phoneStr) : 0;
                user.setPhone(phone);
            } catch (Exception e) {
                user.setPhone(0);
            }

            cursor.close();
            return user;
        }

        if (cursor != null) {
            cursor.close();
        }
        return null;
    }

    // Get user by ID
    public users getById(String userId) {
        String sql = "SELECT * FROM users WHERE user_id=?";
        List<users> list = getData(sql, userId);
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }
    // Sửa lại dangKi (đồng nhất với insert)
    public boolean dangKy(String name, String email, String password, String phone, String city) {
        if (checkEmailExists(email)) {
            return false;
        }
        ContentValues values = new ContentValues();
        values.put("full_name", name);
        values.put("email", email);
        values.put("password", password);
        values.put("phone", phone);
        values.put("address_line", city);
        long result = db.insert("users", null, values);
        return result != -1;
    }
    public boolean dangNhap(String email, String password) {

        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE email = ? AND password = ?", new String[]{email, password});
        boolean result = cursor.moveToFirst(); // true nếu tìm thấy
        cursor.close();
        return result;
    }


    public boolean checkEmailExists(String email){
        String sql = "SELECT * FROM users WHERE email = ?";
        Cursor cursor = db.rawQuery(sql, new String[]{email});
        boolean exists = cursor.moveToFirst();
        cursor.close();
        return exists;
    }
    public int getUserIdByEmail(String email) {
        int userId = -1;

        Cursor cursor = db.rawQuery("SELECT user_id FROM users WHERE email = ?", new String[]{email});
        if (cursor.moveToFirst()) {
            userId = cursor.getInt(cursor.getColumnIndexOrThrow("user_id"));
        }
        cursor.close();
        return userId;
    }


    // Check login (optional)
    public users checkLogin(String email, String password) {
        String sql = "SELECT * FROM users WHERE email=? AND password=?";
        List<users> list = getData(sql, email, password);
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    // General query method
    private List<users> getData(String sql, String... selectionArgs) {
        List<users> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, selectionArgs);
        while (cursor.moveToNext()) {
            users user = new users(
                    cursor.getInt(cursor.getColumnIndexOrThrow("user_id")),
                    cursor.getString(cursor.getColumnIndexOrThrow("full_name")),
                    cursor.getString(cursor.getColumnIndexOrThrow("email")),
                    cursor.getString(cursor.getColumnIndexOrThrow("password")),
                    parsePhone(cursor.getString(cursor.getColumnIndexOrThrow("phone"))),
                    cursor.getString(cursor.getColumnIndexOrThrow("address_line"))
            );
            list.add(user);
        }
        cursor.close();
        return list;
    }

    // Helper: convert phone String -> int safely
    private int parsePhone(String phone) {
        try {
            return Integer.parseInt(phone);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
