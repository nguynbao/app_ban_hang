package com.example.app_ban_hang.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class database extends SQLiteOpenHelper {
    private static final String DB_NAME = "Database.db";
    private static final int DB_VERSION = 5;
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
}
