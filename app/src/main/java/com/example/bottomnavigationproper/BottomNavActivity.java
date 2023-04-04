package com.example.bottomnavigationproper;

import static com.example.bottomnavigationproper.MainActivity.API_KEY;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.bottomnavigationproper.Models.Item;
import com.example.bottomnavigationproper.Models.Order;
import com.example.bottomnavigationproper.ViewModels.HomeViewModel;
import com.example.bottomnavigationproper.ViewModels.OrderViewModel;
import com.example.bottomnavigationproper.databinding.BottomNavBinding;

import java.util.List;

public class BottomNavActivity extends AppCompatActivity {
    BottomNavBinding binding;

    OrderViewModel viewModel;

    List<Order> orders;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder(StrictMode.getVmPolicy())
                .detectLeakedClosableObjects()
                .build());

        setContentView(R.layout.activity_login);
        initViewModel();

        onLogin();

    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(this).get(OrderViewModel.class);
        viewModel.init();
        viewModel.getCustomerOrderResponseLiveData().observe(this, new Observer<List<Order>>(){
            @Override
            public void onChanged(List<Order> orderList){
                orders = orderList;
                populateCustomerOrders();
            }
        });
    }

    private void populateCustomerOrders() {
        //TODO create recycler view of customer orders
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bottom_nav_options, menu);

        MenuItem viewCustomerOrders = menu.findItem(R.id.customer_orders);

        viewCustomerOrders.setVisible(UserSingleton.getInstance().isAdmin());
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.customer_orders) {
            getCustomerOrders();
            return true;
        } else if (id == R.id.action_log_out) {
            SharedPreferences preferences = getSharedPreferences(MainActivity.PREFS_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.remove(API_KEY); // replace "my_key" with the key you want to delete
            editor.apply();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void getCustomerOrders() {
        viewModel.getOrders();


        //TODO display customer orders in recycler view
    }


    private void onLogin(){
        binding = BottomNavBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new HomeFragment());

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {

            switch(item.getItemId()){
                case R.id.homeFragment:
                    replaceFragment(new HomeFragment());
                    break;
                case R.id.statsFragment:
                    replaceFragment(new StatsFragment());
                    break;
            }
            return true;
        });
    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainerView2,fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onStop() {
        if(viewModel.getCustomerOrderResponseLiveData() != null)
            viewModel.getCustomerOrderResponseLiveData().removeObservers(this);
        super.onStop();

    }

}



