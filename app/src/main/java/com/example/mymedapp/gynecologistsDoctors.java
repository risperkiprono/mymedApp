package com.example.mymedapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymedapp.adapters.gynecologistsAdapter;
import com.example.mymedapp.adapters.gynecologistsAdapter.onItemClickListener;
import com.example.mymedapp.data.gynecologists;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class gynecologistsDoctors extends AppCompatActivity implements onItemClickListener {
    FirebaseFirestore db;
    RecyclerView recyclerView;
    java.util.List<gynecologists> list;
    gynecologistsAdapter Adapter;
    private int position;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctorsgynecologists);

       list=new ArrayList<>();
        db=FirebaseFirestore.getInstance();
        recyclerView=findViewById(R.id.doctors_recycler1);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Adapter=new gynecologistsAdapter(list,this,this);

        fetchData();


    }

    private void fetchData() {
        db.collection("doctors").document("gynecologists")
                .collection("doctors")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {





                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            return;
                        }
                        for (DocumentSnapshot doc : queryDocumentSnapshots) {
                            String id = doc.getId();
                            String name = doc.get("name").toString();
                           Doctors doctors = new Doctors(name, id);
                           // boolean add = list.add(gynecologists);


                            recyclerView.setAdapter(Adapter);

                        }


                    }
                });
    }

    @Override
    public void onVideoButtonClicked(int adapterPosition, View v) {
        Toast.makeText(this, "Video call button clicked", Toast.LENGTH_SHORT).show();
        Intent intent=new Intent(getApplicationContext(),Chatboard.class);
        startActivity(intent);


    }

    @Override
    public void onChatButtonClicked(int adapterPosition, View v) {
        Toast.makeText(this, "Chat button clicked", Toast.LENGTH_SHORT).show();
        gynecologists item=list.get(adapterPosition);
        String name=item.getName();
        String id=item.getId();

        Intent intent=new Intent(getApplicationContext(),ChatActivity.class);
        intent.putExtra("name",name);
        intent.putExtra("id",id);

    }

    @Override
    public void onAppointmentButtonClicked(int adapterPosition, View v) {
        Toast.makeText(this, "Appointments Button Clicked", Toast.LENGTH_SHORT).show();
        gynecologists item=list.get(adapterPosition);
        String name=item.getName();
        String id=item.getId();
        Intent intent = new Intent(getApplicationContext(),BaseActivity.class);
        intent.putExtra("name",name);
        intent.putExtra("id",id);

        startActivity(intent);

    }
}

