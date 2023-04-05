package com.example.softwarepatternsca;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserProfile extends AppCompatActivity implements View.OnClickListener {

    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;
    private Button button;
    TextView displayUserType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(this);

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();

        final TextView displayTextName = (TextView) findViewById(R.id.name);
        final TextView displayTextNumber = (TextView) findViewById(R.id.number);
        final TextView displayTextEmail = (TextView) findViewById(R.id.emailText);
         displayUserType = (TextView) findViewById(R.id.userDef);

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);

                if (user != null) {
                    String name = user.na;
                    String phone = user.ph;
                    String email = user.em;
                    boolean admin = user.admin;

                    displayTextName.setText(name);
                    displayTextNumber.setText(phone);
                    displayTextEmail.setText(email);
                    if (admin) {
                        displayUserType.setText("Admin");
                    } else {
                        displayUserType.setText("Customer");
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UserProfile.this, "Something went wrong!", Toast.LENGTH_LONG).show();

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                if(displayUserType.equals("Admin")){
                    Intent intent = new Intent(UserProfile.this, Homepage.class);
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(UserProfile.this, CustomerHomepage.class);
                    startActivity(intent);
                }
                break;

        }

    }
}