package com.example.softwarepatternsca;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.softwarepatternsca.Interface.ItemClickListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MyCartAdapter extends RecyclerView.Adapter<MyCartAdapter.CartViewHolder>{

    int totalPrice=0;
    DatabaseReference cReference;
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
        holder.txtPrice.setText("€"+ cart.getpPrice());

        totalPrice = totalPrice+ Integer.parseInt(cart.getpPrice());
        //holder.txtTotal.setText(String.valueOf(totalPrice));
        cReference = FirebaseDatabase.getInstance().getReference("Shopping Cart").child("User Cart").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Products");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence options[] = new CharSequence[]{
                        "Edit",
                        "Delete"
                };
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Shopping Cart Options");
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        if(i==0){
                            Intent intent = new Intent(context, ProductDetails.class);
                            intent.putExtra("pid",cart.getPid());
                            context.startActivity(intent);
                        }
                        if(i==1){
                            cReference.child("User Cart").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Products")
                                    .child(cart.getPid()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                Toast.makeText(context, "Item Removed From Cart", Toast.LENGTH_LONG).show();
                                                Intent intent = new Intent(context,StockClass.class);
                                                context.startActivity(intent);
                                            }

                                        }
                                    });
                        }
                    }
                });
                builder.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder{

        TextView txtName, txtPrice, txtTotal;


        public CartViewHolder(@NonNull View itemView, ItemClickListener itemClickListener) {
            super(itemView);

            txtName = itemView.findViewById(R.id.cart_prodName);
            txtPrice = itemView.findViewById(R.id.cart_prodPrice);
            txtTotal = itemView.findViewById(R.id.totalPrice);
        }
    }
}
