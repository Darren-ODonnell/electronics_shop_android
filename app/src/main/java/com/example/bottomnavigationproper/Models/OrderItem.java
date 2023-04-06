package com.example.bottomnavigationproper.Models;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public class OrderItem {

    private Integer id;

    @JsonIgnoreProperties("children")
    @JsonBackReference
    private Order customerOrderNo;

    private Item item;

    private Integer quantity;

    public OrderItem() {
    }

    public OrderItem(Order customerOrderNo, Item item, Integer quantity) {
        this.customerOrderNo = customerOrderNo;
        this.item = item;
        this.quantity = quantity;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Order getCustomerOrderNo() {
        return customerOrderNo;
    }

    public void setCustomerOrderNo(Order customerOrderNo) {
        this.customerOrderNo = customerOrderNo;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}