package com.namhaigroup.map;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.namhaigroup.map.adapter.ProductAdapter;
import com.namhaigroup.map.dao.ProductsDAO;
import com.namhaigroup.map.object.Orders;
import com.namhaigroup.map.object.Products;

import java.util.ArrayList;
import java.util.List;

public class ProductActivity extends AppCompatActivity {
    private RecyclerView recyclerViewPremiumProduct, recyclerViewOtherProduct;
    private ProductAdapter premiumProductAdapter, otherProductAdapter;
    List<Products> productList;
    ProductsDAO productsDAO;
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
            public void onItemProductClick(Products product) {
                Intent intent = new Intent(ProductActivity.this, ProductDetailActivity.class);
                intent.putExtra("product_id", String.valueOf(product.getId()));
                intent.putExtra("product_name", product.getName());
                intent.putExtra("price", String.valueOf(product.getPrice()));
                intent.putExtra("quantity", String.valueOf(product.getQuantity()));
                intent.putExtra("sold", String.valueOf(product.getSold()));
                intent.putExtra("description", product.getDescription());
                intent.putExtra("image", product.getImage());
                startActivityForResult(intent, 1);
            }

            @Override
            public void onItemOrdersManagerClick(Orders orders) {

            }
        });

        otherProductAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemProductClick(Products product) {
                Intent intent = new Intent(ProductActivity.this, ProductDetailActivity.class);
                intent.putExtra("product_id", String.valueOf(product.getId()));
                intent.putExtra("product_name", product.getName());
                intent.putExtra("price", String.valueOf(product.getPrice()));
                intent.putExtra("quantity", String.valueOf(product.getQuantity()));
                intent.putExtra("sold", String.valueOf(product.getSold()));
                intent.putExtra("description", product.getDescription());
                intent.putExtra("image", product.getImage());
                startActivityForResult(intent, 2);
            }

            @Override
            public void onItemOrdersManagerClick(Orders orders) {

            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                DisplayProduct();
            }
        } else if (requestCode == 2) {
            if (resultCode == RESULT_OK) {
                DisplayProduct();
            }
        }
    }
    private void DisplayProduct() {
        productsDAO = new ProductsDAO(this);
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

    @Override
    protected void onResume() {
        super.onResume();
    }
}