package com.example.mymedapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mymedapp.data.Book;
import com.example.mymedapp.helpers.PreferenceHelper;
import com.google.firebase.firestore.FirebaseFirestore;

public class ViewActivity extends AppCompatActivity {
    String name,phone,speciality,date,time,docEmail;
    Button book,cancel;
    TextView tvname,tv_phone,tv_spec,tv_date,tv_time,tv_location;
    PreferenceHelper helper;
    FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view2);
        helper=new PreferenceHelper(this);
        db=FirebaseFirestore.getInstance();
    tvname=findViewById(R.id.nametv);
    tv_phone=findViewById(R.id.phnotv);
    tv_spec=findViewById(R.id.specialtv);
    tv_date=findViewById(R.id.datetv);
    tv_time=findViewById(R.id.timetv);
   book=findViewById(R.id.bookbt);
   cancel=findViewById(R.id.cancelbt);
    name=getIntent().getStringExtra(helper.getUserEmail());
    speciality=getIntent().getStringExtra("specs");
    time=getIntent().getStringExtra("time");
     date=getIntent().getStringExtra("date");
        docEmail=getIntent().getStringExtra("docEmail");



    ///set items
        tv_spec.setText(speciality);
        tvname.setText(helper.getUserEmail());
        tv_time.setText(time);
        tv_date.setText(date);
        tv_spec.setText(speciality);

        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Book book=new Book(helper.getUserEmail(),time,date);
                db.collection("admin")
                        .document(docEmail)
                        .collection("bookings")
                        .document(helper.getUserEmail())
                        .set(book)
                        .addOnSuccessListener(aVoid -> Toast.makeText(ViewActivity.this, "Book request sent successfully!!!", Toast.LENGTH_SHORT).show())
                        .addOnFailureListener(e -> Toast.makeText(ViewActivity.this, "Failed to send request!", Toast.LENGTH_SHORT).show());

            }
        });
        cancel.setOnClickListener(v -> {
            Intent intent=new Intent(getApplicationContext(),BaseActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
            Toast.makeText(getApplicationContext(), "Book request cancelled successfully!!!", Toast.LENGTH_SHORT).show();
        });
//
//        Context context = null;
//
//        AlertDialog.Builder builder1= new AlertDialog.Builder(context);
//        builder1.setMessage("do you want to cancel the appointment?");
//        builder1.setCancelable(true);
//        builder1.setPositiveButton("OK",
//                new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                       dialog.cancel();
//                    }
//                });
//        builder1.setNegativeButton("Cancel",
//                new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.cancel();
//                    }
//                });
//        AlertDialog alert11= builder1.create();
//        alert11.show();
    }
}