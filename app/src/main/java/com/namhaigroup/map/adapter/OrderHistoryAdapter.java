package com.namhaigroup.map.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.namhaigroup.map.R;
import com.namhaigroup.map.object.OrderHistory;

import java.text.NumberFormat;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryAdapter.OrderHistoryViewHolder> {
    private List<OrderHistory> orderHistoryList;
    public OrderHistoryAdapter(List<OrderHistory> orderHistoryList) {
        this.orderHistoryList = orderHistoryList;
    }
    @NonNull
    @Override
    public OrderHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.orders_history, parent, false);
        return new OrderHistoryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderHistoryAdapter.OrderHistoryViewHolder holder, int position) {
        OrderHistory orderHistory = orderHistoryList.get(position);
        holder.tvProductNameHistory.setText("Sản phẩm: " + orderHistory.getProduct_name());
        holder.tvOrderDate.setText("Ngày thanh toán: " + orderHistory.getOrder_date());
        holder.tvProductPriceHistory.setText("Giá: " + formatCurrency(orderHistory.getOrder_price()));
    }

    @Override
    public int getItemCount() {
        return orderHistoryList.size();
    }

    public class OrderHistoryViewHolder extends RecyclerView.ViewHolder {
        TextView tvProductNameHistory, tvOrderDate, tvProductPriceHistory;

        public OrderHistoryViewHolder(View itemView) {
            super(itemView);
            tvProductNameHistory = itemView.findViewById(R.id.tvProductNameHistory);
            tvOrderDate = itemView.findViewById(R.id.tvOrderDate);
            tvProductPriceHistory = itemView.findViewById(R.id.tvProductPriceHistory);
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
