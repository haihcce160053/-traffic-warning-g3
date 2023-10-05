package com.namhaigroup.map;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class MenuActivity extends AppCompatActivity {
    Button btnNeedLogin, btnNeedSignUp;
    LinearLayout lnLogined, lnNotLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        btnNeedLogin = findViewById(R.id.btnNeedLogin);
        btnNeedSignUp = findViewById(R.id.btnNeedSignUp);
        lnLogined = findViewById(R.id.lnLogined);
        lnNotLogin = findViewById(R.id.lnNotLogin);

        if(MainActivity.isLogin == false) {
            lnLogined.setVisibility(View.GONE);
        } else {
            lnLogined.setVisibility(View.VISIBLE);
        }
        btnNeedLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MenuActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });
        btnNeedSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MenuActivity.this, SignupActivity.class);
                startActivity(i);
            }
        });
    }
}