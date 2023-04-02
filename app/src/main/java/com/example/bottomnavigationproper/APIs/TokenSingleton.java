package com.example.bottomnavigationproper.APIs;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.bottomnavigationproper.R;

public class TokenSingleton {

    private static TokenSingleton tokenInstance;
    private String tokenStr;
    private String bearerTokenStr;

    private TokenSingleton(){

    }

    public static TokenSingleton getInstance(){

        tokenInstance = (tokenInstance == null) ? new TokenSingleton(): tokenInstance;
        return  tokenInstance;
    }

    public void setTokenWithBearerString(String tokenStr){
        this.bearerTokenStr = "Bearer " + tokenStr;
    }

    public String getBearerTokenString(){
        return bearerTokenStr;
    }

    public String getTokenStr(){
        return tokenStr;
    }

    public void setTokenString(String token) {
        tokenStr = token;
        setTokenWithBearerString(tokenStr);
    }
}
