package com.namhaigroup.map;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.namhaigroup.map.dao.CartDAO;
import com.namhaigroup.map.object.Cart;
import com.namhaigroup.map.system.UserInformation;
import com.squareup.picasso.Picasso;

public class ProductDetailActivity extends AppCompatActivity {
    CartDAO cartDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        Intent intent = getIntent();
        if (intent != null) {
            String id = intent.getStringExtra("product_id");
            String name = intent.getStringExtra("product_name");
            String price = intent.getStringExtra("price");
            String description = intent.getStringExtra("description");
            String image = intent.getStringExtra("image");
            TextView tvName = findViewById(R.id.tvProductName);
            TextView tvPrice = findViewById(R.id.tvProductPriceDetail);
            TextView tvDescription = findViewById(R.id.tvProductDescription);
            ImageView imgProductDetails = findViewById(R.id.imgProductDetails);

            tvName.setText(name);
            tvPrice.setText("Giá: " + price);
            tvDescription.setText(description);
            Picasso.get().load(image).into(imgProductDetails);

            cartDAO = new CartDAO(this);
            Button btnAddToCart = findViewById(R.id.btnAddToCart);
            btnAddToCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(UserInformation.isLogin == true) {
                        Cart cart = new Cart();
                        cart.setUsername(UserInformation.username);
                        cart.setProduct_id(Integer.parseInt(String.valueOf(id)));
                        cart.setQuantity(1);
                        if(cartDAO.addCart(cart) != null) {
                            Toast.makeText(ProductDetailActivity.this, "Thêm vào giỏ hàng thành công ID: " + id, Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(ProductDetailActivity.this, "Thêm vào giỏ hàng thất bại: " + id, Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(ProductDetailActivity.this, "Vui lòng đăng nhập để đặt đơn hàng", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(ProductDetailActivity.this, LoginActivity.class);
                        startActivity(i);
                    }
                }
            });
        }
    }
}