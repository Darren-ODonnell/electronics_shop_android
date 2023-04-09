package com.example.bottomnavigationproper.ViewModels;

import android.app.Application;
import android.media.session.MediaSession;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.bottomnavigationproper.APIs.TokenSingleton;
import com.example.bottomnavigationproper.Models.Item;
import com.example.bottomnavigationproper.Models.ItemReview;
import com.example.bottomnavigationproper.Models.ItemReviewModel;
import com.example.bottomnavigationproper.Models.Order;
import com.example.bottomnavigationproper.Models.OrderModel;
import com.example.bottomnavigationproper.Services.ItemRepository;
import com.example.bottomnavigationproper.Services.ItemReviewRepository;
import com.example.bottomnavigationproper.Services.OrderRepository;

import java.util.List;

public class OrderViewModel extends AndroidViewModel {
        private OrderRepository orderRepository;

        private LiveData<List<Order>> customerOrderResponseLiveData;
        private LiveData<Order> singleOrderLiveData;

        private ItemRepository itemRepository;
        private LiveData<List<Item>> itemResponseLiveData;

        private LiveData<List<ItemReview>> itemReviewResponseLiveData;
        private ItemReviewRepository itemReviewRepository;

        public OrderViewModel(@NonNull Application application) {
            super(application);
        }

        public void init(){
            orderRepository = new OrderRepository();
            customerOrderResponseLiveData = orderRepository.getOrderResponseLiveData();
            singleOrderLiveData = orderRepository.getSingleOrderLiveData();

            itemRepository = new ItemRepository();
            itemResponseLiveData = itemRepository.getItemResponseLiveData();

            itemReviewRepository = new ItemReviewRepository();
            itemReviewResponseLiveData = itemReviewRepository.getItemReviewResponseLiveData();
        }

        public void getOrders(){
            orderRepository.getCustomerOrders(TokenSingleton.getInstance().getBearerTokenString());
        }


        public LiveData<List<Order>> getCustomerOrderResponseLiveData(){
            return customerOrderResponseLiveData;
        }

        public void addOrder(OrderModel orderModel){
            orderRepository.add(TokenSingleton.getInstance().getBearerTokenString(), orderModel);
        }

        public void getItems(){
            itemRepository.getItems(TokenSingleton.getInstance().getBearerTokenString());
        }


        public LiveData<List<Item>> getItemsResponseLiveData(){
        return itemResponseLiveData;
    }

        public LiveData<Order> getSingleOrderLiveData(){
            return singleOrderLiveData;
        }

        public void addReview(ItemReviewModel review) {
            itemReviewRepository.add(TokenSingleton.getInstance().getBearerTokenString(), review);
        }

        public LiveData<List<ItemReview>> getItemReviewLiveData(){
            return itemReviewResponseLiveData;
        }



    //TODO on checkout display order items and ask for review

}
