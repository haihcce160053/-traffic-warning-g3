package com.namhaigroup.map.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.namhaigroup.map.R;
import com.namhaigroup.map.dao.OrderDAO;
import com.namhaigroup.map.object.Orders;

import java.text.NumberFormat;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderHistoryViewHolder> {
    private List<Orders> orderHistoryList;
    private OrderDAO orderDAO;
    public OrderAdapter(List<Orders> orderHistoryList, Context context) {
        this.orderHistoryList = orderHistoryList;
        orderDAO = new OrderDAO(context);
    }
    @NonNull
    @Override
    public OrderHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.orders_history, parent, false);
        return new OrderHistoryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderAdapter.OrderHistoryViewHolder holder, int position) {
        Orders orderHistory = orderHistoryList.get(position);
        holder.tvProductNameHistory.setText("Sản phẩm: " + orderDAO.getOrderById(orderHistory.getId()).getProducts().getName());
        holder.tvOrderDate.setText("Ngày đặt đơn: " + orderDAO.getOrderById(orderHistory.getId()).getOrder_date());
        holder.tvProductPriceHistory.setText("Giá: " + formatCurrency(orderDAO.getOrderById(orderHistory.getId()).getProducts().getPrice()));
        holder.tvProductCartQuantity.setText("Số lượng: " + String.valueOf(orderDAO.getOrderById(orderHistory.getId()).getQuantity()));
        holder.tvOrderStatus.setText("Trạng thái: " + orderDAO.getOrderStatusById(orderHistory.getStatus()).getName());
    }

    @Override
    public int getItemCount() {
        return orderHistoryList.size();
    }

    public class OrderHistoryViewHolder extends RecyclerView.ViewHolder {
        TextView tvProductNameHistory, tvOrderDate, tvProductPriceHistory, tvProductCartQuantity, tvOrderStatus;

        public OrderHistoryViewHolder(View itemView) {
            super(itemView);
            tvProductNameHistory = itemView.findViewById(R.id.tvProductNameHistory);
            tvOrderDate = itemView.findViewById(R.id.tvOrderDate);
            tvProductPriceHistory = itemView.findViewById(R.id.tvProductPriceHistory);
            tvProductCartQuantity = itemView.findViewById(R.id.tvProductCartQuantity);
            tvOrderStatus = itemView.findViewById(R.id.tvOrderStatus);
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
