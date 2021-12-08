package com.example.mymedapp.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymedapp.R;
import com.example.mymedapp.adapters.UserAdapter;
import com.example.mymedapp.helpers.PreferenceHelper;
import com.example.mymedapp.test.ChatsActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity implements UserAdapter.OnItemClickListener{
    PreferenceHelper helper;
    FirebaseFirestore db;
    RecyclerView recyclerView;
    UserAdapter adapter;
    List<com.example.mymedapp.data.List> list=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        db=FirebaseFirestore.getInstance();
        helper=new PreferenceHelper(getApplicationContext());
        recyclerView=findViewById(R.id.list_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter=new UserAdapter(list,this,this);
        recyclerView.setAdapter(adapter);
        db.collection("admin")
                .document(helper.getAdminEmail())
                .collection("inbox")
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (DocumentSnapshot doc:queryDocumentSnapshots)
                {
                    String name=doc.get("name").toString();
                    String email=doc.getId();
                    com.example.mymedapp.data.List data=new com.example.mymedapp.data.List(name,email);
                    list.add(data);
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public void onItemClick(int position, View v) {
        com.example.mymedapp.data.List item=list.get(position);
        String email=item.getId();
        String name=item.getName();
        Intent intent=new Intent(getApplicationContext(), ChatsActivity.class);
        intent.putExtra("patientId",email);
        intent.putExtra("patientName",name);
        intent.putExtra("key","admin");
        startActivity(intent);
    }
}