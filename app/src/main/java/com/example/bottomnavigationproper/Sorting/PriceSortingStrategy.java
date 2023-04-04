package com.example.bottomnavigationproper.Sorting;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.bottomnavigationproper.Models.Item;

import java.util.Comparator;

public class PriceSortingStrategy extends SortingStrategy<Item> {
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected Comparator<Item> getAscendingComparator() {
        return Comparator.comparing(Item::getPrice);
    }
}