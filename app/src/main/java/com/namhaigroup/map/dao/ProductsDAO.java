package com.namhaigroup.map.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.namhaigroup.map.database.DatabaseHelper;
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
                Products products = new Products(id, name, description, type, price, quantity, sold, image);
                productsList.add(products);
            }
            cursor.close();
        }
        db.close();
        return productsList;
    }
}
