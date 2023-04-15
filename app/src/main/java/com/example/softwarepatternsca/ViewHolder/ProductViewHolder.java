package com.example.softwarepatternsca.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextClock;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.softwarepatternsca.Interface.ItemClickListener;
import com.example.softwarepatternsca.R;

public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public ImageView imageView;
    public TextView txtProductName, txtProductPrice,txtProductManufacturer;
    public ItemClickListener listener;

    public ProductViewHolder(@NonNull View itemView) {
        super(itemView);

        imageView = (ImageView) itemView.findViewById(R.id.productImage);
        txtProductName = (TextView) itemView.findViewById(R.id.productName);
        txtProductPrice = (TextView) itemView.findViewById(R.id.productPrice);
        txtProductManufacturer = (TextView) itemView.findViewById(R.id.productManufacturer);

    }

    public void setItemClickListener(ItemClickListener itemClickListener){
       this.listener=listener;
    }

    @Override
    public void onClick(View v) {

        listener.onClick(v,getAdapterPosition(),false);
    }
}
