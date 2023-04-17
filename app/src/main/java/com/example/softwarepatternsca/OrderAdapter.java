package com.example.softwarepatternsca;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.softwarepatternsca.Interface.ItemClickListener;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder>{
    private final ItemClickListener itemClickListener;
    Context context;
    ArrayList<Orders> list;

    public OrderAdapter(Context context, ArrayList<Orders> list, ItemClickListener itemClickListener) {
        this.context = context;
        this.list = list;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.order_layout,parent,false);
        return new OrderViewHolder(v, itemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {

        Orders order = list.get(position);
        holder.userTxt.setText("Customer Name: "+ order.getName());
        holder.priceTxt.setText("Total Price: "+ order.getTotalAmount());
        holder.eirTxt.setText("Eircode: "+ order.getEircode());


    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public static class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView userTxt, priceTxt, eirTxt;

            public OrderViewHolder(@NonNull View itemView, ItemClickListener itemClickListener) {
                super(itemView);

                userTxt = itemView.findViewById(R.id.username);
                priceTxt = itemView.findViewById(R.id.order_totalPrice);
                eirTxt = itemView.findViewById(R.id.order_address);



            }
        }
    }

