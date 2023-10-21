package com.namhaigroup.map.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.namhaigroup.map.database.DatabaseHelper;
import com.namhaigroup.map.object.Voucher;

public class VoucherDAO {
    private DatabaseHelper databaseHelper;
    private Context context;
    public VoucherDAO(Context context) {
        this.context = context;
        databaseHelper = new DatabaseHelper(context);
    }

    public Voucher getVoucherByCode(String voucher_code) {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        String[] projection = {
                "id",
                "voucher_code",
                "sale"
        };

        String selection = "voucher_code = ?";
        String[] selectionArgs = {String.valueOf(voucher_code)};

        Cursor cursor = db.query(
                "voucher",
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            String code = cursor.getString(cursor.getColumnIndexOrThrow("voucher_code"));
            double sale = cursor.getDouble(cursor.getColumnIndexOrThrow("sale"));
            cursor.close();
            db.close();
            return new Voucher(id, code, sale);
        } else {
            db.close();
            return null;
        }
    }

    public boolean deleteVoucherById(int id) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        int deletedRows = db.delete("voucher", "id = ?", new String[]{String.valueOf(id)});
        db.close();
        return deletedRows > 0;
    }
}
