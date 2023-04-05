package com.example.bottomnavigationproper.SortingStrategy;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.bottomnavigationproper.Models.Item;

import java.util.Comparator;

public class ManufacturerSortingStrategy extends SortingStrategy<Item> {
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected Comparator<Item> getAscendingComparator() {
        return Comparator.comparing(Item::getManufacturer);
    }
}