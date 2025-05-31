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

    // Get user by ID
    public users getById(String userId) {
        String sql = "SELECT * FROM users WHERE user_id=?";
        List<users> list = getData(sql, userId);
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
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
