package com.namhaigroup.map;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.namhaigroup.map.adapter.OrderHistoryAdapter;
import com.namhaigroup.map.dao.OrderHistoryDAO;
import com.namhaigroup.map.object.OrderHistory;
import com.namhaigroup.map.system.UserInformation;

import java.util.ArrayList;
import java.util.List;

public class OrderHistoryActivity extends AppCompatActivity {
    List<OrderHistory> orderHistoryList;
    private RecyclerView RecyclerViewOrderHistory;
    private OrderHistoryAdapter orderHistoryAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);

        DisplayOrderHistory();
    }

    private void DisplayOrderHistory() {
        OrderHistoryDAO orderHistoryDAO = new OrderHistoryDAO(this);
        orderHistoryList = orderHistoryDAO.getAllOrderHistory();

        List<OrderHistory> historyList = new ArrayList<>();
        for (OrderHistory orderHistory : orderHistoryList) {
            if(orderHistory.getUsername().equals(UserInformation.username)) {
                historyList.add(orderHistory);
            }
        }

        RecyclerViewOrderHistory = findViewById(R.id.RecyclerViewOrderHistory);
        RecyclerViewOrderHistory.setLayoutManager(new LinearLayoutManager(this));
        orderHistoryAdapter = new OrderHistoryAdapter(historyList);
        RecyclerViewOrderHistory.setAdapter(orderHistoryAdapter);
    }
}