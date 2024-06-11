package com.example.projectpark;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class AddActivity extends AppCompatActivity {

    EditText pemilik,merk,nama,plat,turl;
    Button btnAdd,btnBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        pemilik = (EditText)findViewById(R.id.txtOwnerName);
        merk = (EditText)findViewById(R.id.txtMerk);
        nama = (EditText)findViewById(R.id.txtVName);
        plat = (EditText)findViewById(R.id.txtPlat);
        turl = (EditText)findViewById(R.id.txtPhotoUrl);

        btnAdd = (Button)findViewById(R.id.btnAdd);
        btnBack = (Button)findViewById(R.id.btnBack);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InsertData();
                ClearAll();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void InsertData()
    {
        Map<String,Object> map = new HashMap<>();
        map.put("pemilik",pemilik.getText().toString());
        map.put("merk",merk.getText().toString());
        map.put("nama",nama.getText().toString());
        map.put("plat",plat.getText().toString());
        map.put("turl",turl.getText().toString());

        FirebaseDatabase.getInstance().getReference().child("parking").push()
                .setValue(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(AddActivity.this, "Data Inserted Successfully.", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {
                        Toast.makeText(AddActivity.this,"Error while Insertion",Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void ClearAll()
    {
        pemilik.setText("");
        merk.setText("");
        nama.setText("");
        plat.setText("");
        turl.setText("");
    }

    public void onBackPressed(){
        Intent intent = new Intent(AddActivity.this, MainActivity.class);
        startActivity(intent);
    }
}