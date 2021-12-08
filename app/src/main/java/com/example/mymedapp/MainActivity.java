package com.example.mymedapp;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mymedapp.helpers.PreferenceHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.User;

import static android.provider.ContactsContract.Intents.Insert.EMAIL;
import static android.provider.Telephony.Carriers.PASSWORD;

public class MainActivity extends AppCompatActivity {
    private static final String TAG_EMAIL =EMAIL;
    private static final String TAG_PASSWORD =PASSWORD;

    EditText email, passWord;
    String emails, passwords;
    Button Login;
    Button register;
    TextView open_forgot;
    CheckBox checkBox;
    FirebaseFirestore db;
    User user;
    private Object User;
    ImageView facebook, google;
    private FirebaseAuth mAuth;
    AlertDialog.Builder builder;
    CollectionReference dbUsers;
    public static  final String STUDENT_EMAIL="student_email";
    private Object isUserLoggedIn;
    ACProgressFlower pa;
    ProgressDialog dialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //initialize of views
        mAuth = FirebaseAuth.getInstance();
        open_forgot = findViewById(R.id.open_forgot);
        register = findViewById(R.id.register);
        Login = findViewById(R.id.Login);
        facebook = findViewById(R.id.facebook);
        google = findViewById(R.id.google);
        builder = new AlertDialog.Builder(this);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        email = findViewById(R.id.email);
        checkBox=(CheckBox) findViewById(R.id.usercheck);
        pa=new ACProgressFlower(this);
        dialog=new ProgressDialog(this);
      /* email.addTextChangedListener(new TextWatcher() {
          //  @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
              emails = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

       */
        passWord = findViewById(R.id.passWord);
        dbUsers = db.collection("users");

     /*   if (PrefUtilities.With(this.isUserLoggedIn)){
            Intent intent = new Intent(MainActivity.this,Homepage.class);
            intent.putExtra(STUDENT_EMAIL,emails);
            startActivity(intent);
        }
*/


        //btn to submit data to firebase
        {
            register.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getApplicationContext(), Register.class));
                }
            });
        }
        open_forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), changepassword.class));

            }
        });
        Login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

//                    if (TextUtils.isEmpty(emails)) {
//                        email.setError("Email is required");
//                        return;
//                    }
//                    if (TextUtils.isEmpty(passwords)) {
//                        passWord.setError("password is required");
//                        return;
//                    }

                    if (checkBox.isChecked())
                    {
                        loginAsAdmin();
                    }
                    else {
                        // fetches the emails and passwords from the user inputs ands sends  it to the firebase


                        // progressDialog.setMessage("Login....");
                        //progressDialog.show();
                     /*final ACProgressFlower dialoglogin = new ACProgressFlower.Builder(MainActivity.this)
                             .direction(ACProgressConstant.DIRECT_CLOCKIWISE)
                             .themeColor(Color.WHITE)
                             .text("logging in")
                             .fadeColor(Color.DKGRAY().build();
                             dialoglogin.show();

                      */
                        String emails = email.getText().toString().trim();
                        String passwords = passWord.getText().toString().trim();
dialog.show();
dialog.setMessage("Verifying....");

                        mAuth.signInWithEmailAndPassword(emails, passwords)
                                .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            dialog.dismiss();
                                            if (mAuth.getCurrentUser().isEmailVerified()) {
                                                PreferenceHelper helper=new PreferenceHelper(getApplicationContext());
                                                helper.saveUserEmail(emails);
                                                dialog.dismiss();
                                                //progressDialog.dismiss();
                                                Toast.makeText(MainActivity.this, "logged in successfilly", Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(MainActivity.this, Homepage.class);
                                                startActivity(intent);
                                                finish();
                                            } else if (!mAuth.getCurrentUser().isEmailVerified()) {
                                                Toast.makeText(MainActivity.this, "please verify on your email", Toast.LENGTH_SHORT).show();
                                            }

                                        } else {
                                            Toast.makeText(MainActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                });
                    }

                    open_forgot.setOnClickListener(v1 -> {



   /* public void  Login(View view){
        if(validate()){
            DocumentReference dbDocs = dbUsers.document(emails);
            dbDocs.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                   if(task.isSuccessful()) {
                       DocumentSnapshot document = task.getResult();
                       if(document != null && document.exists()){
                           passCheck(document);
                       }
                       else{
                           Toast.makeText(MainActivity.this,"You are not registered",Toast.LENGTH_LONG).show();
                       }
                   }
                   else{
                       Toast.makeText(MainActivity.this,"Unable to connect to database",Toast.LENGTH_LONG).show();
                   }
                }
            });
        }
    }

    private boolean validate() {
       return true;
    }

    */


   /*     public PrefUtilities(Context context) {

        }

        public static boolean With(Object isUserLoggedIn) {
            return  true;
        }

        private PrefUtilities PrefUtilities(Context context){
            return new PrefUtilities(context);

        }
        public void setUserLogin(boolean isUserLoggedIn){
            preferences.edit().putBoolean(context.getString(R.string.pref_key_user_status),isUserLoggedIn).apply();
        }
        public boolean isUserLoggedIn(){
            return preferences.getBoolean(context.getString(R.string.pref_key_user_status),false);
        }
    }

    private void updateUI(FirebaseUser user) {
    }

    */
                    });
                }
        });
    }

  public void loginAsAdmin() {
        Toast.makeText(getApplicationContext(),"Email exifdhfhfghdfhfhfhfsts",Toast.LENGTH_LONG);
       db= FirebaseFirestore.getInstance();
        db.collection("admin")
                .document(email.getText().toString())
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot snapshot) {
                if (snapshot.exists())
                {
                    Toast.makeText(getApplicationContext(),"Email exists",Toast.LENGTH_LONG);
                    String passw=snapshot.get("password").toString();
                    if(passw.equals(passWord.getText().toString())||passw==passWord.getText().toString())
                    {
                        PreferenceHelper helper=new PreferenceHelper(getApplicationContext());
                        helper.saveAdminValues(email.getText().toString());
                        Toast.makeText(MainActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this, doctorHomepage.class);
                        startActivity(intent);
                        //staret admin
                    }
                    }
                else
                {
                    Toast.makeText(MainActivity.this, "Email does not exist", Toast.LENGTH_SHORT).show();
                }
               }
        });

    }

   /* public void onCheckedClicked(View view) {
        boolean session = false;
        String user_type = null;

        if (session && user_type.contentEquals("admin")) {
            Intent intent = new Intent(MainActivity.this, doctorHomepage.class);
            intent.putExtra(TAG_EMAIL, emails);
            intent.putExtra(TAG_PASSWORD, passwords);
            finish();
            startActivity(intent);

        } else {
            Intent intent = new Intent(MainActivity.this, Homepage.class);
            intent.putExtra(TAG_EMAIL,emails);
            intent.putExtra(TAG_PASSWORD, passwords);
            finish();
            startActivity(intent);
        }

    */
    }

