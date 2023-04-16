package com.example.softwarepatternsca;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class SavePayment extends AppCompatActivity {

    private EditText nameText, numberText, cvvText, expiryText;
    private Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        BottomNavigationView bottomNav = (BottomNavigationView) findViewById(R.id.bottomNavigation);
        bottomNav.setSelectedItemId(R.id.home);
        bottomNav.setSelectedItemId(R.id.person);
        bottomNav.setSelectedItemId(R.id.settings);

        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()) {
                    case R.id.person:
                        startActivity(new Intent(getApplicationContext(), UserProfile.class));
                        return true;
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), Homepage.class));

                        return true;
                    case R.id.settings:
                        startActivity(new Intent(getApplicationContext(), Settings.class));
                        return true;
                }
                return false;
            }
        });

        nameText = (EditText) findViewById(R.id.name);
        numberText = (EditText) findViewById(R.id.number);
        cvvText = (EditText) findViewById(R.id.cvv);
        expiryText = (EditText) findViewById(R.id.date);
        save = (Button) findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savePayment();
            }
        });
    }

    private void savePayment() {
        String name = nameText.getText().toString().trim();
        String nu = numberText.getText().toString().trim();
        long number = Long.parseLong(nu);
        String cv = cvvText.getText().toString().trim();
        int cvv =Integer.parseInt(cv);
        String ex = expiryText.getText().toString().trim();
        int expiry = Integer.parseInt(ex);

       if(numberText.length() !=16){
           numberText.setError("Card Number must be 16 digits");
       }

       if(name.isEmpty()){
           nameText.setError("Input name");
       }
       if(cvvText.length()!=3){
           cvvText.setError("CVV must be 3 digits");
       }
       if(expiryText.length()!=4){
           expiryText.setError("Date must include 4 digits");
       }

       Payment payment = new Payment(name,number,cvv,expiry);
        FirebaseDatabase.getInstance().getReference("Users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Payment Method").push()
                .setValue(payment).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()) {
                            Toast.makeText(SavePayment.this, "Payment method has been saved", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(SavePayment.this, Settings.class);
                            startActivity(intent);
                        }else{
                            Toast.makeText(SavePayment.this, "Failed to add Address", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


    }
}