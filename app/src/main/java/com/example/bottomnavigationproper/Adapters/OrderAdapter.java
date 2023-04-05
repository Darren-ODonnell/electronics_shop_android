package com.example.bottomnavigationproper.Adapters;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bottomnavigationproper.Models.Item;
import com.example.bottomnavigationproper.Models.Order;
import com.example.bottomnavigationproper.Models.OrderItem;
import com.example.bottomnavigationproper.R;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderHolder> {
        private List<Order> results = new ArrayList<>();

    @NonNull
        @Override
        public OrderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.order_model_item, parent, false);

            return new OrderHolder(itemView);
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void onBindViewHolder(@NonNull OrderAdapter.OrderHolder holder, int position) {
            Order customerOrder = results.get(position);

            List<OrderItem> orderItems = customerOrder.getOrderItems();

            String itemsString = orderItems.stream()
                    .map(orderItem -> orderItem.getItem().getTitle() + " " + orderItem.getQuantity())
                    .collect(Collectors.joining("\n"));

            Double total = orderItems.stream()
                    .mapToDouble(orderItem -> orderItem.getItem().getPrice() * orderItem.getQuantity())
                    .sum();

            holder.customerTV.setText(customerOrder.getCustomer().getUsername());
            holder.orderItemsTV.setText(itemsString);
            holder.totalCostTV.setText(formatPrice(total));

        }

        private String formatPrice(Double price){
            return "€" + price;

        }


        @Override
        public int getItemCount() {
            return results.size();
        }

        public void setResults(List<Order> results) {
            this.results = results;
            notifyDataSetChanged();
        }


        class OrderHolder extends RecyclerView.ViewHolder {
            public TextView customerTV;
            private TextView orderItemsTV;
            private TextView totalCostTV;


            public OrderHolder(@NonNull View itemView) {
                super(itemView);

                customerTV = itemView.findViewById(R.id.customer_tv);
                orderItemsTV = itemView.findViewById(R.id.order_items_tv);
                totalCostTV = itemView.findViewById(R.id.total_cost_tv);

            }

        }

}
