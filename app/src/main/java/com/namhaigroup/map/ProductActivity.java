package com.namhaigroup.map;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.namhaigroup.map.adapter.ProductAdapter;
import com.namhaigroup.map.dao.ProductsDAO;
import com.namhaigroup.map.object.Products;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

public class ProductActivity extends AppCompatActivity {
    private RecyclerView recyclerViewPremiumProduct, recyclerViewOtherProduct;
    private ProductAdapter premiumProductAdapter, otherProductAdapter;
    List<Products> productList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        DisplayProduct();
        itemClickProduct();
    }

    public void itemClickProduct() {
        premiumProductAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(Products product) {
                Intent intent = new Intent(ProductActivity.this, ProductDetailActivity.class);
                intent.putExtra("name", product.getName());
                intent.putExtra("price", String.valueOf(formatCurrency(product.getPrice())));
                intent.putExtra("description", product.getDescription());
                intent.putExtra("image", product.getImage());
                startActivity(intent);
            }
        });

        otherProductAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(Products product) {
                Intent intent = new Intent(ProductActivity.this, ProductDetailActivity.class);
                intent.putExtra("name", product.getName());
                intent.putExtra("price", String.valueOf(formatCurrency(product.getPrice())));
                intent.putExtra("description", product.getDescription());
                intent.putExtra("image", product.getImage());
                startActivity(intent);
            }
        });
    }

    private void DisplayProduct() {
        ProductsDAO productsDAO = new ProductsDAO(this);
        productList = productsDAO.displayProducts();

        List<Products> premiumProducts = new ArrayList<>();
        List<Products> otherProducts = new ArrayList<>();

        for (Products product : productList) {
            if (product.getType() >= 5) {
                otherProducts.add(product);
            } else {
                premiumProducts.add(product);
            }
        }

        recyclerViewPremiumProduct = findViewById(R.id.RecyclerViewPremiumProduct);
        recyclerViewPremiumProduct.setLayoutManager(new LinearLayoutManager(this));
        premiumProductAdapter = new ProductAdapter(premiumProducts);
        recyclerViewPremiumProduct.setAdapter(premiumProductAdapter);

        recyclerViewOtherProduct = findViewById(R.id.RecyclerViewOtherProduct);
        recyclerViewOtherProduct.setLayoutManager(new LinearLayoutManager(this));
        otherProductAdapter = new ProductAdapter(otherProducts);
        recyclerViewOtherProduct.setAdapter(otherProductAdapter);
    }

    private String formatCurrency(double amount) {
        Locale vietnameseLocale = new Locale("vi", "VN");
        Currency vietnameseCurrency = Currency.getInstance(vietnameseLocale);
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(vietnameseLocale);
        currencyFormatter.setCurrency(vietnameseCurrency);
        return currencyFormatter.format(amount);
    }
}