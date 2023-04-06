package com.example.bottomnavigationproper;

import com.example.bottomnavigationproper.Models.ItemReview;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;

public class User {
    private Integer id;
    private String accessToken;
    private final String tokenType = "Bearer";
    private String username;
    private String password;
    private String shippingAddress;
    private String paymentMethod;
    private List<String> roles = new ArrayList<>();

    @JsonBackReference
    private List<ItemReview> reviews;


    public User(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType(){
        return this.tokenType;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public List<ItemReview> getReviews() {
        return reviews;
    }

    public void setReviews(List<ItemReview> reviews) {
        this.reviews = reviews;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

