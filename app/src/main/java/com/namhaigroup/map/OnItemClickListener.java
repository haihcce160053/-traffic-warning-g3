package com.namhaigroup.map;

import com.namhaigroup.map.object.Orders;
import com.namhaigroup.map.object.Products;

public interface OnItemClickListener {
    void onItemProductClick(Products product);
    void onItemOrdersManagerClick(Orders orders);
}
