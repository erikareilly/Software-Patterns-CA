package com.example.softwarepatternsca;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.softwarepatternsca.Interface.ItemClickListener;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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
    ArrayList<Cart> list;
    private Button nextB;
    private TextView tPrice, txtMsg1;
    String totalAmount ="";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);


        nextB = (Button)findViewById(R.id.nextButton);
        nextB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShoppingCart.this, ConfirmOrder.class);
                intent.putExtra("Total Price", String.valueOf(cartAdapter.totalPrice));
                startActivity(intent);
            }
        });

        tPrice = (TextView)findViewById(R.id.totalPrice);
        txtMsg1 = (TextView)findViewById(R.id.msg1);
        // tPrice.setText(cartAdapter.totalPrice);
       // tPrice.setText(totalAmount);
        recyclerView = findViewById(R.id.cartList);
        cReference = FirebaseDatabase.getInstance().getReference("Shopping Cart").child("User Cart").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Products");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        cartAdapter = new MyCartAdapter(this, list, this);
        recyclerView.setAdapter(cartAdapter);

        cReference.addValueEventListener(new ValueEventListener() {
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
        nextB = (Button) findViewById(R.id.nextButton);
        tPrice = (TextView) findViewById(R.id.totalPrice);

    }


    @Override
    public void onClick(int position) {


    }




}
