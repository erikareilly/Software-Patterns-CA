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

public class SaveAddress extends AppCompatActivity{
    
    private EditText houseText, streetText, countyText, countryText, eircodeText;
    private Button save;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        
        houseText = (EditText) findViewById(R.id.house);
        streetText = (EditText) findViewById(R.id.street);
        countyText = (EditText) findViewById(R.id.county);
        countryText = (EditText) findViewById(R.id.country);
        eircodeText = (EditText) findViewById(R.id.eircode);
        save = (Button) findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveAddress();
            }
        });

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
    }



    private void saveAddress() {
        String house = houseText.getText().toString().trim();
        String street = streetText.getText().toString().trim();
        String county = countyText.getText().toString().trim();
        String country = countryText.getText().toString().trim();
        String eircode = eircodeText.getText().toString().trim();

        if(house.isEmpty()){
            houseText.setError("House number required");
        }
        if(street.isEmpty()){
            streetText.setError("Street name required");
        }
        if(county.isEmpty()){
            countyText.setText("County required");
        }
        if(country.isEmpty()){
            countryText.setText("Country required");
        }
        if (eircode.isEmpty()){
            eircodeText.setText("Eircode required");
        }

        Address address = new Address(house,street,county,country,eircode);
        FirebaseDatabase.getInstance().getReference("Users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Address").push()
                .setValue(address).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()) {
                            Toast.makeText(SaveAddress.this, "Address has been saved", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(SaveAddress.this, Settings.class);
                            startActivity(intent);
                        }else{
                            Toast.makeText(SaveAddress.this, "Failed to add Address", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
}