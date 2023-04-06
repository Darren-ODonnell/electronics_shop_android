package com.example.bottomnavigationproper.Services;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.bottomnavigationproper.APIs.APIClient;
import com.example.bottomnavigationproper.APIs.APIInterface;
import com.example.bottomnavigationproper.Models.Item;
import com.example.bottomnavigationproper.Models.StatModel;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
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

//    public void getItems(String token){
//
//        Call<ResponseBody> call = apiInterface.getItems(token);
//        Response<ResponseBody> response = null;
//        try {
//             response = call.execute();
//            System.out.println(response.raw());
//
//            if (response.isSuccessful()) {
//                assert response.body() != null;
//                String json = response.body().string();
//                Gson gson = new Gson();
//                Item myObject = gson.fromJson(json, Item.class);
//            }
//        }catch (IOException e){
//            e.printStackTrace();
//        }
//
//        apiInterface.getItems(token)
//                .enqueue(new Callback<ResponseBody>() {
//                    @Override
//                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                        if(response.body() != null)
//                            System.out.println("yes");
////                            itemResponseLiveData.postValue(response.body());
//
//                    }
//
//                    @Override
//                    public void onFailure(Call<ResponseBody> call, Throwable t) {
//                        itemResponseLiveData.postValue(null);
//                    }
//                });
//
//    }



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

    public void search(String token, String attributeFilter, String searchPrompt) {
        HashMap<String, String> params = new HashMap<>();
        params.put("attributeFilter", attributeFilter);
        params.put("searchPrompt", searchPrompt);
        apiInterface.search(token, params).enqueue(new Callback<List<Item>>() {
            @Override
            public void onResponse(Call<List<Item>> call, Response<List<Item>> response) {
                itemResponseLiveData.postValue(response.body());
            }

            @Override
            public void onFailure(Call<List<Item>> call, Throwable t) {
                itemResponseLiveData.postValue(null);
            }
        });

    }
}