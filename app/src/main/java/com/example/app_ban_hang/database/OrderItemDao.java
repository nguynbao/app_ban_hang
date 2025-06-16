package com.example.app_ban_hang.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.app_ban_hang.Model.orderItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class OrderItemDao {
    private SQLiteDatabase db;

    public OrderItemDao (Context context){
        database dbHelper = new database(context);
        db = dbHelper.getWritableDatabase();
    }

    @SuppressLint("Range")
    public List<orderItem> get(String sql, String... selectArgs){
        List<orderItem> orderItemList = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, selectArgs);
        while (cursor.moveToNext()){
            orderItem orderItem = new orderItem();
            orderItem.setOrderItemId(cursor.getInt(cursor.getColumnIndexOrThrow("order_item_id")));
            orderItem.setOrderId(cursor.getInt(cursor.getColumnIndexOrThrow("order_id")));
            orderItem.setProductId(cursor.getInt(cursor.getColumnIndexOrThrow("product_id")));
            orderItem.setQuantity(cursor.getInt(cursor.getColumnIndexOrThrow("quantity")));
            orderItem.setUnitPrice(cursor.getFloat(cursor.getColumnIndexOrThrow("unit_price")));
            orderItem.setSubtotal(cursor.getFloat(cursor.getColumnIndexOrThrow("subtotal")));
            orderItemList.add(orderItem);
        }
        cursor.close();
        return orderItemList;
    }

    //Lấy tất cả orderItem
    public List<orderItem> getAll(){
        String sql = "SELECT * FROM order_items";
        return get(sql);
    }

    //Thêm orderItem
    public long insertOrderItem(orderItem orderItem){
        ContentValues contentValues = new ContentValues();
        contentValues.put("order_id", orderItem.getOrderId());
        contentValues.put("product_id", orderItem.getProductId());
        contentValues.put("quantity", orderItem.getQuantity());
        contentValues.put("unit_price", orderItem.getUnitPrice());
        contentValues.put("subtotal", orderItem.getSubtotal());
        return db.insert("order_items", null, contentValues);
    }
}
