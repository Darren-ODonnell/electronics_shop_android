package com.example.bottomnavigationproper.ViewModels;

import android.app.Application;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.bottomnavigationproper.APIs.TokenSingleton;
import com.example.bottomnavigationproper.Models.Item;
import com.example.bottomnavigationproper.Models.ItemModel;
import com.example.bottomnavigationproper.Models.ItemReview;
import com.example.bottomnavigationproper.Models.Order;
import com.example.bottomnavigationproper.Services.ItemRepository;
import com.example.bottomnavigationproper.Services.OrderRepository;
import com.example.bottomnavigationproper.UserSingleton;

import java.util.List;

public class HomeViewModel extends AndroidViewModel {
    private ItemRepository itemRepository;

    private LiveData<List<Item>> itemResponseLiveData;
    private LiveData<List<ItemReview>> itemReviewResponseLiveData;

    public HomeViewModel(@NonNull Application application) {
        super(application);
    }

    public void init(){
        itemRepository = new ItemRepository();
        itemResponseLiveData = itemRepository.getItemResponseLiveData();
        itemReviewResponseLiveData = itemRepository.getItemReviewResponseLiveData();
    }

    public void getItems(){
        itemRepository.getItems(TokenSingleton.getInstance().getBearerTokenString());
    }


    public LiveData<List<Item>> getItemsResponseLiveData(){
        return itemResponseLiveData;
    }

    public void updateItem(Item item) {
        itemRepository.update(TokenSingleton.getInstance().getBearerTokenString(), item);
    }

    public void search(String attributeFilter, String searchPrompt) {
        itemRepository.search(TokenSingleton.getInstance().getBearerTokenString(), attributeFilter, searchPrompt);
    }

    public void getReviews() {
        itemRepository.getItemReviewsGroupedByItem(TokenSingleton.getInstance().getBearerTokenString());
    }

    public LiveData<List<ItemReview>> getItemReviewResponseLiveData(){
        return itemReviewResponseLiveData;
    }
}
