package com.example.app_ban_hang.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class database extends SQLiteOpenHelper {
    private static final String DB_NAME = "Database.db";
    private static final int DB_VERSION = 4;
    public database(Context context){
        super(context, DB_NAME, null,  DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Create_table.CREATE_TABLE_USERS);
        db.execSQL(Create_table.CREATE_TABLE_CATEGORIES);
        db.execSQL(Create_table.CREATE_TABLE_PRODUCTS);
        db.execSQL(Create_table.CREATE_TABLE_CART_ITEMS);
        db.execSQL(Create_table.CREATE_TABLE_WISHLISTS);
        db.execSQL(Create_table.CREATE_TABLE_ORDERS);
        db.execSQL(Create_table.CREATE_TABLE_ORDER_ITEMS);

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS users");
        db.execSQL("DROP TABLE IF EXISTS categories");
        db.execSQL("DROP TABLE IF EXISTS products");
        db.execSQL("DROP TABLE IF EXISTS cart_items");
        db.execSQL("DROP TABLE IF EXISTS wishlists");
        db.execSQL("DROP TABLE IF EXISTS orders");
        db.execSQL("DROP TABLE IF EXISTS order_items");
        onCreate(db);
    }

    public boolean dangKi(String name, String email , String password, String phone){
        SQLiteDatabase db = this.getWritableDatabase();
        if(checkEmailExists(email)){
            // Email đã tồn tại
            return false;
        }
        String sql = "INSERT INTO users (full_name, email, password, phone) VALUES(?,?,?,?)";
        try {
            db.execSQL(sql, new String[]{name, email, password, phone});
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean checkEmailExists(String email){
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM users WHERE email = ?";
        Cursor cursor = db.rawQuery(sql, new String[]{email});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }






}
