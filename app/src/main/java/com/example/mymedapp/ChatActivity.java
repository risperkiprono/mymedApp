package com.example.mymedapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymedapp.adapters.MessageAdapter;
import com.example.mymedapp.data.Message;
import com.example.mymedapp.helpers.PreferenceHelper;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {
    EditText mess;
    Button send;
    FirebaseFirestore db;
    List<Message> list;
    MessageAdapter adapter;
    RecyclerView recyclerView;
    String name,id,key;
    PreferenceHelper helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
db=FirebaseFirestore.getInstance();
helper=new PreferenceHelper(getApplicationContext());
list=new ArrayList<>();
send=findViewById(R.id.send_message_btn);
recyclerView=findViewById(R.id.message_recycler);
recyclerView.setLayoutManager(new LinearLayoutManager(this));
adapter=new MessageAdapter(list,this);
id=getIntent().getStringExtra("email");
name=getIntent().getStringExtra("name");
key=getIntent().getStringExtra(key);
if(key.equals("admin")||key=="admin")
{
    getMessagesFromAdmin();
}
else if (key.equals("patient") || key=="patient")
{
    getMessagesFromPatient();
}
mess=findViewById(R.id.message_text);
//creating root reference
//FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
//        Query query = rootRef.collection("doctors")
//                .orderBy("speciality",Query.Direction.ASCENDING);
//        //setting fireStore recycler options
//      //  FirestoreRecyclerOptions<Message> options = new FirestoreRecyclerOptions.Builder<Message>()
//             //   .setQuery(query, Message.class)
//               // .build();
//
//
//        db.collection("doctors")
//                .document("sam")
//                .collection("messages")
//               .addSnapshotListener((value, e) -> {
//                   list.clear();
//                   if (e != null) {
//                       Log.w("AG", "Listen failed.", e);
//                       return;
//                   }
//                   for (QueryDocumentSnapshot doc : value) {
//                     if (doc.exists())
//                     {
//                     String name=doc.getString("message");
//                         Message message = new Message(name);
//                         list.add(message);
//                        recyclerView.setAdapter(adapter);
//                     }
//                   }
//               });
send.setOnClickListener(v -> sendMessage());
    }

    private void getMessagesFromPatient() {
        db.collection("admin")
                .document(id)
                .collection("messages")
                .document(helper.getUserEmail())
                .collection("chats").orderBy("time",Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        if (e!=null)
                        {
                            return;
                        }
                        list.clear();
                        for (DocumentSnapshot doc:queryDocumentSnapshots)
                        {
                            String message=doc.get("message")
                                    .toString();
                            Message mess=new Message(message);
                            list.add(mess);
                            adapter.notifyDataSetChanged();
                        }

                    }
                });
    }

    private void getMessagesFromAdmin()
    {
        db.collection("admin")
                .document(helper.getAdminEmail())
                .collection("messages")
                .document(id)
                .collection("chats").orderBy("time",Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        if (e!=null)
                        {
                            return;
                        }
                        list.clear();
                        for (DocumentSnapshot doc:queryDocumentSnapshots)
                        {
                            String message=doc.get("message")
                                    .toString();
                            Message mess=new Message(message);
                            list.add(mess);
                            adapter.notifyDataSetChanged();
                        }

                    }
                });

    }


    private void sendMessage()
    {
        list.clear();
        Message message=new Message(mess.getText().toString());
        db.collection("doctors")
                .document("sam")
                .collection("messages")
                .document()
                .set(message)
                .addOnSuccessListener(task-> {Toast.makeText(this, "Message sent successfully!", Toast.LENGTH_SHORT).show();

                });
    }
}