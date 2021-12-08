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
import com.example.mymedapp.data.Doctor;

import java.util.List;

public class DoctorsAdapter extends RecyclerView.Adapter<DoctorsAdapter.ViewHolder> {
    List<Doctor> list;
    onItemClickListener  listener;
    Context context;

    public DoctorsAdapter(List<Doctor> list, onItemClickListener listener, Context context) {
        this.list = list;
        this.listener = listener;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_doctors,parent,false);
ViewHolder vh=new ViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
String name=list.get(position).getName();
String id=list.get(position).getId();
//changes made
String speciality=list.get(position).getSpeciality();
String hospital=list.get(position).getHospital();
String experience=list.get(position).getExperience();
String fee=list.get(position).getFee();
holder.setData(name);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        //add views
        TextView tv_name,speciality,experience,fee,hospital;
        Button videocall,appointment,chat;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name=itemView.findViewById(R.id.doctors_name);
            speciality=itemView.findViewById(R.id.speciality);
            experience=itemView.findViewById(R.id.experience);
            fee=itemView.findViewById(R.id.doctorFee);
            hospital=itemView.findViewById(R.id.hospital);
            videocall=itemView.findViewById(R.id.video_call_btn);
            appointment=itemView.findViewById(R.id.appointments);
            chat=itemView.findViewById(R.id.btn_chat);
            videocall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onVideoButtonClicked(getAdapterPosition(),v);
                }
            });
            appointment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onAppointmentButtonClicked(getAdapterPosition(),v);
                }
            });
            chat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onChatButtonClicked(getAdapterPosition(),v);
                }
            });
        }
        //changes made
        public void setData(String name) {tv_name.setText(name); }

    }
    public interface onItemClickListener{
        void onVideoButtonClicked(int position,View v);
        void onChatButtonClicked(int position,View v);
        void onAppointmentButtonClicked(int position,View v);

    }
}
