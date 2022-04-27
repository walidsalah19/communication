package com.example.communication.Student.CoursesStudent;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.communication.Model.CourseModel;
import com.example.communication.Model.group_info;
import com.example.communication.R;
import com.example.communication.Student.ChatStudent.ChatStudent;

import java.util.ArrayList;

public class course_adapter extends RecyclerView.Adapter<course_adapter.helpe>{
    ArrayList<CourseModel> arrayList;
    Fragment fragment;

    public course_adapter(ArrayList<CourseModel> arrayList, Fragment fragment) {
        this.arrayList = arrayList;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public helpe onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.student_recycler_layout,parent,false);

        return new helpe(v);
    }

    @Override
    public void onBindViewHolder(@NonNull helpe holder, int position) {
      holder.name.setText(arrayList.get(position).getCourse_name());
      holder.doctor.setText(arrayList.get(position).getCourse_number());
      holder.division.setText(arrayList.get(position).getCourse_divison());
      holder.itemView.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              group_info.setSection_number(arrayList.get(position).getCourse_divison());
              group_info.setCourse_number(arrayList.get(position).getCourse_number());
              group_info.setCourse_name(arrayList.get(position).getCourse_name());
              fragment.getActivity(). getSupportFragmentManager().beginTransaction()
                      .replace(R.id.container_student, new ChatStudent())
                      .addToBackStack(null)
                      .commitAllowingStateLoss();
          }
      });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class helpe extends RecyclerView.ViewHolder
    {
        TextView name,division,doctor;
        public helpe(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.student_recycler_course_name);
            division=itemView.findViewById(R.id.student_recycler_course_drivasion);
            doctor=itemView.findViewById(R.id.student_recycler_course_docotr_name);


        }
    }
}
