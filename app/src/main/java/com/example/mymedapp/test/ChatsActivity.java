package com.example.mymedapp.test;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymedapp.R;
import com.example.mymedapp.adapters.MessagingAdapter;
import com.example.mymedapp.data.Messages;
import com.example.mymedapp.helpers.PreferenceHelper;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;

public class ChatsActivity extends AppCompatActivity {
RecyclerView recyclerView;
MessagingAdapter adapter;
List<Messages> list=new ArrayList<>();
   public String docId="xOG6JL8pnFvlUU1uSSBO";
   public EditText message;
    Button send;
    FirebaseFirestore db;
    String sender;
    String receiver;
    String key,id;
    String receiverId,receiverName,senderId,senderName;
    PreferenceHelper helper;
    int type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        helper=new PreferenceHelper(this);
        key=getIntent().getStringExtra("key");

        if (key=="admin"||key.equals("admin"))
        {
            receiverId=getIntent().getStringExtra("patientId");
            receiverName=getIntent().getStringExtra("patientName");
            sender="admin";
            receiver=helper.getUserEmail();
        }
        else if (key=="patient"||key.equals("patient"))
        {
            receiverId=getIntent().getStringExtra("docId");
            receiverName=getIntent().getStringExtra("docName");
            sender=helper.getAdminEmail();
            receiver="admin";
        }

        adapter=new MessagingAdapter(list,this);
        recyclerView=findViewById(R.id.message_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        db=FirebaseFirestore.getInstance();
        send=findViewById(R.id.send_message_btn);
        message=findViewById(R.id.message_text);
//        send.setOnClickListener(view -> sendMessage());

        if (key=="admin"||key.equals("admin"))
        {
            getMessagesFromPatient();
        }
        else if (key=="patient"||key.equals("patient"))
        {
            getMessagesFromDoctor();
        }
        else {
            Toast.makeText(this, "KEY DOES NOT EXIST, PLEASE RELAUNCH THE APP!", Toast.LENGTH_SHORT).show();
        }
        send.setOnClickListener(view -> {
            if (key=="admin"||key.equals("admin"))
            {
                sendToPatient();
            }
            else  if (key=="patient"||key.equals("patient"))
            {
                sendToDoctor();
            }
            else
            {
                Toast.makeText(getApplicationContext(), "KEY DOES NOT EXIST, PLEASE RELAUNCH THE APP!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getMessagesFromDoctor() {
        db.collection("admin").document(receiverId)
                .collection("inbox")
                .document(helper.getUserEmail())
                .collection("messages")
                .orderBy("time", Query.Direction.ASCENDING).addSnapshotListener((value, error) -> {
            if (error!=null)
            {
                return;
            }
            list.clear();
            for (DocumentSnapshot doc:value)
            {
                if (doc.exists())
                {
                    String message=doc.get("message").toString();
                    String sen=doc.get("sender").toString();
                    String receiver=doc.get("receiver").toString();
                    if (receiver.equals("admin")||receiver=="admin")
                    {
                        type=1;
                    }
                    else {
                        type=2;
                    }

                    Messages m=new Messages(message,sen,null,type,receiver);
                    list.add(m);
                    adapter.notifyDataSetChanged();
                    adapter.notifyItemInserted(-1);
                }
            }
        });
    }

    private void getMessagesFromPatient() {
        db.collection("admin").document(helper.getAdminEmail())
                .collection("inbox")
                .document(receiverId)
                .collection("messages")
                .orderBy("time", Query.Direction.ASCENDING)
               .addSnapshotListener((value, error) -> {
            if (error!=null)
            {
                return;
            }
            list.clear();
            for (DocumentSnapshot doc:value)
            {
                if (doc.exists())
                {
                    String message=doc.get("message").toString();
                    String sen=doc.get("sender").toString();
                    String receiver=doc.get("receiver").toString();
                    if (receiver.equals("admin")||receiver=="admin")
                    {
                        type=2;
                    }
                    else if (receiver.equals("patient")||receiver=="patient")
                    {
                        type=1;
                    }
                    Messages m=new Messages(message,sender,null,type,receiver);
                    list.add(m);
                    adapter.notifyDataSetChanged();
                    adapter.notifyItemInserted(-1);
                }
            }
        });
    }

    private void sendToPatient() {
        String messag=message.getText().toString();
        Messages mess=new Messages(messag,sender,null,1,"patient");
        db.collection("admin").document(helper.getAdminEmail())
                .collection("inbox")
                .document(receiverId)
                .collection("messages")
                .document()
                .set(mess)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(ChatsActivity.this, "Message sent successfully!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void sendToDoctor() {
        com.example.mymedapp.data.List desc=new com.example.mymedapp.data.List(helper.getUserEmail(),"patient");
        String messag=message.getText().toString();
        Messages mess=new Messages(messag,sender,null,1,"admin");
        db.collection("admin").document(receiverId)
                .collection("inbox")
                .document(helper.getUserEmail())
                .set(desc).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                db.collection("admin").document(receiverId)
                        .collection("inbox")
                        .document(helper.getUserEmail())
                        .collection("messages")
                        .document()
                        .set(mess)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(ChatsActivity.this, "Message sent successfully!", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                            Toast.makeText(ChatsActivity.this, "Message sending request failed!", Toast.LENGTH_LONG).show();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ChatsActivity.this, "Message sending request failed!", Toast.LENGTH_LONG).show();
            }
        });
  }

   /* private void getMessages() {
        db.collection("doctors").document(id)
                .collection("chats")
                .orderBy("time", Query.Direction.ASCENDING).addSnapshotListener((value, error) -> {
                   if (error!=null)
                   {
                       return;
                   }
                   list.clear();
                   for (DocumentSnapshot doc:value)
                   {
                       if (doc.exists())
                       {
                           String message=doc.get("message").toString();
                           String sen=doc.get("sender").toString();
                           if (key!=helper.getEmail())
                           {
                               type=1;
                           }
                           else
                           {
                               type=2;
                           }
                           Message m=new Message(message,"",null,type);
                           list.add(m);
                           adapter.notifyDataSetChanged();
                           adapter.notifyItemInserted(-1);
                       }
                   }
                });
    }
    
    */
}