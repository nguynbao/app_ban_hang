package com.example.app_ban_hang.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.app_ban_hang.Model.CartItem;
import com.example.app_ban_hang.Model.product;

import java.util.ArrayList;
import java.util.List;

public class CartDao {
    private SQLiteDatabase db;
    public CartDao(Context context){
        database dbHelper = new database(context);
        db = dbHelper.getWritableDatabase();
        db.execSQL("PRAGMA foreign_keys=ON;");
    }
    @SuppressLint("Range")
    public List<CartItem> get(String sql, String... selectArgs){
        List<CartItem> cartItemList = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, selectArgs);
        while (cursor.moveToNext()){
            CartItem cartItem = new CartItem();
            cartItem.setCart_id(cursor.getInt(cursor.getColumnIndexOrThrow("cart_item_id")));
            cartItem.setUser_id(cursor.getInt(cursor.getColumnIndexOrThrow("user_id")));
            cartItem.setQuantity(cursor.getInt(cursor.getColumnIndexOrThrow("quantity")));
            cartItem.setProduct_id(cursor.getInt(cursor.getColumnIndexOrThrow("product_id")));
            cartItemList.add(cartItem);
        }
        cursor.close();
        return cartItemList;
    }

    // Lấy tất cả item trong giỏ hàng
    public List<CartItem> getAll (){
        String sql = "SELECT * FROM cart_items";
        return get(sql);
    }

    // Lấy theo cartID
    public CartItem getByCartId (String cartId){
        String sql = "SELECT * FROM cart_items WHERE cart_item_id = ?";
        List<CartItem> cartItemList = get(sql, cartId);
        CartItem cartItem = new CartItem();
        if (cartItemList.size() > 0){
            cartItem = cartItemList.get(0);
        }
        return cartItem;
    }

    // Lấy tất cả item theo idUser
    public List<CartItem> getItemIdUser (String UserID){
        String sql = "SELECT * FROM cart_items WHERE user_id = ?";
        return get(sql, UserID);
    }
    //Lấy item theo productID
    public CartItem getItemIdProduct(String productID){
        String sql = "SELECT * FROM cart_items WHERE product_id = ?";
        List<CartItem> cartItemList = get(sql, productID);
        CartItem cartItem = new CartItem();
        if (cartItemList.size() > 0){
            cartItem = cartItemList.get(0);
        }
        return  cartItem;
    }
    // Thêm item vào giỏ hàng
    public long insert(CartItem cartItem){
        ContentValues contentValues = new ContentValues();
        contentValues.put("user_id", cartItem.getUser_id());
        contentValues.put("product_id", cartItem.getProduct_id());
        contentValues.put("quantity", cartItem.getQuantity());

        return db.insert("cart_items", null, contentValues);
    }

    // Tăng giảm quantity sản phẩm
    public int updateQuantity(int Quantity, int cartID){
        ContentValues contentValues = new ContentValues();
        contentValues.put("quantity", Quantity);

        return db.update("cart_items", contentValues, "cart_item_id = ?", new String[]{String.valueOf(cartID)});
    }

    //Xóa những đơn hàng mà product đã bị xóa khỏi data
    public int deletedCartByIdProduct(int productID){
        ContentValues contentValues = new ContentValues();
        contentValues.put("product_id", productID);
        return db.delete("cart_items", "product_id = ?", new String[]{String.valueOf(productID)});
    }

}
