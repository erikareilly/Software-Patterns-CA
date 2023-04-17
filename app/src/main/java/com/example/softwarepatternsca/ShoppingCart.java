package com.example.softwarepatternsca;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.softwarepatternsca.Interface.ItemClickListener;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ShoppingCart extends AppCompatActivity implements ItemClickListener {

    DatabaseReference cReference;
    private RecyclerView recyclerView;
    MyCartAdapter cartAdapter;
    ArrayList<Cart>list;
    private Button nextB;
    private TextView tPrice;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);

        recyclerView = findViewById(R.id.cartList);
        cReference = FirebaseDatabase.getInstance().getReference("Shopping Cart").child("User Cart").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Products");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        cartAdapter = new MyCartAdapter(this,list,  this);
        recyclerView.setAdapter(cartAdapter);

        cReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Cart cart = dataSnapshot.getValue(Cart.class);
                    list.add(cart);
                }
                cartAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        nextB = (Button) findViewById(R.id.nextButton);
        tPrice = (TextView) findViewById(R.id.totalPrice);

    }


    @Override
    public void onClick(int position) {

    }
}