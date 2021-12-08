package com.example.mymedapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymedapp.adapters.DoctorsAdapter;
import com.example.mymedapp.data.Doctor;
import com.example.mymedapp.test.ChatsActivity;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Doctors extends AppCompatActivity implements DoctorsAdapter.onItemClickListener {
//Button app,btn_chat;
    FirebaseFirestore db;
    RecyclerView recyclerView;
    List<Doctor> list;
    DoctorsAdapter adapter;
    String category;
public  Doctors(){

}
    public Doctors(String name, String id) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctors);
        category=getIntent().getStringExtra("category");
//        app=findViewById(R.id.appointments);
//btn_chat=findViewById(R.id.btn_chat);
//btn_chat.setOnClickListener(view->startActivity(new Intent(this,ChatActivity.class)));
//        app.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(),BaseActivity.class);
//                startActivity(intent);
//            }
//        });
        list=new ArrayList<>();
        db=FirebaseFirestore.getInstance();
        recyclerView=findViewById(R.id.doctors_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter=new DoctorsAdapter(list,this,this);

        fetchData();
    }

    private void fetchData() {
        db.collection("admin").whereEqualTo("category",category)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        if (e!=null)
                        {
                            return;
                        }
                        for (DocumentSnapshot doc:queryDocumentSnapshots) {

                            String cat = doc.get("category").toString();
                            if (cat.equals(category) || cat == category) {
                                String id = doc.getId();
                                String name = doc.get("name").toString();
                                //changes made from here
                           /* String speciality=doc.get("speciality").toString();
                            String experience=doc.get("experience").toString();
                            String hospital=doc.get("hospital").toString();
                            String fee=doc.get("fee").toString();

                            */

                                Doctor doctor = new Doctor(name, id);
                                list.add(doctor);
                                recyclerView.setAdapter(adapter);
                            }
                        }
                    }
                });
    }

    @Override
    public void onVideoButtonClicked(int position, View v) {
        Toast.makeText(this, "Video call button clicked", Toast.LENGTH_SHORT).show();
        Intent intent=new Intent(getApplicationContext(),Chatboard.class);
        startActivity(intent);
    }

    @Override
    public void onChatButtonClicked(int position, View v) {
        Toast.makeText(this, "Chat button clicked", Toast.LENGTH_SHORT).show();
        Doctor item=list.get(position);
        String name=item.getName();
        String id=item.getId();
        /*String speciality=item.getSpeciality();
        String experience=item.getExperience();
        String hospital=item.getHospital();
        StrinAQ1g fee=item.getFee();

         */
        Intent intent=new Intent(getApplicationContext(), ChatsActivity.class);
        intent.putExtra("id",id);
        intent.putExtra("docId",id);
        intent.putExtra("docName",name);
        intent.putExtra("key","patient");
        /*intent.putExtra("speciality",speciality);
        intent.putExtra("experience",experience);
        intent.putExtra("hospital",hospital);
        intent.putExtra("fee",fee);

         */
        startActivity(intent);
    }

    @Override
    public void onAppointmentButtonClicked(int position, View v) {
        Toast.makeText(this, "Appointments Button Clicked", Toast.LENGTH_SHORT).show();
        Doctor item=list.get(position);
        String name=item.getName();
        String id=item.getId();
        Intent intent = new Intent(getApplicationContext(),BaseActivity.class);
        intent.putExtra("name",name);
        intent.putExtra("id",id);

        startActivity(intent);
    }
}