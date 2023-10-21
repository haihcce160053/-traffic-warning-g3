package com.namhaigroup.map.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.namhaigroup.map.database.DatabaseHelper;
import com.namhaigroup.map.object.Accounts;

import java.util.ArrayList;
import java.util.List;

public class AccountsDAO {
    private DatabaseHelper databaseHelper;
    private Context context;
    public AccountsDAO(Context context) {
        this.context = context;
        databaseHelper = new DatabaseHelper(context);
    }

    public boolean login(String username, String password) {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        String[] projection = { "username" };
        String selection = "username = ? AND password  = ?";
        String[] selectionArgs = {username, password};
        Cursor cursor = db.query("accounts", projection, selection, selectionArgs, null, null, null);
        boolean loggedIn = cursor.moveToFirst();
        cursor.close();
        db.close();
        return loggedIn;
    }

    public Accounts addAccount(Accounts account) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", account.getUsername());
        values.put("password", account.getPassword());
        values.put("fullname", account.getFullname());
        values.put("email", account.getEmail());
        values.put("phone", account.getPhone());
        values.put("address", account.getAddress());
        values.put("permission", account.getPermission());
        long newRowId = db.insert("accounts", null, values);
        db.close();
        if (newRowId != -1) {
            return account;
        } else {
            return null;
        }
    }

    public boolean updateAccount(Accounts account) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("fullname", account.getFullname());
        values.put("email", account.getEmail());
        values.put("phone", account.getPhone());
        values.put("address", account.getAddress());
        int updatedRows = db.update("accounts", values, "username = ?", new String[]{account.getUsername()});
        db.close();
        return updatedRows > 0;
    }

    public boolean deleteAccount(String username) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        int deletedRows = db.delete("accounts", "username = ?", new String[]{username});
        db.close();
        return deletedRows > 0;
    }

    public Accounts getAccountByUsername(String username) {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        String[] projection = {
                "username",
                "password",
                "fullname",
                "email",
                "phone",
                "address",
                "permission"
        };

        String selection = "username = ?";
        String[] selectionArgs = {username};

        Cursor cursor = db.query(
                "accounts",
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if (cursor != null && cursor.moveToFirst()) {
            String password = cursor.getString(cursor.getColumnIndexOrThrow("password"));
            String fullname = cursor.getString(cursor.getColumnIndexOrThrow("fullname"));
            String email = cursor.getString(cursor.getColumnIndexOrThrow("email"));
            String phone = cursor.getString(cursor.getColumnIndexOrThrow("phone"));
            String address = cursor.getString(cursor.getColumnIndexOrThrow("address"));
            int permission = cursor.getInt(cursor.getColumnIndexOrThrow("permission"));
            cursor.close();
            db.close();
            return new Accounts(username, password, fullname, email, phone, address, permission);
        } else {
            db.close();
            return null;
        }
    }


    public List<Accounts> displayAccounts() {
        List<Accounts> accountList = new ArrayList<>();
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        String[] projection = {
                "username",
                "password",
                "fullname",
                "email",
                "phone",
                "address",
                "permission"
        };

        Cursor cursor = db.query(
                "accounts",
                projection,
                null,
                null,
                null,
                null,
                null
        );

        if (cursor != null) {
            while (cursor.moveToNext()) {
                String username = cursor.getString(cursor.getColumnIndexOrThrow("username"));
                String password = cursor.getString(cursor.getColumnIndexOrThrow("password"));
                String fullname = cursor.getString(cursor.getColumnIndexOrThrow("fullname"));
                String email = cursor.getString(cursor.getColumnIndexOrThrow("email"));
                String phone = cursor.getString(cursor.getColumnIndexOrThrow("phone"));
                String address = cursor.getString(cursor.getColumnIndexOrThrow("address"));
                int permission = cursor.getInt(cursor.getColumnIndexOrThrow("permission"));
                Accounts account = new Accounts(username, password, fullname, email, phone, address, permission);
                accountList.add(account);
            }
            cursor.close();
        }
        db.close();
        return accountList;
    }
}
