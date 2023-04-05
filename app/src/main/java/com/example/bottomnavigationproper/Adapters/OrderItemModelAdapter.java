package com.example.bottomnavigationproper.Adapters;

import android.os.Build;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bottomnavigationproper.Models.Item;
import com.example.bottomnavigationproper.Models.OrderItem;
import com.example.bottomnavigationproper.Models.OrderItemModel;
import com.example.bottomnavigationproper.R;
import com.example.bottomnavigationproper.User;
import com.example.bottomnavigationproper.UserSingleton;

import java.util.ArrayList;
import java.util.List;

public class OrderItemModelAdapter extends RecyclerView.Adapter<OrderItemModelAdapter.OrderHolder> {
        private List<OrderItemModel> results = new ArrayList<>();
        private List<Item> items = new ArrayList<>();
        User user;

        @NonNull
        @Override
        public OrderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.order_item_row, parent, false);

            return new OrderHolder(itemView);
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void onBindViewHolder(@NonNull OrderItemModelAdapter.OrderHolder holder, int position) {
            OrderItemModel orderItemModel = results.get(position);

            Item item = getItemById(orderItemModel.getItem_id());

            holder.itemTV.setText(item.getTitle());
            String qty = orderItemModel.getQuantity().toString();
            holder.quantityTV.setText(qty);

            double price = item.getPrice() * orderItemModel.getQuantity();
            holder.priceTV.setText(formatPrice(price));

        }

        private String formatPrice(Double price){
            return "€" + price;

        }


        @Override
        public int getItemCount() {
            return results.size();
        }

        public void setResults(List<OrderItemModel> results) {
            this.results = results;
            notifyDataSetChanged();
        }

        public void setItems(List<Item> items){
            this.items = items;
            notifyDataSetChanged();
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        private Item getItemById(Integer id){
            return items.stream()
                    .filter(item -> id.equals(item.getId()))
                    .findFirst()
                    .orElse(null);
        }


    public void setCustomer(User user) {
            this.user = user;
    }


    class OrderHolder extends RecyclerView.ViewHolder {
        private TextView itemTV;
        private TextView priceTV;
        private TextView quantityTV;


        public OrderHolder(@NonNull View itemView) {
            super(itemView);

            itemTV = itemView.findViewById(R.id.order_item_title_tv);
            quantityTV = itemView.findViewById(R.id.order_item_stock_tv);
            priceTV = itemView.findViewById(R.id.order_item_price_tv);

        }


    }
}
