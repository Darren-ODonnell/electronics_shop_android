package com.example.bottomnavigationproper.Models;

public class OrderItemModel {
    Integer item_id;
    Integer quantity;

    public OrderItemModel() {
    }

    public OrderItemModel(Integer id, int i) {
        this.item_id = id;
        this.quantity = i;
    }

    public Integer getItem_id() {
        return item_id;
    }

    public void setItem_id(Integer item_id) {
        this.item_id = item_id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
