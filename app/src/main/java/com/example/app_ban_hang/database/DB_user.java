package com.example.app_ban_hang.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DB_user extends SQLiteOpenHelper {
    private static final String DB_NAME = "Database.db";
    private static final int DB_VERSION = 1;
    public DB_user(Context context){
        super(context, DB_NAME, null,  DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE user(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "password TEXT," +
                "email TEXT UNIQUE," +
                "phone TEXT UNIQUE)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS user");
        onCreate(db);
    }
    public boolean dangKi(String password, String email, String phone){
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "INSERT INTO user(password, email, phone) VALUES(?,?,?)";
        try {
            db.execSQL(sql, new String[]{password, email, phone});
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean dangNhap(String email, String password){
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM user WHERE email = ? AND password = ?";
        Cursor cursor = db.rawQuery(sql, new String[]{email, password});
        boolean result = cursor.getCount() > 0;
        cursor.close();
        return result;
    }


}
