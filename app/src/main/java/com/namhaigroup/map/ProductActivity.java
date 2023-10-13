package com.namhaigroup.map;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.namhaigroup.map.adapter.ProductAdapter;
import com.namhaigroup.map.dao.ProductsDAO;
import com.namhaigroup.map.object.Products;

import java.util.ArrayList;
import java.util.List;

public class ProductActivity extends AppCompatActivity {
    private RecyclerView recyclerViewPremiumProduct, recyclerViewOtherProduct;
    private ProductAdapter premiumProductAdapter, otherProductAdapter;
    List<Products> productList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        DisplayProduct();
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
}