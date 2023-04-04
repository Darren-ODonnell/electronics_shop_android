package com.example.bottomnavigationproper;

import com.example.bottomnavigationproper.Models.Player;

public class UserSingleton {

    private static UserSingleton instance;
    private User user;

    private UserSingleton(){

    }

    public static UserSingleton getInstance(){
        instance = (instance == null) ? new UserSingleton(): instance;
        return instance;
    }

    public void setUser(User user){
        this.user = user;
    }

    public User getUser() {
        return user;
    }


    public boolean isAdmin() {
        return user.getRoles().contains("ROLE_ADMIN") ||
                user.getRoles().contains("ROLE_COACH");
    }
}
