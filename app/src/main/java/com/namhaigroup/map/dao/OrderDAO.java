package com.namhaigroup.map.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.namhaigroup.map.database.DatabaseHelper;
import com.namhaigroup.map.object.Orders;
import com.namhaigroup.map.object.Orders_status;
import com.namhaigroup.map.object.Products;

import java.util.ArrayList;
import java.util.List;

public class OrderDAO {
    ProductsDAO productsDAO;
    private DatabaseHelper databaseHelper;
    private Context context;

    public OrderDAO(Context context) {
        this.context = context;
        databaseHelper = new DatabaseHelper(context);
        productsDAO = new ProductsDAO(context);
    }

    public List<Orders> createOrders(List<Orders> orders) {
        List<Orders> result = new ArrayList<>();
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        for (Orders order : orders) {
            ContentValues values = new ContentValues();
            values.put("username", order.getUsername());
            values.put("product_id", order.getProduct_id());
            values.put("quantity", order.getQuantity());
            values.put("total_price", order.getTotal_price());
            values.put("order_date", order.getOrder_date());
            values.put("status", order.getStatus());
            long newRowId = db.insert("orders", null, values);
            if (newRowId != -1) {
                result.add(order);
            }
        }
        db.close();
        return result;
    }

    public Orders_status getOrderStatusById(int id) {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        String[] projection = {
                "id",
                "name"
        };

        String selection = "id = ?";
        String[] selectionArgs = {String.valueOf(id)};

        Cursor cursor = db.query(
                "orders_status",
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if (cursor != null && cursor.moveToFirst()) {
            int orderStatusId = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            String orderStatusName = cursor.getString(cursor.getColumnIndexOrThrow("name"));
            Orders_status orderStatus = new Orders_status(orderStatusId, orderStatusName);
            cursor.close();
            db.close();
            return orderStatus;
        } else {
            db.close();
            return null;
        }
    }

    public List<Orders> getOrderByUsername(String username) {
        List<Orders> ordersList = new ArrayList<>();
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        String[] projection = {
                "id",
                "username",
                "product_id",
                "quantity",
                "total_price",
                "order_date",
                "status"
        };

        String selection = "username = ?";
        String[] selectionArgs = {String.valueOf(username)};

        Cursor cursor = db.query(
                "orders",
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
                String order_username = cursor.getString(cursor.getColumnIndexOrThrow("username"));
                int product_id = cursor.getInt(cursor.getColumnIndexOrThrow("product_id"));
                int quantity = cursor.getInt(cursor.getColumnIndexOrThrow("quantity"));
                double total_price = cursor.getDouble(cursor.getColumnIndexOrThrow("total_price"));
                String order_date = cursor.getString(cursor.getColumnIndexOrThrow("order_date"));
                int status = cursor.getInt(cursor.getColumnIndexOrThrow("status"));

                Products products = productsDAO.getProductById(product_id);
                Orders_status orderStatus = getOrderStatusById(status);

                Orders order = new Orders(id, order_username, product_id, quantity, total_price, order_date, status, products, orderStatus);
                ordersList.add(order);
            }
            cursor.close();
        }
        db.close();
        return ordersList;
    }

    public Orders getOrderById(int id) {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        String[] projection = {
                "id",
                "username",
                "product_id",
                "quantity",
                "total_price",
                "order_date",
                "status"
        };

        String selection = "id = ?";
        String[] selectionArgs = {String.valueOf(id)};

        Cursor cursor = db.query(
                "orders",
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if (cursor != null && cursor.moveToFirst()) {
            int order_id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            String order_username = cursor.getString(cursor.getColumnIndexOrThrow("username"));
            int product_id = cursor.getInt(cursor.getColumnIndexOrThrow("product_id"));
            int quantity = cursor.getInt(cursor.getColumnIndexOrThrow("quantity"));
            double total_price = cursor.getDouble(cursor.getColumnIndexOrThrow("total_price"));
            String order_date = cursor.getString(cursor.getColumnIndexOrThrow("order_date"));
            int status = cursor.getInt(cursor.getColumnIndexOrThrow("status"));

            Products products = productsDAO.getProductById(product_id);
            Orders_status orderStatus = getOrderStatusById(status);
            Orders orders = new Orders(order_id, order_username, product_id, quantity, total_price, order_date, status, products, orderStatus);
            cursor.close();
            return orders;
        } else {
            db.close();
            return null;
        }
    }

    public List<Orders> getAllOrders() {
        List<Orders> ordersList = new ArrayList<>();
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        String[] projection = {
                "id",
                "username",
                "product_id",
                "quantity",
                "total_price",
                "order_date",
                "status"
        };

        Cursor cursor = db.query(
                "orders",
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
                String order_username = cursor.getString(cursor.getColumnIndexOrThrow("username"));
                int product_id = cursor.getInt(cursor.getColumnIndexOrThrow("product_id"));
                int quantity = cursor.getInt(cursor.getColumnIndexOrThrow("quantity"));
                double total_price = cursor.getDouble(cursor.getColumnIndexOrThrow("total_price"));
                String order_date = cursor.getString(cursor.getColumnIndexOrThrow("order_date"));
                int status = cursor.getInt(cursor.getColumnIndexOrThrow("status"));

                Products products = productsDAO.getProductById(product_id);
                Orders_status orderStatus = getOrderStatusById(status);

                Orders order = new Orders(id, order_username, product_id, quantity, total_price, order_date, status, products, orderStatus);
                ordersList.add(order);
            }
            cursor.close();
        }
        db.close();
        return ordersList;
    }

    public boolean deleteVoucherById(int id) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        int deletedRows = db.delete("voucher", "id = ?", new String[]{String.valueOf(id)});
        db.close();
        return deletedRows > 0;
    }

    public boolean updateOrder(Orders updatedOrder) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("username", updatedOrder.getUsername());
        values.put("product_id", updatedOrder.getProduct_id());
        values.put("quantity", updatedOrder.getQuantity());
        values.put("total_price", updatedOrder.getTotal_price());
        values.put("order_date", updatedOrder.getOrder_date());
        values.put("status", updatedOrder.getStatus());

        String selection = "id = ?";
        String[] selectionArgs = {String.valueOf(updatedOrder.getId())};

        int rowsUpdated = db.update("orders", values, selection, selectionArgs);

        db.close();

        return rowsUpdated > 0;
    }

}
