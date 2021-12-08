package com.example.mymedapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class Findandbook extends AppCompatActivity {
    //Declaring variables
    Toolbar toolbar;
  ImageView general,gynecologist;
  LinearLayout wellness;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_activity_findandbook);
        //initializing variables
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        general=findViewById(R.id.general);
        gynecologist=findViewById(R.id.gynecologist);
        wellness=findViewById(R.id.mental_wellness);
        wellness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),Doctors.class);
                intent.putExtra("category","Mental wellness");
                startActivity(intent);
            }
        });
        gynecologist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Doctors.class));
            }
        });


general.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent=new Intent(getApplicationContext(),Doctors.class);
        intent.putExtra("category","Women Health");
        startActivity(intent);
    }

});

        //making home button enabled in toolbar
        if (getActionBar()!=null){
            getSupportActionBar().setHomeButtonEnabled(true);
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}