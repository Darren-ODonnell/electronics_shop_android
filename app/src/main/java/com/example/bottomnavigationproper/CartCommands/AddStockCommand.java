package com.example.bottomnavigationproper.CartCommands;

import androidx.lifecycle.ViewModel;

import com.example.bottomnavigationproper.Models.Item;
import com.example.bottomnavigationproper.ViewModels.HomeViewModel;


public class AddStockCommand implements Command{
    private Item item;
    HomeViewModel viewModel;

    public AddStockCommand(HomeViewModel viewModel, Item item) {
        this.item = item;
        this.viewModel = viewModel;
    }

    @Override
    public void execute() {
        item.setStock(item.getStock() + 1);
        viewModel.updateItem(item);

    }
}
