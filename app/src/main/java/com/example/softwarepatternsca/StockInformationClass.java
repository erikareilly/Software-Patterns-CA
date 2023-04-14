package com.example.softwarepatternsca;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

public class StockInformationClass extends AppCompatActivity {

    private ImageView laptop,alexa,ipad,phone,monitor,airpods,headphones,tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_information_class);
    }
}