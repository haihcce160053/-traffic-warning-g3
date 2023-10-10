package com.namhaigroup.map;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.namhaigroup.map.system.UserInformation;

public class MenuActivity extends AppCompatActivity {
    LinearLayout lnLogined, lnNotLogin;
    RelativeLayout btnLogout;
    Button btnNogSignIn, btnNotSignUp;
    TextView tvUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        lnLogined = findViewById(R.id.lnLogined);
        lnNotLogin = findViewById(R.id.lnNotLogin);
        btnNogSignIn = findViewById(R.id.btnNogSignIn);
        btnNotSignUp = findViewById(R.id.btnNotSignUp);
        btnLogout = findViewById(R.id.btnLogout);
        tvUsername = findViewById(R.id.tvUsername);

        btnNogSignIn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(UserInformation.isLogin == false) {
                    Intent i = new Intent(MenuActivity.this, LoginActivity.class);
                    startActivity(i);
                }
            }
        });
        btnNotSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(UserInformation.isLogin == false) {
                    Intent i = new Intent(MenuActivity.this, SignupActivity.class);
                    startActivity(i);
                }
            }
        });
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserInformation.isLogin = false;
                UserInformation.username = null;
                UserInformation.fullname = null;
                UserInformation.phone = null;
                UserInformation.email = null;
                UserInformation.premiumType = null;
                UserInformation.address = null;
                UserInformation.permission = 0;
                lnLogined.setVisibility(View.GONE);
                lnNotLogin.setVisibility(View.VISIBLE);
                Intent i = new Intent(MenuActivity.this, MainActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(UserInformation.isLogin == true) {
            lnLogined.setVisibility(View.VISIBLE);
            lnNotLogin.setVisibility(View.GONE);
            tvUsername.setText("Tài khoản: " + UserInformation.username);
        } else {
            lnLogined.setVisibility(View.GONE);
            lnNotLogin.setVisibility(View.VISIBLE);
        }
    }
}