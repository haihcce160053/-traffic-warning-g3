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
import com.namhaigroup.map.dao.ProductsDAO;
import com.namhaigroup.map.object.Cart;
import com.namhaigroup.map.system.UserInformation;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;

public class ProductDetailActivity extends AppCompatActivity {
    CartDAO cartDAO;
    ProductsDAO productsDAO;
    Button btnRemoveProduct, btnUpdateProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        Intent intent = getIntent();
        if (intent != null) {
            String id = intent.getStringExtra("product_id");
            String name = intent.getStringExtra("product_name");
            String price = intent.getStringExtra("price");
            String quantity = intent.getStringExtra("quantity");
            String sold = intent.getStringExtra("sold");
            String description = intent.getStringExtra("description");
            String image = intent.getStringExtra("image");
            TextView tvName = findViewById(R.id.tvProductName);
            TextView tvPrice = findViewById(R.id.tvProductPriceDetail);
            TextView tvDescription = findViewById(R.id.tvProductDescription);
            ImageView imgProductDetails = findViewById(R.id.imgProductDetails);
            btnRemoveProduct = findViewById(R.id.btnRemoveProduct);
            btnUpdateProduct = findViewById(R.id.btnUpdateProduct);

            if(UserInformation.isLogin == true) {
                if(UserInformation.permission != 1) {
                    btnRemoveProduct.setVisibility(View.GONE);
                    btnUpdateProduct.setVisibility(View.GONE);
                } else {
                    btnRemoveProduct.setVisibility(View.VISIBLE);
                    btnUpdateProduct.setVisibility(View.VISIBLE);
                }
            }

            tvName.setText(name);
            tvPrice.setText("Giá: " + formatCurrency(Double.parseDouble(price)));
            tvDescription.setText(description);
            Picasso.get().load(image).into(imgProductDetails);

            cartDAO = new CartDAO(this);
            productsDAO = new ProductsDAO(this);
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

            btnRemoveProduct.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(productsDAO.deleteProduct(Integer.parseInt(id))) {
                        try {
                            productsDAO.deleteCart(Integer.parseInt(id));
                        } catch (Exception ex) {

                        }
                        Toast.makeText(ProductDetailActivity.this, "Xoá sản phẩm thành công", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ProductDetailActivity.this, ProductActivity.class);
                        setResult(RESULT_OK, intent);
                        finish();
                    } else {
                        Toast.makeText(ProductDetailActivity.this, "Xoá sản phẩm thất bại!", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            btnUpdateProduct.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ProductDetailActivity.this, ProductManagerActivity.class);
                    intent.putExtra("source", "ProductDetailActivity");
                    intent.putExtra("product_id", id);
                    intent.putExtra("product_name", name);
                    intent.putExtra("price", price);
                    intent.putExtra("quantity", quantity);
                    intent.putExtra("sold", sold);
                    intent.putExtra("description", description);
                    intent.putExtra("image", image);
                    startActivity(intent);
                }
            });
        }
    }

    private String formatCurrency(double amount) {
        Locale vietnameseLocale = new Locale("vi", "VN");
        Currency vietnameseCurrency = Currency.getInstance(vietnameseLocale);
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(vietnameseLocale);
        currencyFormatter.setCurrency(vietnameseCurrency);
        return currencyFormatter.format(amount);
    }
}