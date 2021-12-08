package com.example.mymedapp.admin;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymedapp.R;
import com.example.mymedapp.adapters.BookAdapter;
import com.example.mymedapp.data.Book;
import com.example.mymedapp.helpers.PreferenceHelper;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class BookListActivity extends AppCompatActivity implements BookAdapter.OnItemClickListener {
RecyclerView recyclerView;
BookAdapter adapter;
List<Book> list=new ArrayList<>();
FirebaseFirestore db;
PreferenceHelper helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);
        recyclerView=findViewById(R.id.book_recycler);
        db=FirebaseFirestore.getInstance();
        helper=new PreferenceHelper(this);
        adapter=new BookAdapter(list,this,this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        getData();
    }

    private void getData() {
        db.collection("admin")
                .document(helper.getAdminEmail())
                .collection("bookings")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                       for(DocumentSnapshot doc:queryDocumentSnapshots)
                       {
                           Book book=new Book(doc.get("email").toString(),doc.get("time").toString(),doc.get("date").toString());
                           list.add(book);
                           adapter.notifyDataSetChanged();
                       }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText(BookListActivity.this, "Failed to fetch requests", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onItemClick(int position, View v) {
        Toast.makeText(this, "Item clicked!", Toast.LENGTH_SHORT).show();
    }
}