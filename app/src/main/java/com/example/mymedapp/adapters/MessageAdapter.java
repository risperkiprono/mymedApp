  package com.example.mymedapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymedapp.R;
import com.example.mymedapp.data.Message;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {
    List<Message> list;
    Context context;
    //Public constructor for message_adapter class.
    public MessageAdapter(List<Message> list, Context context) {
        this.list = list;
        this.context=context;
    }

    @NonNull
    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_send,parent,false);
//        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_receive,parent,false);
        ViewHolder vh=new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.ViewHolder holder, int position) {
String message=list.get(position).getMessage();
//int resource=list.get(position).getCircle_imageView();
//String msg=list.get(position).getTxt_send();
//String ms=list.get(position).getEdt_receive();
holder.setData(message);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView msg_txt;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            msg_txt=itemView.findViewById(R.id.txt_send);

        }


        public void setData(String message) {
msg_txt.setText(message);
        }
        }
    }
