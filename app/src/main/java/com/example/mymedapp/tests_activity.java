package com.example.mymedapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class tests_activity extends AppCompatActivity {
    ProgressDialog progressDialog;
    EditText editTextEmail,editTextPassword;
    FirebaseAuth firebaseAuth;
    private FirebaseUser user;
    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tests_activity);
editTextEmail=findViewById(R.id.email);
editTextPassword=findViewById(R.id.passWord);
 firebaseAuth=FirebaseAuth.getInstance();
 //ref=DatabaseReference.getReference;



            String email = editTextEmail.getText().toString().trim();
            String password = editTextPassword.getText().toString().trim();

            if(TextUtils.isEmpty(email)){
                Toast.makeText(this, "Please Enter Emailsss", Toast.LENGTH_SHORT).show();
                return;
            }
            if(TextUtils.isEmpty(password)){
                Toast.makeText(this, "Please Enter Your PASSWORDS", Toast.LENGTH_SHORT).show();
                return;
            }

            progressDialog.setMessage("Login....");
            progressDialog.show();

            firebaseAuth.signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressDialog.dismiss();
                            if(task.isSuccessful()){
                                onAuthSuccess(task.getResult().getUser());
                                //Toast.makeText(signinActivity.this, "Successfully Signed In", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(tests_activity.this, "Could not login, password or email wrong , bullcraps", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }



    private void onAuthSuccess(FirebaseUser user) {

            //String username = usernameFromEmail(user.getEmail())
            if (user != null) {
                //Toast.makeText(signinActivity.this, user.getUid(), Toast.LENGTH_SHORT).show();
                ref = FirebaseDatabase.getInstance().getReference().child("users").child(user.getUid()).child("type");
                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String value = dataSnapshot.getValue(String.class);
                        //for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                        // Toast.makeText(signinActivity.this, value, Toast.LENGTH_SHORT).show();
                        if(Integer.parseInt(value) == 1) {
                            //String jason = (String) snapshot.getValue();
                            //Toast.makeText(signinActivity.this, jason, Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(tests_activity.this, Homepage.class));
                            Toast.makeText(tests_activity.this, "You're Logged in as Patient", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            startActivity(tests_activity.this, "You're Logged in as Doctor", Toast.LENGTH_SHORT);
                            finish();
                        }
                    }

                    private void startActivity(Intent intent) {

                    }

                    private void startActivity(tests_activity tests_activity, String s, int lengthShort) {
                        return ;
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }

        }

    public void startActivity(Intent intent) {
        return;
    }


}

