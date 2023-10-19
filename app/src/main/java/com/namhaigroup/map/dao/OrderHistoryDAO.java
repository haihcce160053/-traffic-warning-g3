package com.namhaigroup.map.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.namhaigroup.map.database.DatabaseHelper;
import com.namhaigroup.map.object.OrderHistory;

import java.util.ArrayList;
import java.util.List;

public class OrderHistoryDAO {
    private DatabaseHelper databaseHelper;
    private Context context;

    public OrderHistoryDAO(Context context) {
        this.context = context;
        databaseHelper = new DatabaseHelper(context);
    }

    public List<OrderHistory> getAllOrderHistory() {
        List<OrderHistory> orderHistoryList = new ArrayList<>();
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        String[] projection = {
                "id",
                "username",
                "product_name",
                "order_date",
                "order_price",
                "product_id"
        };

        Cursor cursor = db.query(
                "order_history",
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
                String username =  cursor.getString(cursor.getColumnIndexOrThrow("username"));
                String product_name = cursor.getString(cursor.getColumnIndexOrThrow("product_name"));
                String order_date =  cursor.getString(cursor.getColumnIndexOrThrow("order_date"));
                Double order_price =  cursor.getDouble(cursor.getColumnIndexOrThrow("order_price"));
                int product_id = cursor.getInt(cursor.getColumnIndexOrThrow("product_id"));
                OrderHistory orderHistory = new OrderHistory(id, username, product_name, order_date, order_price, product_id);
                orderHistoryList.add(orderHistory);
            }
            cursor.close();
        }
        db.close();
        return orderHistoryList;
    }
}
