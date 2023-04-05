package com.example.bottomnavigationproper.CartCommands;

import com.example.bottomnavigationproper.Models.Item;
import com.example.bottomnavigationproper.ViewModels.HomeViewModel;

public class RemoveStockCommand implements Command{
    private Item item;
    HomeViewModel viewModel;

    public RemoveStockCommand(HomeViewModel viewModel, Item item) {
        this.item = item;
        this.viewModel = viewModel;
    }

    @Override
    public void execute() {
        item.setStock(item.getStock() - 1);
        viewModel.updateItem(item);

    }
}