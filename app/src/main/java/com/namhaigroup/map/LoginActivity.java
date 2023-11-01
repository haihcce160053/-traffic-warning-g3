package com.namhaigroup.map;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.namhaigroup.map.dao.AccountsDAO;
import com.namhaigroup.map.dao.OrderDAO;
import com.namhaigroup.map.dao.ProductsDAO;
import com.namhaigroup.map.object.Accounts;
import com.namhaigroup.map.object.Orders;
import com.namhaigroup.map.system.UserInformation;

import java.util.List;

public class LoginActivity extends AppCompatActivity {
    Button btnLogin;
    EditText etUsername, etPassword;
    TextView tvPremium;
    AccountsDAO accountsDAO;
    ProductsDAO productsDAO;
    OrderDAO orderDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = findViewById(R.id.btnLogin);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accountsDAO =  new AccountsDAO(LoginActivity.this);
                String username = etUsername.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                if(username.length() > 0 && password.length() > 0) {
                    if(accountsDAO.login(username, password) == true) {
                        Accounts accounts = accountsDAO.getAccountByUsername(username);
                        orderDAO = new OrderDAO(LoginActivity.this);
                        productsDAO = new ProductsDAO(LoginActivity.this);
                        List<Orders> ordersList;
                        ordersList = orderDAO.getOrderByUsername(username);
                        if(ordersList.size() > 0) {
                            for (Orders order : ordersList) {
                                if(order.getProducts().getType() <= 4 && order.getStatus() == 5) {
                                    UserInformation.isPremium = true;
                                    break;
                                } else {
                                    UserInformation.isPremium = false;
                                }
                            }
                        }
                        Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                        UserInformation.isLogin = true;
                        UserInformation.username = username;
                        UserInformation.permission = accounts.getPermission();
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Vui lòng nhập đầy đủ thông tin đăng nhập", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}