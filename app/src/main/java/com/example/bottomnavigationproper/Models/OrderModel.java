package com.example.bottomnavigationproper.Models;

import java.util.ArrayList;
import java.util.List;

public class OrderModel {
    int customer_id;
    List<OrderItemModel> orderItemModels = new ArrayList<>();

    public OrderModel() {
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public List<OrderItemModel> getOrderItemModels() {
        return orderItemModels;
    }

    public void setOrderItemModels(List<OrderItemModel> orderItemModels) {
        this.orderItemModels = orderItemModels;
    }
}
