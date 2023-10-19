package com.namhaigroup.map;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.namhaigroup.map.dao.AccountsDAO;
import com.namhaigroup.map.database.DatabaseHelper;
import com.namhaigroup.map.object.Accounts;
import com.namhaigroup.map.system.UserInformation;

public class SignupActivity extends AppCompatActivity {
    private DatabaseHelper databaseHelper = new DatabaseHelper(this);
    EditText etUsername, etPassword, etFullname, etEmail, etPhone, etAddress;
    Button btnSignup;
    AccountsDAO accountsDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        etFullname = findViewById(R.id.etFullname);
        etEmail = findViewById(R.id.etEmail);
        etPhone = findViewById(R.id.etPhone);
        etAddress = findViewById(R.id.etAddress);
        btnSignup = findViewById(R.id.btnSignup);

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etUsername.length() > 0 &&
                etPassword.length() > 0 &&
                etFullname.length() > 0 &&
                etEmail.length() > 0 &&
                etPhone.length() == 10 || etPhone.length() == 11  &&
                etAddress.length() > 0) {
                    Accounts accounts = new Accounts();
                    accounts.setUsername(etUsername.getText().toString().trim());
                    accounts.setPassword(etPassword.getText().toString().trim());
                    accounts.setEmail(etEmail.getText().toString().trim());
                    accounts.setAddress(etAddress.getText().toString().trim());
                    accounts.setFullname(etFullname.getText().toString().trim());
                    accounts.setPhone(etPhone.getText().toString().trim());
                    accounts.setPermission(0);
                    accountsDAO =  new AccountsDAO(SignupActivity.this);
                    if(accountsDAO.addAccount(accounts) != null) {
                        Toast.makeText(SignupActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                        UserInformation.isLogin = true;
                        UserInformation.username = etUsername.getText().toString().trim();
                        UserInformation.email = etEmail.getText().toString().trim();
                        UserInformation.phone = etPhone.getText().toString().trim();
                        UserInformation.permission = 0;
                        UserInformation.address = etAddress.getText().toString().trim();
                        finish();
                    } else {
                        Toast.makeText(SignupActivity.this, "Đăng ký thất bại, tài khoản đã tồn tại", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(SignupActivity.this, "Thông tin không hợp lệ", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}