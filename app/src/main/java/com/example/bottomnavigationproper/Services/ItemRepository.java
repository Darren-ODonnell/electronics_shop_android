package com.example.bottomnavigationproper.Services;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.bottomnavigationproper.APIs.APIClient;
import com.example.bottomnavigationproper.APIs.APIInterface;
import com.example.bottomnavigationproper.Models.Item;
import com.example.bottomnavigationproper.Models.StatModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemRepository {

    private APIInterface apiInterface;
    private MutableLiveData<List<Item>> itemResponseLiveData;


    public ItemRepository(){
        itemResponseLiveData = new MutableLiveData<>();

        apiInterface = APIClient.getClient().create(APIInterface.class);
    }

    public void getItems(String token){
        apiInterface.getItems(token)
                .enqueue(new Callback<List<Item>>() {
                    @Override
                    public void onResponse(Call<List<Item>> call, Response<List<Item>> response) {
                        if(response.body() != null)
                            itemResponseLiveData.postValue(response.body());

                    }

                    @Override
                    public void onFailure(Call<List<Item>> call, Throwable t) {
                        itemResponseLiveData.postValue(null);
                    }
                });

    }
    public LiveData<List<Item>> getItemResponseLiveData() {
        return itemResponseLiveData;
    }


    public void update(String token, Item item) {

        apiInterface.updateItem(token, item)
                .enqueue(new Callback<Item>() {
                    @Override
                    public void onResponse(Call<Item> call, Response<Item> response) {
                        if(response.body() != null)
                            itemResponseLiveData.postValue(response.body());

                    }

                    @Override
                    public void onFailure(Call<Item> call, Throwable t) {
                        itemResponseLiveData.postValue(null);
                    }
                });
    }
}

