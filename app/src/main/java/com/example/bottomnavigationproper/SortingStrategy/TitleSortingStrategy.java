package com.example.bottomnavigationproper.SortingStrategy;

import com.example.bottomnavigationproper.Models.Item;

import java.util.Comparator;

public class TitleSortingStrategy extends SortingStrategy<Item> {
    @Override
    protected Comparator<Item> getAscendingComparator() {
        return Comparator.comparing(Item::getTitle);
    }
}