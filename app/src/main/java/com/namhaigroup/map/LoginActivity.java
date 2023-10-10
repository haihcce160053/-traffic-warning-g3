package com.namhaigroup.map;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.namhaigroup.map.dao.AccountsDAO;
import com.namhaigroup.map.object.Accounts;
import com.namhaigroup.map.system.UserInformation;

import java.util.List;

public class LoginActivity extends AppCompatActivity {
    Button btnLogin;
    EditText etUsername, etPassword;
    AccountsDAO accountsDAO;

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
                if(accountsDAO.login(username, password) == true) {
                    Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                    UserInformation.isLogin = true;
                    UserInformation.username = username;
                    Intent i = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(i);
                } else {
                    Toast.makeText(LoginActivity.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
                }
                List<Accounts> accountList = accountsDAO.displayAccounts();
                for (Accounts account : accountList) {
                    Log.d("AccountInfo", "Username: " + account.getUsername() + ", Password: " + account.getPassword() +
                            ", Fullname: " + account.getFullname() + ", Email: " + account.getEmail() +
                            ", Phone: " + account.getPhone() + ", Address: " + account.getAddress() + ", Permission: " + account.getPermission());
                }
            }
        });
    }
}