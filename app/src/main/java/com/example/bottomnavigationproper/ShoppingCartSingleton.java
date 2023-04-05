package com.example.bottomnavigationproper;

import com.example.bottomnavigationproper.CartCommands.Command;
import com.example.bottomnavigationproper.Models.Item;
import com.example.bottomnavigationproper.Models.OrderItemModel;
import com.example.bottomnavigationproper.Models.OrderModel;

public class ShoppingCartSingleton {

    private static ShoppingCartSingleton instance;

    private OrderModel shoppingCart;

    private Command addCommand;
    private Command removeCommand;

    private ShoppingCartSingleton(Command addCommand, Command removeCommand) {
        this.addCommand = addCommand;
        this.removeCommand = removeCommand;
    }

    public static ShoppingCartSingleton getInstance(Command addCommand, Command removeCommand) {
        if (instance == null) {
            instance = new ShoppingCartSingleton(addCommand, removeCommand);
        }
        return instance;
    }

    public static ShoppingCartSingleton getInstance(){
        instance = (instance == null)? new ShoppingCartSingleton(): instance;
        return instance;
    }


    public OrderModel getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(OrderModel shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    public void addToCart(Item item){
        boolean itemAlreadyInList = false;
        for (OrderItemModel orderItemModel : shoppingCart.getOrderItemModels()) {
            if (orderItemModel.getItem_id().equals(item.getId())) {
                orderItemModel.setQuantity(orderItemModel.getQuantity() + 1);
                itemAlreadyInList = true;
                break;
            }
        }
        if (!itemAlreadyInList) {
            shoppingCart.getOrderItemModels().add(new OrderItemModel(item.getId(), 1));
        }
    }

    public void removeFromCart(Item item){
        for (OrderItemModel orderItemModel : shoppingCart.getOrderItemModels()) {
            if (orderItemModel.getItem_id().equals(item.getId())) {
                if(orderItemModel.getQuantity() == 1){
                    shoppingCart.getOrderItemModels().remove(orderItemModel);
                }else{
                    orderItemModel.setQuantity(orderItemModel.getQuantity() - 1);
                }
            }
        }
    }
}
