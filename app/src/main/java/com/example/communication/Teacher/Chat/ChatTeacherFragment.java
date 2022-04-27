package com.example.communication.Teacher.Chat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.communication.EventBus.PassMassageActionClick;
import com.example.communication.Model.StudentModel;
import com.example.communication.Model.group_info;
import com.example.communication.R;
import com.example.communication.Teacher.Chat.adapter.list_student_adapter;
import com.example.communication.common.common;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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
import java.util.HashMap;
import java.util.UUID;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class ChatTeacherFragment extends Fragment {

    private Unbinder unbinder;
    private TextView group_name,group_section;
    private String Section_num,course_name,course_number,group_id;
    private DatabaseReference database;
    private RecyclerView recycler;
    private ArrayList<String> subscribe_number;
    private ArrayList<StudentModel> student_arr;
    @OnClick(R.id.chat_item)
    void chat_item(){
        group_info.setGroup_id(group_id);
        group_info.setChat_type("group");
        EventBus.getDefault().postSticky(new PassMassageActionClick("openChat"));
    }


    public static ChatTeacherFragment createFor() {
        ChatTeacherFragment fragment = new ChatTeacherFragment();

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
        View v= inflater.inflate(R.layout.fragment_group_chat, container, false);
        firebase_tool();
        get_arguments();
        intialization(v);
        get_group_name();
        get_subscriber_number();
        return v;
    }
   private void intialization(View v)
   {
       group_name=v.findViewById(R.id.teacher_group_chat_txt);
       group_section=v.findViewById(R.id.teacher_group_chat_message);
       recycler=v.findViewById(R.id.teacher_student_recycler);
       recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
   }
    private void firebase_tool() {
        database=FirebaseDatabase.getInstance().getReference();
    }
    boolean found=false;
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
                       found = true;
                   }

               }
           }
               if (!found)
               {
                   create_group();
               }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void create_group() {
        String id= UUID.randomUUID().toString();
        HashMap<String, String>map=new HashMap<>();
        map.put("course_name",course_name);
        map.put("course_number",course_number);
        map.put("group_id",id);
        map.put("Section_number",Section_num);
        database.child("Groups").child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful())
                {
                    group_id=id;
                    group_name.setText(course_name);
                    group_section.setText(Section_num);
                }
            }
        });
    }

    private void get_arguments()
    {
      Section_num=group_info.getSection_number();
      course_name=group_info.getCourse_name();
      course_number=group_info.getCourse_number();
    }
    private void get_subscriber_number()
    {
        subscribe_number=new ArrayList<>();
        database.child(common.SubscriberKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                {
                    for (DataSnapshot snap:snapshot.getChildren())
                    {
                        String name=snap.child("course_number").getValue().toString();
                        String u_type=snap.child("user_type").getValue().toString();
                        if (name.equals(course_number + " - "+course_name+" - "+Section_num)&&u_type.equals("Student"))
                        {
                             subscribe_number.add(snap.child("subscriber_number").getValue().toString());
                        }
                    }
                    get_student();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void get_student() {
        student_arr=new ArrayList<>();
        database.child(common.StudentsKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                {
                    for (DataSnapshot snap:snapshot.getChildren())
                    {
                        String number=snap.child("student_university_number").getValue().toString();
                        if (subscribe_number.contains(number))
                        {
                            String name=snap.child("student_fullname").getValue().toString();
                            String email=snap.child("studentEmail").getValue().toString();
                            String id=snap.child("student_id").getValue().toString();
                            String image=snap.child("student_img").getValue().toString();
                            String level=snap.child("student_level").getValue().toString();
                            String student_password=snap.child("student_password").getValue().toString();
                            String student_semeter=snap.child("student_semeter").getValue().toString();
                            String student_sepcialist=snap.child("student_sepcialist").getValue().toString();
                            student_arr.add(new StudentModel(id,name,number,email,level,student_sepcialist,student_semeter,student_password,image));
                        }
                    }
                    list_student_adapter adapter= new list_student_adapter(student_arr,ChatTeacherFragment.this);
                    recycler.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

}
