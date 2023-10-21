package com.namhaigroup.map;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.namhaigroup.map.adapter.OrderAdapter;
import com.namhaigroup.map.dao.OrderDAO;
import com.namhaigroup.map.object.Orders;
import com.namhaigroup.map.system.UserInformation;

import java.util.ArrayList;
import java.util.List;

public class OrderActivity extends AppCompatActivity {
    List<Orders> orderHistoryList;
    private RecyclerView RecyclerViewOrderHistory;
    private OrderAdapter orderHistoryAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);

        DisplayOrderHistory();
    }

    private void DisplayOrderHistory() {
        OrderDAO orderDAO = new OrderDAO(this);
        orderHistoryList = orderDAO.getOrderByUsername(UserInformation.username);

        List<Orders> historyList = new ArrayList<>();
        for (Orders orderHistory : orderHistoryList) {
            if(orderHistory.getUsername().equals(UserInformation.username)) {
                historyList.add(orderHistory);
            }
        }

        RecyclerViewOrderHistory = findViewById(R.id.RecyclerViewOrderHistory);
        RecyclerViewOrderHistory.setLayoutManager(new LinearLayoutManager(this));
        orderHistoryAdapter = new OrderAdapter(historyList, this);
        RecyclerViewOrderHistory.setAdapter(orderHistoryAdapter);
    }
}