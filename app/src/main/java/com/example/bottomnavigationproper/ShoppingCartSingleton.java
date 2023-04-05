package com.example.bottomnavigationproper;

import com.example.bottomnavigationproper.CartCommands.Command;
import com.example.bottomnavigationproper.Models.Item;
import com.example.bottomnavigationproper.Models.OrderItemModel;
import com.example.bottomnavigationproper.Models.OrderModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShoppingCartSingleton {

    private static ShoppingCartSingleton instance;

    private OrderModel shoppingCart = new OrderModel();

    private ShoppingCartSingleton() {

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

    public void addToCart(Item item) {
        if (shoppingCart.getOrderItemModels() == null) {
            List<OrderItemModel> orderItemModels = new ArrayList<>();
            orderItemModels.add(new OrderItemModel(item.getId(), 1));
            shoppingCart.setOrderItemModels(orderItemModels);
        } else {
            Map<Integer, OrderItemModel> orderItemModelMap = new HashMap<>();
            for (OrderItemModel orderItemModel : shoppingCart.getOrderItemModels()) {
                orderItemModelMap.put(orderItemModel.getItem_id(), orderItemModel);
            }
            if (orderItemModelMap.containsKey(item.getId())) {
                OrderItemModel orderItemModel = orderItemModelMap.get(item.getId());
                orderItemModel.setQuantity(orderItemModel.getQuantity() + 1);
            } else {
                shoppingCart.getOrderItemModels().add(new OrderItemModel(item.getId(), 1));
            }
        }
    }

    public void removeFromCart(Item item){
        if (!shoppingCart.getOrderItemModels().isEmpty()) {
            for (OrderItemModel orderItemModel : shoppingCart.getOrderItemModels()) {
                if (orderItemModel.getItem_id().equals(item.getId())) {
                    if (orderItemModel.getQuantity() == 1) {
                        shoppingCart.getOrderItemModels().remove(orderItemModel);
                    } else {
                        orderItemModel.setQuantity(orderItemModel.getQuantity() - 1);
                    }
                }
            }
        }
    }
}
