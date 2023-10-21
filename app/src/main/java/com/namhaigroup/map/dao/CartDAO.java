package com.namhaigroup.map.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

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

    public Cart addCart(Cart cart) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        String selectQuery = "SELECT * FROM cart WHERE product_id = ?";
        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(cart.getProduct_id())});

        if (cursor.getCount() > 0) {
            String updateQuery = "UPDATE cart SET quantity = quantity + 1 WHERE product_id = ?";
            db.execSQL(updateQuery, new String[]{String.valueOf(cart.getProduct_id())});
        } else {
            values.put("username", cart.getUsername());
            values.put("product_id", cart.getProduct_id());
            values.put("quantity", cart.getQuantity());
            long newRowId = db.insert("cart", null, values);
            if (newRowId == -1) {
                return null;
            }
        }
        db.close();
        return cart;
    }

    public Cart getCartById(int id) {
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
        if(cursor != null && cursor.moveToFirst()) {
            int cartId = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            String username = cursor.getString(cursor.getColumnIndexOrThrow("username"));
            int product_id = cursor.getInt(cursor.getColumnIndexOrThrow("product_id"));
            int quantity = cursor.getInt(cursor.getColumnIndexOrThrow("quantity"));

            Products products = productsDAO.getProductById(product_id);
            Cart cart = new Cart(cartId, username, product_id, quantity, products);
            cart.setProducts(products);
            cursor.close();
            return cart;
        } else {
            cursor.close();
            db.close();
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

    public boolean increaseQuantity(int cartId) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        Cart cart = getCartById(cartId);
        if (cart != null) {
            values.put("quantity", cart.getQuantity() + 1);
            int updatedRows = db.update("cart", values, "id = ?", new String[]{String.valueOf(cart.getId())});
            db.close();
            return updatedRows > 0;
        } else {
            db.close();
            return false;
        }
    }

    public boolean decreaseQuantity(int cartId) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        Cart cart = getCartById(cartId);
        if (cart != null && cart.getQuantity() > 1) {
            int newQuantity = cart.getQuantity() - 1;
            values.put("quantity", newQuantity);
            String selection = "id = ?";
            String[] selectionArgs = {String.valueOf(cartId)};
            int updatedRows = db.update("cart", values, selection, selectionArgs);
            db.close();
            return updatedRows > 0;
        } else {
            db.close();
            return false;
        }
    }
    public void deleteCartList(List<Cart> _cart) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        for (Cart cart : _cart) {
            db.delete("cart", "id = ?", new String[]{String.valueOf(cart.getId())});
        }
        db.close();
    }

    public void deleteCartById(int cartId) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        db.delete("cart", "id = ?", new String[]{String.valueOf(cartId)});
        db.close();
    }
}
