package com.example.softwarepatternsca;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.softwarepatternsca.Interface.ItemClickListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private final ItemClickListener itemClickListener;
    Context context;
    ArrayList<Product> list;

    public MyAdapter(Context context, ArrayList<Product> list, ItemClickListener itemClickListener) {
        this.context = context;
        this.list = list;
        this.itemClickListener=itemClickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType ) {
        View v = LayoutInflater.from(context).inflate(R.layout.product_layout,parent,false);
        return new MyViewHolder(v, itemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Product product = list.get(position);
        holder.productName.setText(product.getName());
        holder.productPrice.setText("€"+product.getPrice());
        holder.productManufacturer.setText(product.getManufacturer());
        Picasso.get().load(product.getImage()).into(holder.productImage);

          holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,ProductDetails.class);
                intent.putExtra("pid", product.getPid());
                context.startActivity(intent);

            }
        });
    }



    @Override
    public int getItemCount() {
        return list.size();
    }




    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView productName, productPrice, productManufacturer;
        ImageView productImage;

        public MyViewHolder(@NonNull View itemView, ItemClickListener itemClickListener) {
            super(itemView);


            productName = itemView.findViewById(R.id.productName);
            productPrice = itemView.findViewById(R.id.productPrice);
            productManufacturer = itemView.findViewById(R.id.productManufacturer);
            productImage = itemView.findViewById(R.id.productImage);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemClickListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            itemClickListener.onClick(position);
                        }
                    }
                }
            });
        }
    }
}
