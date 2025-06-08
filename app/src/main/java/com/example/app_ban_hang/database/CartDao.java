package com.example.app_ban_hang.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.app_ban_hang.Model.CartItem;

import java.util.ArrayList;
import java.util.List;

public class CartDao {
    private SQLiteDatabase db;

    public CartDao(Context context) {
        database dbHelper = new database(context);
        db = dbHelper.getWritableDatabase();
    }

    // Thêm sản phẩm vào giỏ hàng
    public long insert(CartItem cartItem) {
        ContentValues values = new ContentValues();
        values.put("user_id", cartItem.getUser_id());
        values.put("product_id", cartItem.getProduct_id());
        values.put("quantity", cartItem.getQuantity());
        return db.insert("cart_items", null, values);
    }

    // Cập nhật số lượng sản phẩm trong giỏ hàng
    public int update(CartItem cartItem) {
        ContentValues values = new ContentValues();
        values.put("quantity", cartItem.getQuantity());
        return db.update(
                "cart_items",
                values,
                "cart_id = ?",
                new String[]{String.valueOf(cartItem.getCart_id())}
        );
    }

    // Xóa sản phẩm khỏi giỏ hàng
    public int delete(int cart_id) {
        return db.delete(
                "cart_items",
                "cart_id = ?",
                new String[]{String.valueOf(cart_id)}
        );
    }

    // Lấy danh sách giỏ hàng của người dùng cụ thể
    public List<CartItem> getCartItemsByUser(int user_id) {
        List<CartItem> cartItems = new ArrayList<>();
        String sql = "SELECT * FROM cart_items WHERE user_id = ?";
        Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(user_id)});
        if (cursor.moveToFirst()) {
            do {
                int cartId = cursor.getInt(cursor.getColumnIndexOrThrow("cart_id"));
                int productId = cursor.getInt(cursor.getColumnIndexOrThrow("product_id"));
                int quantity = cursor.getInt(cursor.getColumnIndexOrThrow("quantity"));
                CartItem item = new CartItem(cartId, user_id, productId, quantity);
                cartItems.add(item);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return cartItems;
    }

    // Xóa toàn bộ giỏ hàng của người dùng
    public int clearCartByUser(int user_id) {
        return db.delete(
                "cart_items",
                "user_id = ?",
                new String[]{String.valueOf(user_id)}
        );
    }

    // Tính tổng số lượng sản phẩm trong giỏ hàng
    public int getTotalQuantity(int user_id) {
        int total = 0;
        String sql = "SELECT SUM(quantity) as total_quantity FROM cart_items WHERE user_id = ?";
        Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(user_id)});
        if (cursor.moveToFirst()) {
            total = cursor.getInt(cursor.getColumnIndexOrThrow("total_quantity"));
        }
        cursor.close();
        return total;
    }
}
