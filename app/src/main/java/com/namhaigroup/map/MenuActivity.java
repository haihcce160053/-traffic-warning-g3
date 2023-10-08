package com.namhaigroup.map;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class MenuActivity extends AppCompatActivity {
    LinearLayout lnLogined, lnNotLogin;
    Button btnNogSignIn, btnNotSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        lnLogined = findViewById(R.id.lnLogined);
        lnNotLogin = findViewById(R.id.lnNotLogin);
        btnNogSignIn = findViewById(R.id.btnNogSignIn);
        btnNotSignUp = findViewById(R.id.btnNotSignUp);

        btnNogSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
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
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(UserInformation.isLogin == true) {
            lnLogined.setVisibility(View.VISIBLE);
            lnNotLogin.setVisibility(View.GONE);
        } else {
            lnLogined.setVisibility(View.GONE);
            lnNotLogin.setVisibility(View.VISIBLE);
        }
    }
}