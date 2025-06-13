package com.example.app_ban_hang.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.example.app_ban_hang.Model.product;

import java.util.ArrayList;
import java.util.List;

public class ProductDao {
    private SQLiteDatabase db;

    public ProductDao(Context context) {
        database dbhelper = new database(context);
        db = dbhelper.getWritableDatabase();
    }

    @SuppressLint("Range")
    public List<product> get(String sql, String... selectArgs) {
        List<product> productList = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, selectArgs);
        while (cursor.moveToNext()) {
            product product = new product();
            product.setProduct_id(cursor.getInt(cursor.getColumnIndexOrThrow("product_id")));
            product.setCategory_id(cursor.getInt(cursor.getColumnIndexOrThrow("category_id")));
            product.setProduct_name(cursor.getString(cursor.getColumnIndexOrThrow("name")));
            product.setProduct_description(cursor.getString(cursor.getColumnIndexOrThrow("description")));
            product.setProduct_price(cursor.getFloat(cursor.getColumnIndexOrThrow("price")));
            product.setProduct_imgRes(cursor.getString(cursor.getColumnIndexOrThrow("image_url")));
            productList.add(product);
        }
        cursor.close(); // Đóng cursor để tránh rò rỉ bộ nhớ
        return productList;
    }

    // Lấy tất cả sản phẩm
    public List<product> getAll (){
        String sql = "SELECT * FROM products";
        return get(sql);
    }

    // Lấy sản phẩm theo ID category
    public List<product> getByCategoryId(String category_id){
        String sql = "SELECT * FROM products WHERE category_id = ?";
        List<product> productList = get(sql, category_id);
        return productList;
    }

    // Lấy sản phẩm theo ID category
    public product getById(String id){
        String sql = "SELECT * FROM products WHERE product_id = ?";
        List<product> productList = get(sql, id);
        if(productList.size()>0){
            return productList.get(0);
        }
        return null;
    }

    // Thêm sản phẩm mới
    public long insert(product product){
        ContentValues contentValues = new ContentValues();
        contentValues.put("category_id", product.getCategory_id());
        contentValues.put("name", product.getProduct_name());
        contentValues.put("description", product.getProduct_description());
        contentValues.put("price", product.getProduct_price());
        contentValues.put("image_url", product.getProduct_imgRes().toString());


        return db.insert("products", null, contentValues);
    }

    //Xóa 1 sản phẩm theo id
    public int delete(String id){
        return db.delete("products", "product_id=?", new String[]{id});
    }
}
