package com.example.bottomnavigationproper.Models;

import com.example.bottomnavigationproper.User;

public class ItemReviewModel {
        private User user;
        private Item item;
        private Integer rating;
        private String comment;

    public ItemReviewModel() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
