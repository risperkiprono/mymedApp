package com.example.mymedapp;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.User;

public class Register extends AppCompatActivity {
    private static final String TAG = "Register";
    private static final String KEY_FirstName = "FirstName";
    private static final String KEY_LastName = "LastName";
    private static final String KEY_Email = "Email";
    private static final String KEY_Password = "Password";

     FirebaseFirestore db;
    Button register;
    User user;
     EditText FirstName, LastName, Password, Email;
    ProgressDialog pa;
    CollectionReference reference;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        pa=new ProgressDialog(this);
        register = findViewById(R.id.btnRegister);
        FirstName = findViewById(R.id.editTextTextPersonName);
        LastName = findViewById(R.id.editTextTextPersonName2);
        Email = findViewById(R.id.editTextTextEmailAddress);
        Password = findViewById(R.id.editTextTextPassword);
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        Toast.makeText(Register.this,"r",Toast.LENGTH_SHORT).show();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String f = FirstName.getText().toString().trim();
                String l = LastName.getText().toString().trim();
                String e = Email.getText().toString().trim();
                String p = Password.getText().toString().trim();
             /*   Map<String, Object> note = new HashMap<>();
                note.put(KEY_FirstName, f);
                note.put(KEY_LastName, l);
                note.put(KEY_Email, e);
                note.put(KEY_Password, p);

              */
                pa.show();
                pa.setMessage("please wait.");

                        mAuth.createUserWithEmailAndPassword(e,p)
                                .addOnCompleteListener(Register.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        Toast.makeText(Register.this,"Vervcc"+task.toString(),Toast.LENGTH_SHORT).show();

                                        if(task.isSuccessful()){
                                            pa.dismiss();
                                            FirebaseUser fuser= mAuth.getCurrentUser();
                                            fuser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                   Toast.makeText(Register.this,"Verification mail has been send to your mail"+e,Toast.LENGTH_SHORT).show();
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(Register.this,"Vervcc"+e.toString(),Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                         // User info = new User(FirstName,LastName,Email,Pas())

                                        }
                                    }
                                });
                       /* .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                pa.dismiss();
                                Toast.makeText(Register.this, "Register Successfully", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Register.this, "Failed to Register", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, e.toString());

                        */
                        pa.dismiss();

            }});}}






