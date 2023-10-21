package com.namhaigroup.map;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.namhaigroup.map.dao.CartDAO;
import com.namhaigroup.map.system.UserInformation;

public class MenuActivity extends AppCompatActivity {
    LinearLayout lnLogined, lnNotLogin;
    RelativeLayout btnLogout, btnAlertSettings, ViewPremiumProduct, btnOrderHistory;
    Button btnNogSignIn, btnNotSignUp;
    ImageButton btnCart;
    TextView tvUsername, tvPremium;
    CartDAO cartDAO;

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
        ViewPremiumProduct = findViewById(R.id.ViewPremiumProduct);
        btnAlertSettings = findViewById(R.id.btnAlertSettings);
        btnOrderHistory = findViewById(R.id.btnOrderHistory);
        btnCart = findViewById(R.id.btnCart);
        tvPremium = findViewById(R.id.tvPremium);
        cartDAO = new CartDAO(this);

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
        ViewPremiumProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MenuActivity.this, ProductActivity.class);
                startActivity(i);
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
                UserInformation.isPremium = false;
                UserInformation.address = null;
                UserInformation.permission = 0;
                lnLogined.setVisibility(View.GONE);
                lnNotLogin.setVisibility(View.VISIBLE);
                finish();
            }
        });

        btnAlertSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MenuActivity.this, SettingAlertActivity.class);
                startActivity(i);
            }
        });

        btnOrderHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MenuActivity.this, OrderActivity.class);
                startActivity(i);
            }
        });

        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MenuActivity.this, CartActivity.class);
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
            if(UserInformation.isNotiCart == false) {
                UserInformation.isNotiCart = true;
                if(cartDAO.getCartByUsername(UserInformation.username).size() > 0) {
                    showNotification();
                }
            }
        } else {
            lnLogined.setVisibility(View.GONE);
            lnNotLogin.setVisibility(View.VISIBLE);
        }

        if(UserInformation.isPremium == true) {
            tvPremium.setText("Gói dịch vụ: Premium");
        } else {
            tvPremium.setText("Ngày hết hạn: Miễn phí");
        }
    }

    private void showNotification() {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        String channelId = "namhaimap";
        CharSequence channelName = "Nam Hải Map";
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            channel = new NotificationChannel(channelId, channelName, importance);
        }

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            notification = new Notification.Builder(this, channelId)
                    .setContentTitle("Thông báo")
                    .setContentText("Bạn đang có sản phẩm trong giỏ hàng")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setLargeIcon(bitmap)
                    .build();
        }
        int notificationId = 1;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel(channel);
        }
        notificationManager.notify(notificationId, notification);
    }
}