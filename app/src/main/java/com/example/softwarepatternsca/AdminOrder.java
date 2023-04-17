package com.example.softwarepatternsca;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.softwarepatternsca.Interface.ItemClickListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdminOrder extends AppCompatActivity implements ItemClickListener {

    private RecyclerView productList;
    private DatabaseReference prodRef;
    AdminAdapter cartAdapter;
    ArrayList<Cart> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_order);

        prodRef = FirebaseDatabase.getInstance().getReference().child("Shopping Cart")
                .child("Admin View").child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Products");
        productList = findViewById(R.id.cartList);
        productList.setHasFixedSize(true);
        productList.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        cartAdapter = new AdminAdapter(this, list, this);
        productList.setAdapter(cartAdapter);

        prodRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Cart cart = dataSnapshot.getValue(Cart.class);
                    list.add(cart);
                }
                cartAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onClick(int position) {

    }
}