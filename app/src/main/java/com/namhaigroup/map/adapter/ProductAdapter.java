package com.namhaigroup.map.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.namhaigroup.map.OnItemClickListener;
import com.namhaigroup.map.R;
import com.namhaigroup.map.object.Products;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    private List<Products> productList;
    public ProductAdapter(List<Products> productList) {
        this.productList = productList;
    }
    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_data, parent, false);
        return new ProductViewHolder(itemView);
    }

    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ProductViewHolder holder, int position) {
        Products product = productList.get(position);
        holder.productName.setText(product.getName());
        holder.productDescription.setText(product.getDescription());
        holder.productPrice.setText(formatCurrency(product.getPrice()));
        Picasso.get().load(product.getImage()).into(holder.productImage);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener != null) {
                    listener.onItemClick(product);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView productName, productDescription, productPrice;
        ImageView productImage;

        public ProductViewHolder(View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.tvProductName);
            productDescription = itemView.findViewById(R.id.tvProductDescription);
            productPrice = itemView.findViewById(R.id.tvProductPrice);
            productImage = itemView.findViewById(R.id.imageProduct);
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
