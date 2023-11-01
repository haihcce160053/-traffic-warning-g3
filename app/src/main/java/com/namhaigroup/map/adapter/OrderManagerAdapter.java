package com.namhaigroup.map.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.namhaigroup.map.OnItemClickListener;
import com.namhaigroup.map.R;
import com.namhaigroup.map.dao.OrderDAO;
import com.namhaigroup.map.object.Orders;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

public class OrderManagerAdapter extends RecyclerView.Adapter<OrderManagerAdapter.OrderHistoryViewHolder> {
    private List<Orders> orderHistoryList;
    private OrderDAO orderDAO;
    private OnItemClickListener listener;
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
    public OrderManagerAdapter(List<Orders> orderHistoryList, Context context) {
        this.orderHistoryList = orderHistoryList;
        orderDAO = new OrderDAO(context);
    }
    @NonNull
    @Override
    public OrderHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.orders_manager, parent, false);
        return new OrderHistoryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderManagerAdapter.OrderHistoryViewHolder holder, int position) {
        Orders orders = orderHistoryList.get(position);
        holder.tvOrderManagerUsername.setText("Người dùng: " + orders.getUsername());
        holder.tvProductNameHistory.setText("Sản phẩm: " + orders.getProducts().getName());
        holder.tvOrderDate.setText("Ngày đặt đơn: " + orders.getOrder_date());
        holder.tvProductPriceHistory.setText("Giá: " + formatCurrency(orders.getTotal_price()));
        holder.tvProductCartQuantity.setText("Số lượng: " + String.valueOf(orders.getQuantity()));
        holder.tvOrderStatus.setText("Trạng thái: " + orders.getOrders_status().getName());

        ArrayList<String> itemList = new ArrayList<>();
        itemList.add("Đang xác nhận");
        itemList.add("Đã xác nhận");
        itemList.add("Đang vận chuyển");
        itemList.add("Giao hàng thành công");
        itemList.add("Thanh toán thành công");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(holder.itemView.getContext(), android.R.layout.simple_spinner_item, itemList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holder.orderManagerSpinner.setAdapter(adapter);
        int selectedPosition = itemList.indexOf(orders.getOrders_status().getName());
        holder.orderManagerSpinner.setSelection(selectedPosition);
        holder.orderManagerSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                orders.setStatus(position + 1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }
        });

        holder.btnUpdateOrdersInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener != null) {
                    listener.onItemOrdersManagerClick(orders);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return orderHistoryList.size();
    }

    public class OrderHistoryViewHolder extends RecyclerView.ViewHolder {
        TextView tvOrderManagerUsername, tvProductNameHistory, tvOrderDate, tvProductPriceHistory, tvProductCartQuantity, tvOrderStatus;
        Spinner orderManagerSpinner;
        Button btnUpdateOrdersInfo;

        public OrderHistoryViewHolder(View itemView) {
            super(itemView);
            tvOrderManagerUsername = itemView.findViewById(R.id.tvOrderManagerUsername);
            tvProductNameHistory = itemView.findViewById(R.id.tvProductNameHistory);
            tvOrderDate = itemView.findViewById(R.id.tvOrderDate);
            tvProductPriceHistory = itemView.findViewById(R.id.tvProductPriceHistory);
            tvProductCartQuantity = itemView.findViewById(R.id.tvProductCartQuantity);
            tvOrderStatus = itemView.findViewById(R.id.tvOrderStatus);
            orderManagerSpinner = itemView.findViewById(R.id.orderManagerSpinner);
            btnUpdateOrdersInfo = itemView.findViewById(R.id.btnUpdateOrdersInfo);
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
