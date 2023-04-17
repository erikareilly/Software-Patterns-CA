package com.example.softwarepatternsca;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class ConfirmOrder extends AppCompatActivity {

    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;
    private String totalAmount =null;

    String name, houseNo,street1,county1,country1,eircode1;
    EditText userName, houseText, streetText,countytext,countryText,eirText;
    Button confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);
        totalAmount = getIntent().getStringExtra("Total Price");
        Toast.makeText(ConfirmOrder.this,"Total Price: "+ totalAmount, Toast.LENGTH_LONG).show();
        //user = FirebaseAuth.getInstance().getCurrentUser();
      //  reference =FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Address").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
       // userID = user.getUid();


        confirm = (Button)findViewById(R.id.confirmB);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Check();
            }
        });
        userName = (EditText) findViewById(R.id.nameEdit);
        houseText = (EditText) findViewById(R.id.houseEdit);
        streetText = (EditText) findViewById(R.id.streetEdit);
        countytext = (EditText) findViewById(R.id.countyEdit);
        countryText = (EditText) findViewById(R.id.countryEdit);
        eirText = (EditText) findViewById(R.id.eirocdeEdit);


    }

    private void Check() {
        name= userName.getText().toString().trim();
        houseNo = houseText.getText().toString().trim();
        street1 = streetText.getText().toString().trim();
        county1 = countytext.getText().toString().trim();
        country1=countryText.getText().toString().trim();
        eircode1 = eirText.getText().toString().trim();

        if(name.isEmpty()){
            userName.setError("Enter Name");
        }

        if(houseNo.isEmpty()){
            houseText.setError("House Number Required");
        }
        if(street1.isEmpty()){
            streetText.setError("Street Required");
        }
        if(county1.isEmpty()){
            countytext.setError("County Required");
        }
        if(country1.isEmpty()){
            countryText.setError("Country Required");
        }
        if(eircode1.isEmpty()){
            eirText.setError("Eircode Required");
        }

        ConfirmUserOrder();
    }

    private void ConfirmUserOrder(){
       final String saveTime, saveDate;

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveTime = currentTime.format(calendar.getTime());

        final DatabaseReference orderRef = FirebaseDatabase.getInstance().getReference().child("Orders")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        HashMap<String, Object> orderMap = new HashMap<>();
        orderMap.put("TotalAmount",totalAmount);
        orderMap.put("name",userName.getText().toString().trim());
        orderMap.put("HouseNo",houseText.getText().toString().trim());
        orderMap.put("Street",streetText.getText().toString().trim());
        orderMap.put("County", countytext.getText().toString().trim());
        orderMap.put("Country",countryText.getText().toString().trim());
        orderMap.put("Eircode", eirText.getText().toString().trim());
        orderMap.put("OrderDate",saveDate);
        orderMap.put("OrderTime",saveTime);
        orderMap.put("state", "Not Shipped");
        orderRef.updateChildren(orderMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    FirebaseDatabase.getInstance().getReference().child("Shopping Cart").child("User Cart").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(ConfirmOrder.this, "Order placed Successfully", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(ConfirmOrder.this, CustomerHomepage.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                finish();
                            }
                        }
                    });
                }
            }
        });

    }
}