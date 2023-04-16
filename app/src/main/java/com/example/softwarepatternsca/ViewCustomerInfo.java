package com.example.softwarepatternsca;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ViewCustomerInfo extends AppCompatActivity {

    private FirebaseUser user;
    private DatabaseReference reference, areference;
    private String userID;
    TextView addressText,paymentInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_customer_info);

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Payment Method");
        areference = FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Address");
        userID = user.getUid();

        addressText = (TextView) findViewById(R.id.addressText);
        paymentInfo = (TextView) findViewById(R.id.paymentText);

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Payment payment = snapshot.getValue(Payment.class);
                if(payment !=null){
                    String na = payment.name;
                    long nu = payment.number;
                    int cv = payment.cvv;
                    int ex = payment.expiry;

                    paymentInfo.setText(na+nu+cv+ex);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ViewCustomerInfo.this, "Something went wrong!", Toast.LENGTH_LONG).show();

            }
        });

        areference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Address address = snapshot.getValue(Address.class);
                if(address!=null){
                    String ho = address.house;
                    String st = address.street;
                    String co = address.county;
                    String cu = address.country;
                    String ei = address.eircode;

                    addressText.setText(ho+st+co+cu+ei);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ViewCustomerInfo.this, "Something went wrong!", Toast.LENGTH_LONG).show();

            }
        });

    }
}