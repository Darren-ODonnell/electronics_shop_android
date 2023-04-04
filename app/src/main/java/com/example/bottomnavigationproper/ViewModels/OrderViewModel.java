package com.example.bottomnavigationproper.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.bottomnavigationproper.APIs.TokenSingleton;
import com.example.bottomnavigationproper.Models.Item;
import com.example.bottomnavigationproper.Models.Order;
import com.example.bottomnavigationproper.Models.OrderModel;
import com.example.bottomnavigationproper.Services.ItemRepository;
import com.example.bottomnavigationproper.Services.OrderRepository;

import java.util.List;

public class OrderViewModel extends AndroidViewModel {
        private OrderRepository orderRepository;

        private LiveData<List<Order>> customerOrderResponseLiveData;
        private LiveData<Order> singleOrderLiveData;

        public OrderViewModel(@NonNull Application application) {
            super(application);
        }

        public void init(){
            orderRepository = new OrderRepository();
            customerOrderResponseLiveData = orderRepository.getOrderResponseLiveData();
            singleOrderLiveData = orderRepository.getSingleOrderLiveData();
        }

        public void getOrders(){
            orderRepository.getCustomerOrders(TokenSingleton.getInstance().getBearerTokenString());
        }


        public LiveData<List<Order>> getCustomerOrderResponseLiveData(){
            return customerOrderResponseLiveData;
        }

        public void add(OrderModel orderModel){
            orderRepository.add(TokenSingleton.getInstance().getBearerTokenString(), orderModel);
        }

        public LiveData<Order> getSingleOrderLiveData(){
            return singleOrderLiveData;
        }
        //TODO on checkout display order items and ask for review

}
