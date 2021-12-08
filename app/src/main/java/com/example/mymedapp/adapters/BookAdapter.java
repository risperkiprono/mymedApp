package com.example.mymedapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymedapp.R;
import com.example.mymedapp.data.Book;

import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder> {
    private List<Book> UserList;
    private Context context;
    OnItemClickListener listener;
    public BookAdapter(List<Book>UserList, Context context, OnItemClickListener listener){

        this.UserList=UserList;
        this.context=context;
        this.listener=listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.list_item_book,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String email=UserList.get(position).getEmail();
        String date=UserList.get(position).getDate();
        String time=UserList.get(position).getTime();
        holder.setData(email,date,time);

    }

    @Override
    public int getItemCount() {
        return UserList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView  tv_email,tv_date,tv_time;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_email=itemView.findViewById(R.id.email);
            tv_date=itemView.findViewById(R.id.date);
            tv_time=itemView.findViewById(R.id.time_at);
            itemView.setOnClickListener(this);
        }

        public void setData(String email,String date,String time){
            tv_email.setText(email);
            tv_date.setText(date);
            tv_time.setText(time);


        }

        @Override
        public void onClick(View view) {
            listener.onItemClick(getAdapterPosition(),view);
        }
//        public void SetImage(String image){
//            ImageView imageView=itemView.findViewById(R.id.imageview);
//            Picasso.get().load(image).into(imageView);
//        }
    }
    public interface OnItemClickListener{
        void onItemClick(int position, View v);
    }
}
