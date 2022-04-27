package com.example.communication.Teacher.Courses;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.communication.Model.CourseModel;
import com.example.communication.R;
import com.example.communication.Teacher.teachersection.HomeTeacher;

import java.util.ArrayList;

public class recycler_adapter extends RecyclerView.Adapter<recycler_adapter.helper> {
  ArrayList<CourseModel> arrayList;
  Fragment fragment;

    public recycler_adapter(ArrayList<CourseModel> arrayList, Fragment fragment) {
        this.arrayList = arrayList;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public helper onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v=LayoutInflater.from(parent.getContext()).inflate(R.layout.teacher_cources_recycler,parent,false);
        return new helper(v);
    }

    @Override
    public void onBindViewHolder(@NonNull helper holder, int position) {
      holder.cource_name.setText(arrayList.get(position).getCourse_name());
      holder.cource_num.setText(arrayList.get(position).getCourse_number());
      holder.itemView.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              HomeTeacher h=new HomeTeacher();
              Bundle b=new Bundle();
              b.putString("cource_num",arrayList.get(position).getCourse_number());
              b.putString("cource_name",arrayList.get(position).getCourse_name());
              h.setArguments(b);
              fragment.getActivity(). getSupportFragmentManager().beginTransaction()
                      .replace(R.id.container_teacher, h)
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
        TextView cource_name,cource_num;
        public helper(@NonNull View itemView) {
            super(itemView);
            cource_name=(TextView)itemView.findViewById(R.id.teacher_recycler_cource_name);
            cource_num=(TextView)itemView.findViewById(R.id.teacher_recycler_cource_number);
        }
    }
}
