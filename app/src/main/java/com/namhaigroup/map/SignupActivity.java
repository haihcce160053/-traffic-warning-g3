package com.namhaigroup.map;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.namhaigroup.map.dao.AccountsDAO;
import com.namhaigroup.map.object.Accounts;
import com.namhaigroup.map.system.UserInformation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignupActivity extends AppCompatActivity {
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
                if(etUsername.getText().toString().length() > 0 &&
                etPassword.getText().toString().length() > 0 &&
                etFullname.getText().toString().length() > 0 &&
                etEmail.getText().toString().length() > 0 &&
                etPhone.getText().toString().length() == 10 || etPhone.getText().toString().length() == 11  &&
                etAddress.getText().toString().length() > 0) {
                    String pattern = "\\d{10}|(?:\\d{3}-){2}\\d{4}|\\(\\d{3}\\)\\d{3}-?\\d{4}";
                    Pattern p = Pattern.compile(pattern);
                    Matcher m = p.matcher(etPhone.getText().toString().trim());
                    if(m.matches()) {
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
                            UserInformation.isLogin = true;
                            UserInformation.username = etUsername.getText().toString().trim();
                            UserInformation.email = etEmail.getText().toString().trim();
                            UserInformation.phone = etPhone.getText().toString().trim();
                            UserInformation.permission = 0;
                            UserInformation.address = etAddress.getText().toString().trim();
                            Toast.makeText(SignupActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(SignupActivity.this, "Đăng ký thất bại, tài khoản đã tồn tại", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(SignupActivity.this, "Số điện thoại không hợp lệ", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(SignupActivity.this, "Thông tin không hợp lệ", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}