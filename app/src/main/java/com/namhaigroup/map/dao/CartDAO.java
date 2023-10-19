package com.namhaigroup.map.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.namhaigroup.map.database.DatabaseHelper;
import com.namhaigroup.map.object.Cart;
import com.namhaigroup.map.object.Products;

import java.util.ArrayList;
import java.util.List;

public class CartDAO {
    private ProductsDAO productsDAO;
    private DatabaseHelper databaseHelper;
    private Context context;
    public CartDAO(Context context) {
        this.context = context;
        databaseHelper = new DatabaseHelper(context);
        productsDAO = new ProductsDAO(context);
    }

    public Cart getCartById(int id) {
        Log.d("hello2", String.valueOf(id));
        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        String[] projection = {
                "id",
                "username",
                "product_id",
                "quantity",
        };

        String selection = "id = ?";
        String[] selectionArgs = {String.valueOf(id)};

        Cursor cursor = db.query(
                "cart",
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if(cursor.moveToFirst()) {
            int cartId = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            String username = cursor.getString(cursor.getColumnIndexOrThrow("username"));
            int product_id = cursor.getInt(cursor.getColumnIndexOrThrow("product_id"));
            int quantity = cursor.getInt(cursor.getColumnIndexOrThrow("quantity"));

            Products products = productsDAO.getProductById(id);
            Cart cart = new Cart(cartId, username, product_id, quantity, products);
            cart.setProducts(products);

            return cart;
        } else {
            return null;
        }
    }

    public List<Cart> getCartByUsername(String username) {
        List<Cart> cartList = new ArrayList<>();
        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        String[] projection = {
                "id",
                "username",
                "product_id",
                "quantity",
        };

        String selection = "username = ?";
        String[] selectionArgs = {username};

        Cursor cursor = db.query(
                "cart",
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if (cursor != null) {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String cart_username = cursor.getString(cursor.getColumnIndexOrThrow("username"));
                int product_id = cursor.getInt(cursor.getColumnIndexOrThrow("product_id"));
                int quantity = cursor.getInt(cursor.getColumnIndexOrThrow("quantity"));

                Products products = productsDAO.getProductById(product_id);
                Cart cart = new Cart(id, cart_username, product_id, quantity, products);
                cart.setProducts(products);
                cartList.add(cart);
            }
            cursor.close();
        }
        db.close();
        return cartList;
    }
}
