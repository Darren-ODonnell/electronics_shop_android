package com.example.bottomnavigationproper;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.bottomnavigationproper.Adapters.OrderItemModelAdapter;
import com.example.bottomnavigationproper.Adapters.OrderAdapter;
import com.example.bottomnavigationproper.Models.Item;
import com.example.bottomnavigationproper.Models.ItemReview;
import com.example.bottomnavigationproper.Models.ItemReviewModel;
import com.example.bottomnavigationproper.Models.Order;
import com.example.bottomnavigationproper.Models.OrderItem;
import com.example.bottomnavigationproper.Models.OrderItemModel;
import com.example.bottomnavigationproper.Models.OrderModel;
import com.example.bottomnavigationproper.ViewModels.OrderViewModel;

import java.util.List;

public class CartFragment extends Fragment {

    OrderModel shoppingCart;
    View view;
    OrderViewModel viewModel;
    RecyclerView recyclerView;


    public CartFragment() {
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
        view = inflater.inflate(R.layout.fragment_cart, container, false);

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
        Button button = view.findViewById(R.id.checkoutButton);

        viewModel.getSingleOrderLiveData().observe(this, new Observer<Order>() {
            @Override
            public void onChanged(Order order) {
                openReviewDialog(order);
            }
        });

        if(UserSingleton.getInstance().isAdmin()){
            button.setVisibility(View.INVISIBLE);
        }else {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewModel.addOrder(shoppingCart);

                }
            });
        }
    }

    public void openReviewDialog(Order order){
        for(OrderItem orderItem: order.getOrderItems()){

            showDialog(orderItem.getItem());

        }
        //TODO loop on orderitems and display review selection for each
    }

    public void showDialog(Item item) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Rate " + item.getTitle());

        // Set up the layout for the dialog
        LinearLayout layout = new LinearLayout(getContext());
        layout.setOrientation(LinearLayout.VERTICAL);

        // Add the "Rating" text view and spinner to the dialog layout
        TextView ratingTextView = new TextView(getContext());
        ratingTextView.setText("Rating:");
        Spinner ratingSpinner = new Spinner(getContext());
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, new String[]{"1", "2", "3", "4", "5"});
        ratingSpinner.setAdapter(adapter);

        // Add the comment text view and edit text to the dialog layout
        TextView commentTextView = new TextView(getContext());
        commentTextView.setText("Comment:");
        EditText commentEditText = new EditText(getContext());

        // Add the views to the layout
        layout.addView(ratingTextView);
        layout.addView(ratingSpinner);
        layout.addView(commentTextView);
        layout.addView(commentEditText);

        // Set the layout for the dialog and show it
        builder.setView(layout);
        builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Handle the submit button click here
                ItemReviewModel review = new ItemReviewModel();
                review.setUser(UserSingleton.getInstance().getUser());
                review.setComment( commentEditText.getText().toString());
                review.setRating(Integer.parseInt(ratingSpinner.getSelectedItem().toString()));
                review.setItem(item);

                viewModel.addReview(review);
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onStop() {
        super.onStop();

    }

}