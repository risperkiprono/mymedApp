package com.example.mymedapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class changepassword extends AppCompatActivity {
Button reset;
EditText cemail;
FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changepassword);
        reset= findViewById(R.id.reset);
        cemail= findViewById(R.id.cemail);
        mAuth=FirebaseAuth.getInstance();

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailPattern = "[a-z0-9]+@[a-z]+\\.+[a-z]+";
                final String ResetMail;
                ResetMail = reset.getEditableText().toString().trim();
                Log.d("Tag","emails:"+ResetMail);
                if(ResetMail.isEmpty()){

                    Toast.makeText(changepassword.this,"please enter your email address",Toast.LENGTH_SHORT).show();
                }
                else if(!ResetMail.matches(emailPattern)){
                    Toast.makeText(changepassword.this,"please enter valid email address",Toast.LENGTH_SHORT).show();
                }
                else if(ResetMail.matches(emailPattern)){
                    mAuth.sendPasswordResetEmail(ResetMail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(changepassword.this,"Reset password a link Has Been send to your email "+ResetMail,Toast.LENGTH_SHORT).show();
                                Intent intent= new Intent(changepassword.this,MainActivity.class);
                                startActivity(intent);
                            }
                            else{
                                Toast.makeText(changepassword.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });
    }
}