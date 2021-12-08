package com.example.mymedapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mymedapp.admin.BookListActivity;
import com.example.mymedapp.admin.ListActivity;
import com.example.mymedapp.helpers.PreferenceHelper;

public class doctorHomepage extends AppCompatActivity {
LinearLayout open_chats,open_book;

PreferenceHelper helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_homepage);
        open_chats=findViewById(R.id.open_chats);
        open_book=findViewById(R.id.open_book);
        helper=new PreferenceHelper(this);
        Toast.makeText(this, "Welcome:" +helper.getAdminEmail(), Toast.LENGTH_SHORT).show();
        open_chats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ListActivity.class));
            }
        });
        open_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), BookListActivity.class));
            }
        });
    }
}