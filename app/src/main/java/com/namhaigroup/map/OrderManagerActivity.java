package com.namhaigroup.map;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.namhaigroup.map.adapter.OrderManagerAdapter;
import com.namhaigroup.map.dao.OrderDAO;
import com.namhaigroup.map.object.Orders;
import com.namhaigroup.map.object.Products;

import java.util.ArrayList;
import java.util.List;

public class OrderManagerActivity extends AppCompatActivity implements OnItemClickListener {
    List<Orders> ordersList;
    private RecyclerView RecyclerViewOrderManager;
    private OrderManagerAdapter OrderManagerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_manager);

        DisplayOrderHistory();
        itemClickProduct();
    }

    private void DisplayOrderHistory() {
        OrderDAO orderDAO = new OrderDAO(this);
        ordersList = orderDAO.getAllOrders();

        List<Orders> historyList = new ArrayList<>();
        for (Orders orderHistory : ordersList) {
            historyList.add(orderHistory);
        }

        RecyclerViewOrderManager = findViewById(R.id.RecyclerViewOrderManager);
        RecyclerViewOrderManager.setLayoutManager(new LinearLayoutManager(this));
        OrderManagerAdapter = new OrderManagerAdapter(historyList, this);
        RecyclerViewOrderManager.setAdapter(OrderManagerAdapter);
    }

    @Override
    public void onItemProductClick(Products product) {

    }

    @Override
    public void onItemOrdersManagerClick(Orders orders) {

    }

    public void itemClickProduct() {
        OrderManagerAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemProductClick(Products product) {

            }

            @Override
            public void onItemOrdersManagerClick(Orders orders) {
                OrderDAO orderDAO = new OrderDAO(OrderManagerActivity.this);
                if(orderDAO.updateOrder(orders)) {
                    Toast.makeText(OrderManagerActivity.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}