package com.example.bottomnavigationproper.Adapters;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bottomnavigationproper.Models.Item;
import com.example.bottomnavigationproper.R;
import com.example.bottomnavigationproper.UserSingleton;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Locale;


public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.InGameStatsHolder> implements View.OnCreateContextMenuListener {
    private List<Item> results = new ArrayList<>();
    private int longPressedPosition = -1;


    @NonNull
    @Override
    public InGameStatsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.in_game_stats_row, parent, false);

        return new InGameStatsHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull InGameStatsHolder holder, int position) {
        Item item = results.get(position);

        holder.titleTV.setText(item.getTitle());
        holder.manufacturerTV.setText(item.getManufacturer());
        holder.priceTV.setText(formatPrice(item.getPrice()));
        holder.imageTV.setText(item.getImage());
        holder.categoryTV.setText(item.getCategory());
        String stockQty = item.getStock().toString();
        holder.stockTV.setText(stockQty);
        holder.itemView.setOnCreateContextMenuListener(this);

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                longPressedPosition = holder.getAdapterPosition();
                return false;
            }
        });
    }

    private String formatPrice(Double price){
        return "€" + price;

    }


    @Override
    public int getItemCount() {
        return results.size();
    }

    public void setResults(List<Item> results) {
        this.results = results;
        notifyDataSetChanged();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {


        if(UserSingleton.getInstance().isAdmin()){
            menu.add(Menu.NONE, 1, Menu.FIRST, "Increase Stock");
            menu.add(Menu.NONE, 2, Menu.FLAG_APPEND_TO_GROUP, "Decrease Stock");
        }else{
            menu.add(Menu.NONE, 1, Menu.FIRST, "Add to Cart");
            menu.add(Menu.NONE, 2, Menu.FLAG_APPEND_TO_GROUP, "Remove from Cart");
        }


    }


    public int getPosition() {
        return longPressedPosition;
    }


    class InGameStatsHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        private TextView titleTV;
        private TextView manufacturerTV;
        private TextView categoryTV;
        private TextView priceTV;
        private TextView imageTV;
        private TextView stockTV;


        public InGameStatsHolder(@NonNull View itemView) {
            super(itemView);

            titleTV = itemView.findViewById(R.id.title_tv);
            manufacturerTV = itemView.findViewById(R.id.manufacturer_tv);
            priceTV = itemView.findViewById(R.id.price_tv);
            categoryTV = itemView.findViewById(R.id.category_tv);
            imageTV = itemView.findViewById(R.id.image_tv);
            stockTV = itemView.findViewById(R.id.stock_tv);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            if(UserSingleton.getInstance().isAdmin()){
                menu.add(Menu.NONE, 1, Menu.NONE, "Increase Stock");
                menu.add(Menu.NONE, 2, Menu.NONE, "Reduce Stock");
            }else{
                menu.add(Menu.NONE, 1, Menu.NONE, "Add to cart");
                menu.add(Menu.NONE, 2, Menu.NONE, "Remove from cart");
            }

        }
    }



}
