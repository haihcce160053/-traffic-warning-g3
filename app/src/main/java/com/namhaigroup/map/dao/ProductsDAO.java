package com.namhaigroup.map.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.namhaigroup.map.database.DatabaseHelper;
import com.namhaigroup.map.object.Product_type;
import com.namhaigroup.map.object.Products;

import java.util.ArrayList;
import java.util.List;

public class ProductsDAO {
    private DatabaseHelper databaseHelper;
    private Context context;

    public ProductsDAO(Context context) {
        this.context = context;
        databaseHelper = new DatabaseHelper(context);
    }

    public Product_type getProductTypeById(int id) {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        String[] projection = {
                "id",
                "name"
        };

        String selection = "id = ?";
        String[] selectionArgs = {String.valueOf(id)};

        Cursor cursor = db.query(
                "product_type",
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if (cursor != null && cursor.moveToFirst()) {
            int product_id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            String product_name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
            Product_type productType = new Product_type(product_id, product_name);
            cursor.close();
            db.close();
            return productType;
        } else {
            db.close();
            return null;
        }
    }

    public Products getProductById(int id) {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        String[] projection = {
                "id",
                "name",
                "description",
                "type",
                "price",
                "quantity",
                "sold",
                "image"
        };
        String selection = "id = ?";
        String[] selectionArgs = { String.valueOf(id) };
        Cursor cursor = db.query(
                "products",
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
        Products product = null;
        if (cursor != null && cursor.moveToFirst()) {
            int product_id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
            String description = cursor.getString(cursor.getColumnIndexOrThrow("description"));
            int type = cursor.getInt(cursor.getColumnIndexOrThrow("type"));
            double price = cursor.getDouble(cursor.getColumnIndexOrThrow("price"));
            int quantity = cursor.getInt(cursor.getColumnIndexOrThrow("quantity"));
            int sold = cursor.getInt(cursor.getColumnIndexOrThrow("sold"));
            String image = cursor.getString(cursor.getColumnIndexOrThrow("image"));

            Product_type productType = getProductTypeById(type);
            product = new Products(product_id, name, description, type, price, quantity, sold, image, productType);
            cursor.close();
        }
        db.close();
        return product;
    }

    public List<Products> displayProducts() {
        List<Products> productsList = new ArrayList<>();
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        String[] projection = {
                "id",
                "name",
                "description",
                "type",
                "price",
                "quantity",
                "sold",
                "image"
        };

        Cursor cursor = db.query(
                "products",
                projection,
                null,
                null,
                null,
                null,
                null
        );

        if (cursor != null) {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                String description = cursor.getString(cursor.getColumnIndexOrThrow("description"));
                int type = cursor.getInt(cursor.getColumnIndexOrThrow("type"));
                double price = cursor.getDouble(cursor.getColumnIndexOrThrow("price"));
                int quantity = cursor.getInt(cursor.getColumnIndexOrThrow("quantity"));
                int sold = cursor.getInt(cursor.getColumnIndexOrThrow("sold"));
                String image = cursor.getString(cursor.getColumnIndexOrThrow("image"));

                Product_type productType = getProductTypeById(type);
                Products products = new Products(id, name, description, type, price, quantity, sold, image, productType);
                productsList.add(products);
            }
            cursor.close();
        }
        db.close();
        return productsList;
    }

    public long addProduct(Products product) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", product.getName());
        values.put("description", product.getDescription());
        values.put("type", product.getType());
        values.put("price", product.getPrice());
        values.put("quantity", product.getQuantity());
        values.put("sold", product.getSold());
        values.put("image", product.getImage());

        long newRowId = db.insert("products", null, values);
        db.close();
        return newRowId;
    }

    public int updateProduct(Products product) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", product.getName());
        values.put("description", product.getDescription());
        values.put("type", product.getType());
        values.put("price", product.getPrice());
        values.put("quantity", product.getQuantity());
        values.put("sold", product.getSold());
        values.put("image", product.getImage());

        String selection = "id = ?";
        String[] selectionArgs = {String.valueOf(product.getId())};

        int updatedRows = db.update("products", values, selection, selectionArgs);
        db.close();
        return updatedRows;
    }

    public boolean deleteProduct(int productId) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        String selection = "id = ?";
        String[] selectionArgs = {String.valueOf(productId)};
        int deletedRows = db.delete("products", selection, selectionArgs);
        db.close();
        return deletedRows > 0;
    }

    public boolean deleteCart(int productId) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        String selection = "product_id = ?";
        String[] selectionArgs = {String.valueOf(productId)};
        int deletedRows = db.delete("cart", selection, selectionArgs);
        db.close();
        return deletedRows > 0;
    }
}
