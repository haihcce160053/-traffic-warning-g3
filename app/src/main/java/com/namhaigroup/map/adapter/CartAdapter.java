package com.namhaigroup.map.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.namhaigroup.map.R;
import com.namhaigroup.map.dao.CartDAO;
import com.namhaigroup.map.database.DatabaseHelper;
import com.namhaigroup.map.object.Cart;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    private CartDAO cartDAO;
    private Context context;
    private DatabaseHelper databaseHelper;
    private List<Cart> cartList;

    public CartAdapter(List<Cart> cartList, Context context) {
        this.cartList = cartList;
        this.context = context;
        databaseHelper = new DatabaseHelper(context);
        cartDAO = new CartDAO(context);
    }
    @NonNull
    @Override
    public CartAdapter.CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cart_data, parent, false);
        return new CartAdapter.CartViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.CartViewHolder holder, int position) {
        Cart cart = cartList.get(position);

        holder.tvcartProductname.setText(cartDAO.getCartById(cart.getProduct_id()).getProducts().getName());
        holder.tvcartProductPrice.setText(String.valueOf(formatCurrency(cartDAO.getCartById(cart.getProduct_id()).getProducts().getPrice())));
        holder.tvcartProductQuantity.setText(String.valueOf("Số lượng: " + cartDAO.getCartById(cart.getProduct_id()).getQuantity()));
        Picasso.get().load(cartDAO.getCartById(cart.getProduct_id()).getProducts().getImage()).into(holder.cartImageProduct);
        Log.d("Hello", cartDAO.getCartById(cart.getProduct_id()).getProducts().getImage());
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {
        TextView tvcartProductname, tvcartProductPrice, tvcartProductQuantity;
        ImageView cartImageProduct;

        public CartViewHolder(View itemView) {
            super(itemView);
            tvcartProductname = itemView.findViewById(R.id.tvcartProductname);
            tvcartProductPrice = itemView.findViewById(R.id.tvcartProductPrice);
            tvcartProductQuantity = itemView.findViewById(R.id.tvcartProductQuantity);
            cartImageProduct = itemView.findViewById(R.id.cartImageProduct);
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
