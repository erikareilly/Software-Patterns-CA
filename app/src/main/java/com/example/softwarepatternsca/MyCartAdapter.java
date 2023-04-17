package com.example.softwarepatternsca;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.softwarepatternsca.Interface.ItemClickListener;

import java.util.ArrayList;

public class MyCartAdapter extends RecyclerView.Adapter<MyCartAdapter.CartViewHolder>{

    private final ItemClickListener itemClickListener;
    Context context;
    ArrayList<Cart>list;


    public MyCartAdapter(Context context, ArrayList<Cart>list,ItemClickListener itemClickListener) {
        this.context=context;
        this.list=list;
        this.itemClickListener = itemClickListener;

    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v =  LayoutInflater.from(context).inflate(R.layout.cart_items,parent,false);
        return new MyCartAdapter.CartViewHolder(v, itemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        Cart cart = list.get(position);
        holder.txtName.setText(cart.getpName());
        holder.txtPrice.setText(cart.getpPrice());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder{

        TextView txtName, txtPrice;

        public CartViewHolder(@NonNull View itemView, ItemClickListener itemClickListener) {
            super(itemView);

            txtName = itemView.findViewById(R.id.cart_prodName);
            txtPrice = itemView.findViewById(R.id.cart_prodPrice);
        }
    }
}
