package com.namhaigroup.map;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.namhaigroup.map.adapter.CartAdapter;
import com.namhaigroup.map.dao.CartDAO;
import com.namhaigroup.map.object.Cart;
import com.namhaigroup.map.system.UserInformation;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

public class CartActivity extends AppCompatActivity {
    RecyclerView cartView;
    private CartAdapter cartAdapter;
    List<Cart> cartList;
    TextView tvTotalPrice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        DisplayProduct();
    }

    private void DisplayProduct() {
        CartDAO cartDAO = new CartDAO(this);
        cartList = cartDAO.getCartByUsername(UserInformation.username);
        List<Cart> listOfCart = new ArrayList<>();
        double totalPrice = 0;
        for (Cart cart : cartList) {
            totalPrice = totalPrice + cart.getProducts().getPrice();
            listOfCart.add(cart);
        }
        cartView = findViewById(R.id.cartView);
        cartView.setLayoutManager(new LinearLayoutManager(this));
        cartAdapter = new CartAdapter(listOfCart, this);
        cartView.setAdapter(cartAdapter);

        tvTotalPrice = findViewById(R.id.tvTotalPrice);
        tvTotalPrice.setText(formatCurrency(totalPrice));
    }

    private String formatCurrency(double amount) {
        Locale vietnameseLocale = new Locale("vi", "VN");
        Currency vietnameseCurrency = Currency.getInstance(vietnameseLocale);
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(vietnameseLocale);
        currencyFormatter.setCurrency(vietnameseCurrency);
        return currencyFormatter.format(amount);
    }
}