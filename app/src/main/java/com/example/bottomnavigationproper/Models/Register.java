package com.example.bottomnavigationproper.Models;

public class Register {
    private String username;

    private String password;

    private String paymentMethod;

    private String shippingAddress;

    private String passwordConfirm;

    public String getUsername() {       return username;    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirm() {        return passwordConfirm;    }

    public void setPasswordConfirm(String passwordConfirm) {        this.passwordConfirm = passwordConfirm;    }

}
