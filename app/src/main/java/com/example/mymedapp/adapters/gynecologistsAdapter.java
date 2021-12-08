package com.example.mymedapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymedapp.R;
import com.example.mymedapp.data.gynecologists;

import java.util.List;

public class gynecologistsAdapter extends RecyclerView.Adapter<gynecologistsAdapter.ViewHolder> {
    List<gynecologists> list;
    onItemClickListener listener;
    Context context;

    public gynecologistsAdapter(List<gynecologists> list, onItemClickListener listener, Context context) {
        this.list = list;
        this.listener = listener;
        this.context = context;
    }
    @NonNull
    @Override
    public gynecologistsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_doctorsgynecologists,parent,false);
     ViewHolder vh=new ViewHolder(v);

        return vh;

    }

    @Override
    public void onBindViewHolder(@NonNull gynecologistsAdapter.ViewHolder holder, int position) {
        String name=list.get(position).getName();
        String id=list.get(position).getId();
        holder.setData(name);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name;
        Button videocall,appointment,chat;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name=itemView.findViewById(R.id.doctors_name1);
            videocall=itemView.findViewById(R.id.video_call_btn1);
            appointment=itemView.findViewById(R.id.appointments1);
            chat=itemView.findViewById(R.id.btn_chat1);
            videocall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onVideoButtonClicked(getAdapterPosition(),v);
                }
            });
            chat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onChatButtonClicked(getAdapterPosition(),v);
                }
            });
            appointment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onAppointmentButtonClicked(getAdapterPosition(),v);
                }
            });
        }

        public void setData(String name) {
            tv_name.setText(name);

        }
    }

    public interface onItemClickListener {
        public void onVideoButtonClicked(int adapterPosition, View v);



        public void onChatButtonClicked(int adapterPosition, View v) ;



        public void onAppointmentButtonClicked(int adapterPosition, View v);


    }
}
