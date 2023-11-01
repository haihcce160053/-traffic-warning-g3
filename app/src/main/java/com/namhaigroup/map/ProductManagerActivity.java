package com.namhaigroup.map;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.namhaigroup.map.dao.ProductsDAO;
import com.namhaigroup.map.object.Products;

public class ProductManagerActivity extends AppCompatActivity {
    EditText edtProductTitle, edtProductPrice, edtProductQuantity, edtDescription, edtProductImageLink;
    RadioButton rdCamera, rdPremium1d, rdPremium2d, rdPremium3d, rdPremium1m;
    Button btnAddProduct;
    ProductsDAO productsDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_manager);

        edtProductTitle = findViewById(R.id.edtProductTitle);
        edtProductPrice = findViewById(R.id.edtProductPrice);
        edtProductQuantity = findViewById(R.id.edtProductQuantity);
        edtDescription = findViewById(R.id.edtDescription);
        edtProductImageLink = findViewById(R.id.edtProductImageLink);

        rdCamera = findViewById(R.id.rdCamera);
        rdPremium1d = findViewById(R.id.rdPremium1d);
        rdPremium2d = findViewById(R.id.rdPremium2d);
        rdPremium3d = findViewById(R.id.rdPremium3d);
        rdPremium1m = findViewById(R.id.rdPremium1m);

        btnAddProduct = findViewById(R.id.btnAddProduct);

        productsDAO = new ProductsDAO(this);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("source")) {
            String source = intent.getStringExtra("source");
            if ("ProductDetailActivity".equals(source)) {
                String productID = intent.getStringExtra("product_id");
                String productName = intent.getStringExtra("product_name");
                String price = intent.getStringExtra("price");
                String quantity = intent.getStringExtra("quantity");
                String sold = intent.getStringExtra("sold");
                String description = intent.getStringExtra("description");
                String image = intent.getStringExtra("image");

                edtProductTitle.setText(productName);
                edtProductPrice.setText(price);
                edtProductQuantity.setText(quantity);
                edtDescription.setText(description);
                edtProductImageLink.setText(image);

                btnAddProduct.setText("Cập nhật");

                btnAddProduct.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            Double var = Double.parseDouble(edtProductPrice.getText().toString().trim());
                            if (var <= 0) {
                                Toast.makeText(ProductManagerActivity.this, "Giá nhập vào không hợp lệ", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (NumberFormatException e) {
                            Toast.makeText(ProductManagerActivity.this, "Giá nhập vào không hợp lệ", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        try {
                            Integer var = Integer.parseInt(edtProductQuantity.getText().toString().trim());
                            if (var <= 0) {
                                Toast.makeText(ProductManagerActivity.this, "Số lượng nhập vào không hợp lệ", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (NumberFormatException e) {
                            Toast.makeText(ProductManagerActivity.this, "Số lượng nhập vào không hợp lệ", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        String name = edtProductTitle.getText().toString().trim();
                        Double price = Double.parseDouble(edtProductPrice.getText().toString().trim());
                        int quantity = Integer.parseInt(edtProductQuantity.getText().toString().trim());
                        String description = edtDescription.getText().toString().trim();
                        String image = edtProductImageLink.getText().toString().trim();
                        if (name.length() > 0 && description.length() > 0 && image.length() > 0) {
                            Products products = new Products();
                            products.setId(Integer.parseInt(productID));
                            products.setName(name);
                            products.setPrice(price);
                            products.setQuantity(quantity);
                            products.setImage(image);
                            products.setSold(Integer.parseInt(sold));
                            products.setDescription(description);
                            if (rdPremium1d.isChecked()) {
                                products.setType(1);
                            }
                            if (rdPremium2d.isChecked()) {
                                products.setType(2);
                            }
                            if (rdPremium3d.isChecked()) {
                                products.setType(3);
                            }
                            if (rdPremium1m.isChecked()) {
                                products.setType(4);
                            }
                            if (rdCamera.isChecked()) {
                                products.setType(5);
                            }
                            productsDAO.updateProduct(products);
                            Toast.makeText(ProductManagerActivity.this, "Cập nhật sản phẩm thành công", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                });
            }
        } else {
            btnAddProduct.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        Double var = Double.parseDouble(edtProductPrice.getText().toString().trim());
                        if (var <= 0) {
                            Toast.makeText(ProductManagerActivity.this, "Giá nhập vào không hợp lệ", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    } catch (NumberFormatException e) {
                        Toast.makeText(ProductManagerActivity.this, "Giá nhập vào không hợp lệ", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    try {
                        Integer var = Integer.parseInt(edtProductQuantity.getText().toString().trim());
                        if (var <= 0) {
                            Toast.makeText(ProductManagerActivity.this, "Số lượng nhập vào không hợp lệ", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    } catch (NumberFormatException e) {
                        Toast.makeText(ProductManagerActivity.this, "Số lượng nhập vào không hợp lệ", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    String name = edtProductTitle.getText().toString().trim();
                    Double price = Double.parseDouble(edtProductPrice.getText().toString().trim());
                    int quantity = Integer.parseInt(edtProductQuantity.getText().toString().trim());
                    String description = edtDescription.getText().toString().trim();
                    String image = edtProductImageLink.getText().toString().trim();

                    if (name.length() > 0 && description.length() > 0 && image.length() > 0) {
                        Products products = new Products();
                        products.setName(name);
                        products.setPrice(price);
                        products.setQuantity(quantity);
                        products.setImage(image);
                        products.setSold(0);
                        products.setDescription(description);
                        if (rdPremium1d.isChecked()) {
                            products.setType(1);
                        }
                        if (rdPremium2d.isChecked()) {
                            products.setType(2);
                        }
                        if (rdPremium3d.isChecked()) {
                            products.setType(3);
                        }
                        if (rdPremium1m.isChecked()) {
                            products.setType(4);
                        }
                        if (rdCamera.isChecked()) {
                            products.setType(5);
                        }
                        productsDAO.addProduct(products);
                        Toast.makeText(ProductManagerActivity.this, "Thêm sản phẩm thành công", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(ProductManagerActivity.this, "Vui lòng kiểm tra lại thông tin", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}