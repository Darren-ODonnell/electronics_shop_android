package com.example.bottomnavigationproper.Services;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.bottomnavigationproper.APIs.APIClient;
import com.example.bottomnavigationproper.APIs.APIInterface;
import com.example.bottomnavigationproper.Models.Item;
import com.example.bottomnavigationproper.Models.Order;
import com.example.bottomnavigationproper.Models.OrderModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderRepository {

    private APIInterface apiInterface;
    private MutableLiveData<List<Order>> orderResponseLiveData;
    private  MutableLiveData<Order> singleOrderLiveData;


    public OrderRepository(){
        orderResponseLiveData = new MutableLiveData<>();
        singleOrderLiveData = new MutableLiveData<>();

        apiInterface = APIClient.getClient().create(APIInterface.class);
    }

    public void getCustomerOrders(String token){
        apiInterface.getCustomerOrders(token)
                .enqueue(new Callback<List<Order>>() {
                    @Override
                    public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
                        if(response.body() != null)
                            orderResponseLiveData.postValue(response.body());

                    }

                    @Override
                    public void onFailure(Call<List<Order>> call, Throwable t) {
                        orderResponseLiveData.postValue(null);
                    }
                });

    }
    public LiveData<List<Order>> getOrderResponseLiveData() {
        return orderResponseLiveData;
    }


    public void add(String token, OrderModel orderModel) {
        apiInterface.addOrder(token, orderModel)
                .enqueue(new Callback<Order>() {
                    @Override
                    public void onResponse(Call<Order> call, Response<Order> response) {
                        if(response.body() != null)
                            singleOrderLiveData.postValue(response.body());

                    }

                    @Override
                    public void onFailure(Call<Order> call, Throwable t) {
                        singleOrderLiveData.postValue(null);
                    }
                });
    }

    public LiveData<Order> getSingleOrderLiveData() {
        return singleOrderLiveData;
    }
}

