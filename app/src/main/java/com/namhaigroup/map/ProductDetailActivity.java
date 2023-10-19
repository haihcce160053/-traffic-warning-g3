package com.namhaigroup.map;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

public class ProductDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        Intent intent = getIntent();
        if (intent != null) {
            String name = intent.getStringExtra("name");
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
        }
    }
}