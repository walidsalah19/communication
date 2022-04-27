package com.example.communication.Student.ChatStudent;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.communication.EventBus.PassMassageActionClick;
import com.example.communication.Model.StudentModel;
import com.example.communication.Model.group_info;
import com.example.communication.R;
import com.example.communication.Teacher.Chat.ChatTeacherFragment;
import com.example.communication.Teacher.Chat.adapter.list_student_adapter;
import com.example.communication.common.common;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.greenrobot.eventbus.EventBus;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class ChatStudent extends Fragment {

    private Unbinder unbinder;
    private TextView group_name,group_section,teacher_name;
    private ImageView teacher_image;
    private String Section_num,course_name,course_number,group_id,teacher_number,teacher_id;
    private DatabaseReference database;
    private RelativeLayout teacher;
    @OnClick(R.id.chat_item)
    void chat_item(){
        group_info.setGroup_id(group_id);
        group_info.setChat_type("group");
        EventBus.getDefault().postSticky(new PassMassageActionClick("openChat"));


    }


    public static ChatStudent createFor() {
        ChatStudent fragment = new ChatStudent();

        return fragment;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Bundle args = getArguments();

        unbinder = ButterKnife.bind(this, view);
        EventBus.getDefault().postSticky(new PassMassageActionClick("HiddenFloatingActionButton"));


    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_group_chat_student, container, false);
        firebase_tool();
        get_arguments();
        intialization(v);
        get_group_name();
        get_teacher_number();
        chat_teacher();
        return  v;
    }

    private void get_group_name() {
        database.child("Groups").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot snap : snapshot.getChildren()) {
                        String name = snap.child("course_name").getValue().toString();
                        String num = snap.child("course_number").getValue().toString();
                        String g_id = snap.child("group_id").getValue().toString();
                        String section = snap.child("Section_number").getValue().toString();
                        if (name.equals(course_name) && num.equals(course_number) && section.equals(Section_num)) {
                            group_id = g_id;
                            group_name.setText(name);
                            group_section.setText(section);
                        }

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    private void intialization(View v)
    {
        group_name=v.findViewById(R.id.student_group_chat_txt);
        group_section=v.findViewById(R.id.student_group_chat_section);
        teacher_name=v.findViewById(R.id.teacher_name_student);
        teacher_image=v.findViewById(R.id.iv_user_photo2);
        teacher=v.findViewById(R.id.chat_teacher);
    }
    private void get_arguments()
    {
        Section_num= group_info.getSection_number();
        course_name=group_info.getCourse_name();
        course_number=group_info.getCourse_number();
        Log.d("this",course_name);
    }
    private void firebase_tool() {
        database= FirebaseDatabase.getInstance().getReference();
    }
    private void get_teacher_number()
    {
        database.child(common.SubscriberKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                {
                    for (DataSnapshot snap:snapshot.getChildren())
                    {
                        String name=snap.child("course_number").getValue().toString();
                        String u_type=snap.child("user_type").getValue().toString();
                        if (name.equals(course_number + " - "+course_name+" - "+Section_num)&&u_type.equals("Teacher"))
                        {
                            teacher_number=snap.child("subscriber_number").getValue().toString();
                        }
                    }
                    if ( ! teacher_number.equals(""))
                    {
                        get_teacher();
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void get_teacher() {
        database.child(common.TeachersKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                {
                    for (DataSnapshot snap:snapshot.getChildren())
                    {
                        String number=snap.child("teacher_university_number").getValue().toString();
                        if (teacher_number.equals(number))
                        {
                            String name=snap.child("teacher_fullname").getValue().toString();
                            teacher_id=snap.child("teacher_id").getValue().toString();
                            String image=snap.child("student_img").getValue().toString();
                            teacher_name.setText(name);
                            Glide.with(getActivity()).load(image).into(teacher_image);
                        }
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void chat_teacher(){
        teacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                group_info.setGroup_id(teacher_id);
                group_info.setChat_type("student");
                EventBus.getDefault().postSticky(new PassMassageActionClick("openChat"));

            }
        });
    }

}
