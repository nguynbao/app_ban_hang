package com.example.app_ban_hang.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.app_ban_hang.Model.order;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class OrderDao {
    private SQLiteDatabase db;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
    public OrderDao (Context context){
        database dbHelper = new database(context);
        db = dbHelper.getWritableDatabase();
    }
    @SuppressLint("Range")
    public List<order> get(String sql, String... selectArgs){
        List<order> orderList = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, selectArgs);
        while (cursor.moveToNext()){
            order order = new order();
            order.setOrderId(cursor.getInt(cursor.getColumnIndexOrThrow("order_id")));
            order.setUserId(cursor.getInt(cursor.getColumnIndexOrThrow("user_id")));

            String dateString = cursor.getString(cursor.getColumnIndexOrThrow("order_date"));
            try{
                Date orderDate =  sdf.parse(dateString);
                order.setOrderDate(orderDate);
            } catch (ParseException e) {
                e.printStackTrace();
                order.setOrderDate(null);
            }

            order.setShippingAddress(cursor.getString(cursor.getColumnIndexOrThrow("shipping_address")));
            order.setTotalAmount(cursor.getFloat(cursor.getColumnIndexOrThrow("total_amount")));
            order.setPhone_no(cursor.getString(cursor.getColumnIndexOrThrow("phone_no")));
            orderList.add(order);

        }
        cursor.close();
        return orderList;
    }

    //Thêm order mới
    public long insertOrder(order order){
        ContentValues contentValues = new ContentValues();
        contentValues.put("user_id", order.getUserId() );
        contentValues.put("shipping_address", order.getShippingAddress());
        contentValues.put("total_amount", order.getTotalAmount());

        String orderDate = sdf.format(order.getOrderDate());
        contentValues.put("order_date", orderDate);
        contentValues.put("phone_no", order.getPhone_no());

        return db.insert("orders", null, contentValues);
    }
}
