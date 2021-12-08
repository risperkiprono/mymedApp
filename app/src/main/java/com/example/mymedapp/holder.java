package com.example.mymedapp;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class holder extends RecyclerView.ViewHolder {
    private View view;

    public holder(@NonNull View itemView) {
        super(itemView);
        view = itemView;
    }

}
