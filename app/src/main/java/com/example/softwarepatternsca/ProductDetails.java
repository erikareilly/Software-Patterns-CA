package com.example.softwarepatternsca;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class ProductDetails extends AppCompatActivity {

    private TextView pName, pPrice,pMan;
    private Button addB;
    private ImageView pImage;
    private String productID = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        productID = getIntent().getStringExtra("pid");

        pName = (TextView) findViewById(R.id.productDetailsName);
        pPrice = (TextView) findViewById(R.id.productDetailsPrice);
        pMan = (TextView) findViewById(R.id.productDetailsMan);
        addB = (Button) findViewById(R.id.addToCart);
        pImage = (ImageView) findViewById(R.id.productImageDetails);

        addB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               addingToCart();
            }
        });
        getProductDetails(productID);
    }

    private void getProductDetails(String productID) {
        DatabaseReference productRef =FirebaseDatabase.getInstance().getReference().child("Products");
        productRef.child(productID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    Product product =snapshot.getValue(Product.class);
                    pName.setText(product.getName());
                    pPrice.setText(product.getPrice());
                    pMan.setText(product.getManufacturer());
                    Picasso.get().load(product.getImage()).into(pImage);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void addingToCart(){
        String saveTime, saveDate;

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveTime = currentTime.format(calendar.getTime());

        DatabaseReference shopRef = FirebaseDatabase.getInstance().getReference().child("Shopping Cart");
        final HashMap<String, Object> cartMap = new HashMap<>();
        cartMap.put("pid",productID);
        cartMap.put("pName",pName.getText().toString().trim());
        cartMap.put("pPrice",pPrice.getText().toString().trim());
        cartMap.put("pDate",saveDate);
        cartMap.put("pTime",saveTime);
        cartMap.put("discount","");

       /* FirebaseDatabase.getInstance().getReference("Users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Shopping Cart").push()
                .setValue(cartMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                });*/

        shopRef.child("User Cart").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Products").child(productID).updateChildren(cartMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            shopRef.child("Admin View").child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .child("Products").child(productID).updateChildren(cartMap)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                Toast.makeText(ProductDetails.this, "Added to cart",Toast.LENGTH_LONG).show();
                                                Intent intent = new Intent(ProductDetails.this,ShoppingCart.class);
                                                startActivity(intent);
                                            }
                                        }
                                    });
                        }
                    }
                });



    }
}