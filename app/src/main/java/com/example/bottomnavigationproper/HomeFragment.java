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

import android.util.Log;
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

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.bottomnavigationproper.APIs.TokenSingleton;
import com.example.bottomnavigationproper.Adapters.ItemAdapter;
import com.example.bottomnavigationproper.CartCommands.AddStockCommand;
import com.example.bottomnavigationproper.CartCommands.Command;
import com.example.bottomnavigationproper.CartCommands.RemoveStockCommand;
import com.example.bottomnavigationproper.Models.Item;
import com.example.bottomnavigationproper.Models.OrderModel;
import com.example.bottomnavigationproper.SortingStrategy.CategorySortingStrategy;
import com.example.bottomnavigationproper.SortingStrategy.ManufacturerSortingStrategy;
import com.example.bottomnavigationproper.SortingStrategy.PriceSortingStrategy;
import com.example.bottomnavigationproper.SortingStrategy.SortingStrategy;
import com.example.bottomnavigationproper.SortingStrategy.TitleSortingStrategy;
import com.example.bottomnavigationproper.ViewModels.HomeViewModel;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class HomeFragment extends Fragment {

    private HomeViewModel viewModel;

    List<Item> recyclerItems = new ArrayList<>();

    RecyclerView favStatsRV;

    View view;

    ItemAdapter adapter = new ItemAdapter();

    OrderModel orderModel = new OrderModel();

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

//        getItems(TokenSingleton.getInstance().getBearerTokenString());
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void initView() {
        populateUserName();
        populateItems();
        initButtons();


    }

    @RequiresApi(api = Build.VERSION_CODES.N)
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
        adapter.setResults(recyclerItems);
        adapter.notifyDataSetChanged();
    }


    private void populateSpinner() {
        Spinner spinner = view.findViewById(R.id.spinner_sort);
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


        favStatsRV.setLayoutManager(new LinearLayoutManager(getContext()));
        favStatsRV.setAdapter(adapter);
        adapter.setResults(recyclerItems);
        adapter.notifyDataSetChanged();
    }

    public void setSpinnerList(Spinner spinner){
        List<String> list = new ArrayList();
        list.add("category");
        list.add("manufacturer");
        list.add("title");
        list.add("price");

        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(getContext(), R.layout.spinner_item, list);
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
        populateSpinner();

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
        Command command;
        if (itemId == 1) { // "Add to cart" option
            if (UserSingleton.getInstance().isAdmin()) {
                command = new AddStockCommand(viewModel, recyclerItems.get(position));
            } else {
                ShoppingCartSingleton.getInstance().addToCart(recyclerItems.get(position));
                command = new RemoveStockCommand(viewModel, recyclerItems.get(position));

            }
            adapter.notifyDataSetChanged();
            command.execute();
            return true;
        } else if (itemId == 2) { // "Remove from cart" option
            if (UserSingleton.getInstance().isAdmin()) {
                command = new RemoveStockCommand(viewModel, recyclerItems.get(position));
            } else {
                command = new AddStockCommand(viewModel, recyclerItems.get(position));
                ShoppingCartSingleton.getInstance().removeFromCart(recyclerItems.get(position));

            }
            adapter.notifyDataSetChanged();
            command.execute();
            return true;
        } else {
            return super.onContextItemSelected(item);
        }

    }

    public void getItems(final String token) {
        String url = "http://192.168.100.56:8080/items/list";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            List<Item> itemList = new ArrayList<>();
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = response.getJSONObject(i);
                                Log.d("errorMessage", jsonObject.toString());
                                Gson gson = new Gson();
                                Item item = gson.fromJson(jsonObject.toString(), Item.class);
                                // set the fields of item as needed
                                itemList.add(item);
                            }
                            // update itemResponseLiveData with itemList
                        } catch (JSONException e) {
                            // handle JSON parsing error
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // handle error response
                    }
                })
        {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", token);
                return headers;
            }
        };

// Add the request to the RequestQueue.
        Volley.newRequestQueue(requireContext()).add(jsonArrayRequest);
    }

    @Override
    public void onStop() {
        viewModel.getItemsResponseLiveData().removeObservers(this);

        super.onStop();

    }


}