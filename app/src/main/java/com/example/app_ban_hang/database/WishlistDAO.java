package com.example.app_ban_hang.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.example.app_ban_hang.Model.wishlists;
import com.example.app_ban_hang.database.database;

import java.util.ArrayList;
import java.util.List;

public class WishlistDAO {
    private SQLiteDatabase db;

    public WishlistDAO(Context context) {
        database dbHelper = new database(context);
        db = dbHelper.getWritableDatabase();
    }
    // Thêm sản phẩm vào wishlist
    public long insertWishlist(int userId, int productId) {
        ContentValues values = new ContentValues();
        values.put("user_id", userId);
        values.put("product_id", productId);
        return db.insert("wishlists", null, values);
    }

    // Xóa sản phẩm khỏi wishlist
    public int deleteWishlist(int wishlistId) {
        return db.delete("wishlists", "wishlist_id = ?", new String[]{String.valueOf(wishlistId)});
    }

    // Lấy danh sách sản phẩm yêu thích theo userId
    public List<wishlists> getWishlistsByUser(int userId) {
        List<wishlists> list = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM wishlists WHERE user_id = ?", new String[]{String.valueOf(userId)});
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("wishlist_id"));
                int uId = cursor.getInt(cursor.getColumnIndexOrThrow("user_id"));
                int pId = cursor.getInt(cursor.getColumnIndexOrThrow("product_id"));
                list.add(new wishlists(id, uId, pId));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    // Kiểm tra 1 sản phẩm đã có trong wishlist chưa
    public boolean isProductInWishlist(int userId, int productId) {
        Cursor cursor = db.rawQuery("SELECT * FROM wishlists WHERE user_id = ? AND product_id = ?",
                new String[]{String.valueOf(userId), String.valueOf(productId)});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }
    public List<Integer> getWishlistProductIds(int userId) {
        List<Integer> productIds = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT product_id FROM wishlists WHERE user_id = ?", new String[]{String.valueOf(userId)});
        if (cursor.moveToFirst()) {
            do {
                productIds.add(cursor.getInt(0));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return productIds;
    }
    public void removeFromWishlist(int userId, int productId) {
        db.delete("wishlists", "user_id = ? AND product_id = ?",
                new String[]{String.valueOf(userId), String.valueOf(productId)});
    }


}
