package com.example.bottomnavigationproper.Services;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.bottomnavigationproper.APIs.APIClient;
import com.example.bottomnavigationproper.APIs.APIInterface;
import com.example.bottomnavigationproper.Models.Item;
import com.example.bottomnavigationproper.Models.ItemReview;
import com.example.bottomnavigationproper.Models.ItemReviewModel;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemReviewRepository {

        private APIInterface apiInterface;
        private MutableLiveData<List<ItemReview>> itemReviewResponseLiveData;


        public ItemReviewRepository(){
            itemReviewResponseLiveData = new MutableLiveData<>();

            apiInterface = APIClient.getClient().create(APIInterface.class);
        }


        public LiveData<List<ItemReview>> getItemReviewResponseLiveData() {
            return itemReviewResponseLiveData;
        }


        public void add(String token, ItemReviewModel itemReviewModel) {

            apiInterface.addItemReview(token, itemReviewModel)
                    .enqueue(new Callback<List<ItemReview>>() {
                        @Override
                        public void onResponse(Call<List<ItemReview>> call, Response<List<ItemReview>> response) {
                            if(response.body() != null)
                                itemReviewResponseLiveData.postValue(response.body());
                        }

                        @Override
                        public void onFailure(Call<List<ItemReview>> call, Throwable t) {
                            itemReviewResponseLiveData.postValue(null);
                        }
                    });
        }

}
