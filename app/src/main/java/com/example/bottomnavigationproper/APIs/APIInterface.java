package com.example.bottomnavigationproper.APIs;

import com.example.bottomnavigationproper.Models.Item;
import com.example.bottomnavigationproper.Models.ItemModel;
import com.example.bottomnavigationproper.Models.Login;
import com.example.bottomnavigationproper.Models.Order;
import com.example.bottomnavigationproper.Models.OrderModel;
import com.example.bottomnavigationproper.Models.Player;

import com.example.bottomnavigationproper.Models.StatModel;
import com.example.bottomnavigationproper.Models.Register;
import com.example.bottomnavigationproper.User;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface APIInterface {

    @POST("api/auth/login")
    Call<User> login(@Body Login login);

    @POST("api/auth/register")
    Call<User> register(@Body Register register);

    @GET("api/auth/checkToken")
    Call<Boolean> checkToken(@Query("token") String token);

    @GET("player/list")
    Call<List<Player>> getPlayers(@Header("Authorization") String accessToken);


    // NEW
    @GET("items/list")
    Call<List<Item>> getItems(@Header("Authorization") String accessToken);

    @GET("orders/list")
    Call<List<Order>> getCustomerOrders(@Header("Authorization") String accessToken);

    @POST("items/update")
    Call<List<Order>> updateItem(@Header("Authorization") String accessToken, @Body Item item);

    @POST("orders/add")
    Call<Order> addOrder(@Header("Authorization") String accessToken, @Body OrderModel orderModel);
}
