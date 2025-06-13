package com.example.app_ban_hang.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.app_ban_hang.Model.categories;

import java.util.ArrayList;
import java.util.List;

// Lớp Data Access Object cho bảng Category
public class CategoryDao {
    private SQLiteDatabase db;

    public CategoryDao(Context context) {
        database dbHelper = new database(context);
        db = dbHelper.getWritableDatabase();
    }

    // Hàm thêm danh mục mới
    public long insertCategory(categories category) {
        ContentValues values = new ContentValues();
        values.put("name", category.getCategory_name());
        values.put("image_url", category.getCategory_imgRes());
        return db.insert("categories", null, values);
    }

    // Hàm sửa danh mục theo id
    public int updateCategory(categories category) {
        ContentValues values = new ContentValues();
        values.put("name", category.getCategory_name());
        values.put("image_url", category.getCategory_imgRes());
        return db.update("categories", values, "category_id=?", new String[]{String.valueOf(category.getCategory_id())});
    }

    // Hàm xóa danh mục theo id
    public int deleteCategory(int id) {
        return db.delete("categories", "category_id=?", new String[]{String.valueOf(id)});
    }

    // Hàm lấy danh sách tất cả danh mục
    public List<categories> getAllCategories() {
        List<categories> categoryList = new ArrayList<>();
        Cursor cursor = db.query("categories", null, null, null, null, null, "category_id DESC");
        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("category_id"));
                String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                int imgRes  = cursor.getInt(cursor.getColumnIndexOrThrow("image_url"));
                categoryList.add(new categories(id,name,imgRes));
            } while (cursor.moveToNext());
            cursor.close();
        }
        return categoryList;
    }

    // Hàm lấy danh mục theo id (tuỳ chọn)
    public categories getCategoryById(int id) {
        Cursor cursor = db.query("categories", null, "category_id=?", new String[]{String.valueOf(id)}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
            int imgRes  = cursor.getInt(cursor.getColumnIndexOrThrow("image_url"));
            cursor.close();
            return new categories(id,name, imgRes);
        }
        return null;
    }
    public List<String> getAllCategoryNames() {
        List<String> categoryNames = new ArrayList<>();
        Cursor cursor = db.query("categories",
                new String[]{"name"},
                null,
                null,
                null,
                null,
                "category_id DESC");
        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                categoryNames.add(name);
            } while (cursor.moveToNext());
            cursor.close();  // đóng cursor tránh leak
        }
        return categoryNames;
    }
    // Hàm lấy tên danh mục theo id
    public String getCategoryNameById(int id) {
        String name = null;
        Cursor cursor = db.query(
                "categories",
                new String[]{"name"},
                "category_id=?",
                new String[]{String.valueOf(id)},
                null,
                null,
                null
        );

        if (cursor != null && cursor.moveToFirst()) {
            name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
            cursor.close();
        }
        return name;
    }
    public int getCategoryIdByName(String categoryName) {
        Cursor cursor = db.query(
                "categories",
                new String[]{"category_id"},
                "name = ?",
                new String[]{categoryName},
                null,
                null,
                null
        );
        int categoryId = -1;
        if (cursor != null && cursor.moveToFirst()) {
            categoryId = cursor.getInt(cursor.getColumnIndexOrThrow("category_id"));
            cursor.close();
        }
        return categoryId;
    }



}
