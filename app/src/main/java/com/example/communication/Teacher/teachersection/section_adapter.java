package com.example.communication.Teacher.teachersection;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.communication.Model.Section_model;
import com.example.communication.R;
import com.example.communication.Teacher.Chat.ChatTeacherFragment;
import com.example.communication.Model.group_info;

import java.util.ArrayList;

public class section_adapter extends RecyclerView.Adapter<section_adapter.helper>{
  ArrayList<Section_model> arrayList;
  Fragment fragment;

    public section_adapter(ArrayList<Section_model> arrayList, Fragment fragment) {
        this.arrayList = arrayList;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public helper onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.teacher_section_recyler,parent,false);

        return new helper(v);
    }

    @Override
    public void onBindViewHolder(@NonNull helper holder, int position) {
   holder.name.setText(arrayList.get(position).getSectioName());
   holder.number.setText(arrayList.get(position).getSectionNumber());
   holder.itemView.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View view) {
           Bundle b=new Bundle();
           group_info.setSection_number(arrayList.get(position).getSectionNumber());
           group_info.setCourse_number(arrayList.get(position).getCourse_num());
           group_info.setCourse_name(arrayList.get(position).getCourse_name());
           ChatTeacherFragment c=new ChatTeacherFragment();
           c.setArguments(b);
           fragment.getActivity(). getSupportFragmentManager().beginTransaction()
                   .replace(R.id.container_teacher, c)
                   .addToBackStack(null)
                   .commitAllowingStateLoss();
       }
   });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class helper extends RecyclerView.ViewHolder
    {
        TextView name,number;
        public helper(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.teacher_recycler_section_name);
            number=itemView.findViewById(R.id.teacher_recycler_section_number);
        }
    }
}
