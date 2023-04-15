package com.example.softwarepatternsca;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Settings extends AppCompatActivity {

    private Button addAddress, addPayment, viewAddress, viewPayment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        addAddress = (Button) findViewById(R.id.addAddress);
        addPayment = (Button) findViewById(R.id.addPayment);
        viewAddress = (Button) findViewById(R.id.viewAddress);
        viewPayment = (Button) findViewById(R.id.viewPayment);

        addAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Settings.this, SaveAddress.class);
                startActivity(intent);
            }
        });

        addPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Settings.this, SavePayment.class);
                startActivity(intent);
            }
        });

        viewAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Settings.this, ViewCustomerInfo.class);
                startActivity(intent);
            }
        });

        viewPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Settings.this, ViewCustomerInfo.class);
                startActivity(intent);
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
                        return true;
                }
                return false;
            }
        });

    }
}