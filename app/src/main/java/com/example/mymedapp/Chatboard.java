package com.example.mymedapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Chatboard extends AppCompatActivity {

    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatboard);

        //taking reference to button
        Button submit=findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                String text="this is a test";
                String toNumber= "254706352557";
                Intent intent=new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("http://api.whatsapp.com/send?phone=" + toNumber + "$text=" + text));
                startActivity(intent);

            }catch(Exception e){
                e.printStackTrace();
            }
        }
    });
    }
}
