package com.example.bottomnavigationproper;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.bottomnavigationproper.Adapters.ItemAdapter;
import com.example.bottomnavigationproper.Models.Item;
import com.example.bottomnavigationproper.Models.Order;
import com.example.bottomnavigationproper.Models.OrderItem;
import com.example.bottomnavigationproper.Sorting.CategorySortingStrategy;
import com.example.bottomnavigationproper.Sorting.ManufacturerSortingStrategy;
import com.example.bottomnavigationproper.Sorting.PriceSortingStrategy;
import com.example.bottomnavigationproper.Sorting.SortingStrategy;
import com.example.bottomnavigationproper.Sorting.TitleSortingStrategy;
import com.example.bottomnavigationproper.ViewModels.HomeViewModel;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class HomeFragment extends Fragment {

    private HomeViewModel viewModel;

    List<Item> recyclerItems = new ArrayList<>();

    RecyclerView favStatsRV;

    View view;

    ItemAdapter adapter = new ItemAdapter();

    Order order = new Order();

    String spinnerSelection;




    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initStatViewModel();

    }

    private void initStatViewModel(){
        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        viewModel.init();
        viewModel.getItemsResponseLiveData().observe(this, new Observer<List<Item>>(){
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onChanged(List<Item> items) {
                if (items != null) {
                    recyclerItems = items;
                    initView();
                }
            }
        });
        viewModel.getItems();

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void initView() {
        populateUserName();
        populateItems();
        populateSpinner();
        initButtons();


    }

    private void initButtons() {
        Button searchButton = view.findViewById(R.id.search_button);
        Button filterButton = view.findViewById(R.id.filter_button);

        searchButton.setOnClickListener(v ->{
            TextView searchInput = view.findViewById(R.id.search_input);
            String searchPrompt = searchInput.getText().toString();

            String attributeFilter = spinnerSelection;

            viewModel.search(attributeFilter, searchPrompt);
        });

        filterButton.setOnClickListener(v ->{
            String attributeFilter = spinnerSelection;

            filterItems(spinnerSelection);
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void filterItems(String spinnerSelection) {

        SortingStrategy<Item> sortingStrategy;
        switch (spinnerSelection) {
            case "category":
                sortingStrategy = new CategorySortingStrategy();
                break;
            case "manufacturer":
                sortingStrategy = new ManufacturerSortingStrategy();
                break;
            case "price":
                sortingStrategy = new PriceSortingStrategy();
                break;
            default: //Title and Default both sort by title for ux purposes.
                sortingStrategy = new TitleSortingStrategy();
                break;

        }
        recyclerItems = sortingStrategy.sort(recyclerItems);
    }


    private void populateSpinner() {
        Spinner spinner = view.findViewById(R.id.sort_item_spinner);
        setSpinnerList(spinner);
    }

    private void populateUserName() {
        TextView loggedInNameTV = view.findViewById(R.id.home_user_name);
        String name;

        name = "Welcome " + UserSingleton.getInstance().getUser().getUsername();

        loggedInNameTV.setText(name);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void populateItems() {

        favStatsRV = view.findViewById(R.id.available_items_rv);

        registerForContextMenu(favStatsRV);
        adapter.setResults(recyclerItems);
        adapter.notifyDataSetChanged();

        favStatsRV.setLayoutManager(new LinearLayoutManager(getContext()));
        favStatsRV.setAdapter(adapter);
    }

    public void setSpinnerList(Spinner spinner){
        List<String> list = new ArrayList();
        list.add("category");
        list.add("manufacturer");
        list.add("title");
        list.add("price");


        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(getContext(),  R.layout.spinner_sort, list);
        adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                spinnerSelection = (String)parent.getItemAtPosition(pos);
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);


        return view;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = requireActivity().getMenuInflater();
        inflater.inflate(R.menu.item_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int position = adapter.getPosition();
        int itemId = item.getItemId();
        if (itemId == 1) { // "Add to cart" option
            if (UserSingleton.getInstance().isAdmin()) {
                addStock(recyclerItems.get(position));
            } else {
                decreaseStock(recyclerItems.get(position));

            }
            return true;
        } else if (itemId == 2) { // "Remove from cart" option
            if (UserSingleton.getInstance().isAdmin()) {
                decreaseStock(recyclerItems.get(position));
                addToCart(recyclerItems.get(position));
            } else {
                addStock(recyclerItems.get(position));
                removeFromCart(recyclerItems.get(position));
            }
            return true;
        } else {
            return super.onContextItemSelected(item);
        }
    }
    public void addStock(Item item){
        item.setStock(item.getStock() + 1);
        viewModel.updateItem(item);
    }

    public void decreaseStock(Item item){
        item.setStock(item.getStock() - 1);
        viewModel.updateItem(item);
    }

    public void addToCart(Item item){
        boolean itemAlreadyInList = false;
        for (OrderItem orderItem : order.getOrderItems()) {
            if (orderItem.getItem().equals(item)) {
                orderItem.setQuantity(orderItem.getQuantity() + 1);
                itemAlreadyInList = true;
                break;
            }
        }
        if (!itemAlreadyInList) {
            order.getOrderItems().add(new OrderItem(order, item, 1));
        }
    }

    public void removeFromCart(Item item){
        for (OrderItem orderItem : order.getOrderItems()) {
            if (orderItem.getItem().equals(item)) {
                if(orderItem.getQuantity() == 1){
                    order.getOrderItems().remove(orderItem);
                }else{
                    orderItem.setQuantity(orderItem.getQuantity() - 1);
                }
            }
        }
    }



    @Override
    public void onStop() {
        viewModel.getItemsResponseLiveData().removeObservers(this);

        super.onStop();

    }


}