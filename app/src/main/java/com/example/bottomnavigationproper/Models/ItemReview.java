package com.example.bottomnavigationproper.Models;

import com.example.bottomnavigationproper.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

public class ItemReview {
    private Integer item_review_id;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JsonManagedReference
    private User user;

//    @JsonIgnoreProperties("children")
//    @JsonBackReference
//    private Item item;

    private Integer rating;

    private String comment;

    public Integer getId() {
        return item_review_id;
    }

    public void setId(Integer id) {
        this.item_review_id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

//    public Item getItem() {
//        return item;
//    }
//
//    public void setItem(Item item) {
//        this.item = item;
//    }

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