package com.example.app_ban_hang.database;

public class Create_table {
    public static final String CREATE_TABLE_USERS =
            "CREATE TABLE users (" +
                    "user_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "full_name TEXT NOT NULL," +
                    "email TEXT NOT NULL UNIQUE," +
                    "password TEXT NOT NULL," +
                    "phone INTEGER," +
                    "address_line TEXT" + ");";

    public static final String CREATE_TABLE_CATEGORIES =
            "CREATE TABLE categories (" +
                    "category_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "name TEXT NOT NULL UNIQUE," +
                    "image_url TEXT" +
                    ");";

    public static final String CREATE_TABLE_PRODUCTS =
            "CREATE TABLE products (" +
                    "product_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "category_id INTEGER NOT NULL," +
                    "name TEXT NOT NULL," +
                    "description TEXT," +
                    "price REAL NOT NULL," +
                    "image_url TEXT," +
                    "FOREIGN KEY (category_id) REFERENCES categories(category_id) " +
                    ");";

    public static final String CREATE_TABLE_CART_ITEMS =
            "CREATE TABLE cart_items (" +
                    "cart_item_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "user_id INTEGER NOT NULL," +
                    "product_id INTEGER NOT NULL," +
                    "quantity INTEGER NOT NULL DEFAULT 1," +
                    "FOREIGN KEY (user_id) REFERENCES users(user_id) " +
                    "ON DELETE CASCADE ON UPDATE CASCADE," +
                    "FOREIGN KEY (product_id) REFERENCES products(product_id) " +
                    "ON DELETE CASCADE ON UPDATE CASCADE" +
                    ");";

    public static final String CREATE_TABLE_WISHLISTS =
            "CREATE TABLE wishlists (" +
                    "wishlist_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "user_id INTEGER NOT NULL," +
                    "product_id INTEGER NOT NULL," +
                    "FOREIGN KEY (user_id) REFERENCES users(user_id) " +
                    "ON DELETE CASCADE ON UPDATE CASCADE," +
                    "FOREIGN KEY (product_id) REFERENCES products(product_id) " +
                    "ON DELETE CASCADE ON UPDATE CASCADE" +
                    ");";

    public static final String CREATE_TABLE_ORDERS =
            "CREATE TABLE orders (" +
                    "order_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "user_id INTEGER NOT NULL," +
                    "order_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP," +
                    "total_amount REAL NOT NULL DEFAULT 0.00," +
                    "shipping_address TEXT NOT NULL," +
                    "phone_no TEXT NOT NULL," +
                    "FOREIGN KEY (user_id) REFERENCES users(user_id) " +
                    "ON DELETE RESTRICT ON UPDATE CASCADE" +
                    ");";

    public static final String CREATE_TABLE_ORDER_ITEMS =
            "CREATE TABLE order_items (" +
                    "order_item_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "order_id INTEGER NOT NULL," +
                    "product_id INTEGER NOT NULL," +
                    "quantity INTEGER NOT NULL," +
                    "unit_price REAL NOT NULL," +
                    "subtotal REAL NOT NULL," +
                    "FOREIGN KEY (order_id) REFERENCES orders(order_id) " +
                    "ON DELETE CASCADE ON UPDATE CASCADE," +
                    "FOREIGN KEY (product_id) REFERENCES products(product_id) " +
                    "ON DELETE RESTRICT ON UPDATE CASCADE" +
                    ");";
}