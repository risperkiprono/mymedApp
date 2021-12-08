package com.example.mymedapp;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mymedapp.helpers.PreferenceHelper;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class BaseActivity extends Activity implements AdapterView.OnItemSelectedListener{

    Spinner spinner;
    DatabaseReference databaseReference;
    Button bookbt,datebtn;
    TextView tvdate;
    DatePickerDialog.OnDateSetListener setListener;
    List<String>names;
    PreferenceHelper helper;
    EditText time;
    FirebaseFirestore db;
    String doctor;
    List<String> doc=new ArrayList<>();
    ArrayAdapter aa;
    AutoCompleteTextView localTv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        spinner= findViewById(R.id.doc_spinner);
        bookbt=findViewById(R.id.bookbt);
        datebtn=findViewById(R.id.datebt);
        tvdate= findViewById(R.id.tvdate);
        names= new ArrayList<>();
        time=findViewById(R.id.tvtime);
        db=FirebaseFirestore.getInstance();
        helper=new PreferenceHelper(getApplicationContext());
        localTv=findViewById(R.id.localtv);


      aa= new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, doc);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setOnItemSelectedListener(this);
db.collection("admin").get().addOnSuccessListener(queryDocumentSnapshots -> {
    for (DocumentSnapshot dco:queryDocumentSnapshots)
    {
        String name=dco.get("name").toString();
        doc.add(name);
        spinner.setAdapter(aa);
        aa.notifyDataSetChanged();
    }

});



        bookbt.setOnClickListener(v -> {
            db.collection("admin").whereEqualTo("name",doctor).get()
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            for (DocumentSnapshot doc:queryDocumentSnapshots) {
                                Intent intent = new Intent(getApplicationContext(), ViewActivity.class);
                                intent.putExtra("time", time.getText().toString());
                                intent.putExtra("date", tvdate.getText().toString());
                                intent.putExtra("doctor", doctor);
                                intent.putExtra("locality", localTv.getText().toString());
                                intent.putExtra("specs",doc.get("category").toString());
                                intent.putExtra("docEmail",doc.getId());
                                startActivity(intent);
                            }
                        }
                    });

        });


        Calendar calendar= Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

       datebtn.setOnClickListener(v -> {
           DatePickerDialog datePickerDialog = new DatePickerDialog(
                   BaseActivity.this, (view, year1, month1, day1) -> {
               month1 = month1 +1;
               String date = day1 +"/"+ month1 +"/"+ year1;
               tvdate.setText(date);
           },year,month,day);
           datePickerDialog.show();
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

     doctor= spinner.getSelectedItem().toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Toast.makeText(this, "Nothing Selected!!", Toast.LENGTH_SHORT).show();
    }
}
