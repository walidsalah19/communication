package com.example.communication.Teacher.Chat.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.communication.EventBus.PassMassageActionClick;
import com.example.communication.Model.StudentModel;
import com.example.communication.R;
import com.example.communication.Model.group_info;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class list_student_adapter extends RecyclerView.Adapter<list_student_adapter.help> {

    ArrayList<StudentModel> arrayList;
    Fragment fragment;

    public list_student_adapter(ArrayList<StudentModel> arrayList, Fragment fragment) {
        this.arrayList = arrayList;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public help onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.teacher_student_list_chat,parent,false);
        return new help(v);
    }

    @Override
    public void onBindViewHolder(@NonNull help holder, int position) {
        holder.name.setText(arrayList.get(position).getStudent_fullname());
        Glide.with(fragment.getActivity()).load(arrayList.get(position).getStudent_img()).into(holder.image);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                group_info.setGroup_id(arrayList.get(position).getStudent_id());
                group_info.setChat_type("student");
                EventBus.getDefault().postSticky(new PassMassageActionClick("openChat"));
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class help extends RecyclerView.ViewHolder
    {
         TextView name;
         CircleImageView image;
        public help(@NonNull View itemView) {
            super(itemView);
            name=(TextView)itemView.findViewById(R.id.teacher_student_chat_name);
            image=(CircleImageView)itemView.findViewById(R.id.teacher_student_chat_photo1);
        }
    }
}
