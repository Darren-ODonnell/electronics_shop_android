package com.example.bottomnavigationproper;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.example.bottomnavigationproper.Models.Order;
import com.example.bottomnavigationproper.Models.Player;

import java.io.Serializable;
import java.util.List;

public class StatsFragment extends Fragment {

    Order shoppingCart;
    View view;

    public StatsFragment() {
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initStatsSelectionViewModel();

    }

    private void initStatsSelectionViewModel(){

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_stats, container, false);

        initButton();

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

    public void openReviewDialog(Order order){
        //TODO loop on orderitems and display review selection for each
    }

    @Override
    public void onStop() {
        super.onStop();

    }

}