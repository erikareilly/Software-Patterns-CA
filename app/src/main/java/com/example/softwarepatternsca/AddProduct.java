package com.example.softwarepatternsca;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class AddProduct extends AppCompatActivity {
    private ImageView InputProductimage;
    private Button add_productText;
    private EditText product_nameText, product_manufacturerText, product_priceText, product_categoryText;
    private static final int galleryPick =1;
    private Uri Imageuri;
    private String saveCurrentDate, saveCurrentTime, productKey, downloadImage;
    private StorageReference productImageRef;
    String name, manufacturer,price,category;
    private DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        reference = FirebaseDatabase.getInstance().getReference().child("Products");
        productImageRef= FirebaseStorage.getInstance().getReference().child("Product Images");
        InputProductimage = (ImageView) findViewById(R.id.select_image);
        add_productText = (Button) findViewById(R.id.add_product);
        product_nameText = (EditText) findViewById(R.id.product_name);
        product_manufacturerText = (EditText) findViewById(R.id.product_manufacturer);
        product_priceText = (EditText) findViewById(R.id.product_price);
        product_categoryText = (EditText) findViewById(R.id.product_category);

        InputProductimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenGallery();
            }
        });

        add_productText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ValidateProductData();
            }
        });
    }



    private void OpenGallery() {
        Intent gallery = new Intent();
        gallery.setAction(Intent.ACTION_GET_CONTENT);
        gallery.setType("image/*");
        startActivityForResult(gallery, galleryPick);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

            Imageuri = data.getData();
            InputProductimage.setImageURI(Imageuri);

    }

    private void ValidateProductData() {
         name = product_nameText.getText().toString().trim();
         manufacturer = product_manufacturerText.getText().toString().trim();
         price = product_priceText.getText().toString().trim();
         category = product_categoryText.getText().toString().trim();

        if(Imageuri ==null){
            Toast.makeText(this, "Image needed to create product", Toast.LENGTH_LONG).show();
        }
        if(name.isEmpty()){
            product_nameText.setError("Input name");
        }
        if(manufacturer.isEmpty()){
            product_manufacturerText.setError("Input Manufacturer");
        }
        if(price.isEmpty()){
            product_priceText.setError("Input Price");
        }
        if(category.isEmpty()){
            product_categoryText.setError("Input Category");
        }

        StoreProductInfo();
    }

    private void StoreProductInfo() {
        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
         saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime=currentTime.format(calendar.getTime());

        productKey = saveCurrentDate+saveCurrentTime;

        StorageReference filePath = productImageRef.child(Imageuri.getLastPathSegment());
        final UploadTask uploadTask = filePath.putFile(Imageuri);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
               String message = e.toString();
               Toast.makeText(AddProduct.this, "Error "+ message,Toast.LENGTH_LONG).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(AddProduct.this,"Image uploaded successfully", Toast.LENGTH_SHORT).show();
                Task<Uri> urlTask=uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if(!task.isSuccessful()){
                            throw task.getException();
                        }

                        downloadImage = filePath.getDownloadUrl().toString();
                        return filePath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                       if(task.isSuccessful()){
                           downloadImage = task.getResult().toString();
                           Toast.makeText(AddProduct.this,"Image Url received successfully", Toast.LENGTH_LONG).show();
                            saveProductInfo();

                       }
                    }
                });

            }
        });
    }

    private void saveProductInfo(){
        HashMap<String, Object> productMap = new HashMap<>();
        productMap.put("pid", productKey);
        productMap.put("image", downloadImage);
        productMap.put("date", saveCurrentDate);
        productMap.put("time", saveCurrentTime);
        productMap.put("name", name);
        productMap.put("manufacturer", manufacturer);
        productMap.put("price", price);
        productMap.put("category", category);

        reference.child(productKey).updateChildren(productMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(AddProduct.this, "Product added successfully", Toast.LENGTH_LONG).show();
                }else{
                    String message = task.getException().toString();
                    Toast.makeText(AddProduct.this,"Error"+ message, Toast.LENGTH_LONG).show();
                }
            }
        });





    }

}