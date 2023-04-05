package com.example.bottomnavigationproper;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.bottomnavigationproper.Adapters.OrderItemModelAdapter;
import com.example.bottomnavigationproper.Adapters.OrderAdapter;
import com.example.bottomnavigationproper.Models.Item;
import com.example.bottomnavigationproper.Models.Order;
import com.example.bottomnavigationproper.Models.OrderModel;
import com.example.bottomnavigationproper.ViewModels.OrderViewModel;

import java.util.List;

public class StatsFragment extends Fragment {

    OrderModel shoppingCart;
    View view;
    OrderViewModel viewModel;
    RecyclerView recyclerView;


    public StatsFragment() {
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initStatsSelectionViewModel();
        shoppingCart = ShoppingCartSingleton.getInstance().getShoppingCart();

    }

    private void initStatsSelectionViewModel(){

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_stats, container, false);

        viewModel = new ViewModelProvider(this).get(OrderViewModel.class);
        viewModel.init();

        initButton();

        recyclerView = view.findViewById(R.id.order_items_RV);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        if(UserSingleton.getInstance().isAdmin()){
            viewModel.getCustomerOrderResponseLiveData().observe(getViewLifecycleOwner(), new Observer<List<Order>>() {
                @Override
                public void onChanged(List<Order> orders) {
                    OrderAdapter adapter = new OrderAdapter();
                    adapter.setResults(orders);

                    recyclerView.setAdapter(adapter);
                }
            });
            viewModel.getOrders();

        }else{
            viewModel.getItemsResponseLiveData().observe(getViewLifecycleOwner(), new Observer<List<Item>>() {
                @Override
                public void onChanged(List<Item> items) {
                    OrderItemModelAdapter adapter = new OrderItemModelAdapter();
                    adapter.setCustomer(UserSingleton.getInstance().getUser());
                    adapter.setResults(shoppingCart.getOrderItemModels());
                    adapter.setItems(items);
                    recyclerView.setAdapter(adapter);
                }
            });
            viewModel.getItems();

        }

        return view;
    }

    private void initButton() {
        ImageButton button = view.findViewById(R.id.statsDisplayButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               openReviewDialog(shoppingCart);
            }
        });
    }

    public void openReviewDialog(OrderModel orderModel){
        //TODO loop on orderitems and display review selection for each
    }

    @Override
    public void onStop() {
        super.onStop();

    }

}