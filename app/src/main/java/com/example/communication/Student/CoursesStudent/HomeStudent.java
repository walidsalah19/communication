package com.example.communication.Student.CoursesStudent;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.communication.EventBus.PassMassageActionClick;
import com.example.communication.Model.CourseModel;
import com.example.communication.R;
import com.example.communication.Teacher.Courses.TeacherCourses;
import com.example.communication.Teacher.Courses.recycler_adapter;
import com.example.communication.common.common;
import com.google.firebase.auth.FirebaseAuth;
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
import butterknife.Unbinder;


public class  HomeStudent extends Fragment {

    private Unbinder unbinder;
    private RecyclerView recyclerView;
    private FirebaseAuth auth;
    private DatabaseReference database;
    private String user_id ,student_number;
    private ArrayList<CourseModel> courses_arr;
    private ArrayList<String> course_number;
    public static HomeStudent createFor() {
        HomeStudent fragment = new HomeStudent();

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
        View v= inflater.inflate(R.layout.fragment_courses_student, container, false);
        Firebase_tool();
        recyclerView_data(v);
       return v;
    }

    private void recyclerView_data(View v) {
        recyclerView=v.findViewById(R.id.student_course_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        get_Student_number();
    }
    private void get_Student_number() {
        database.child(common.StudentsKey).child(user_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                {
                    student_number=snapshot.child("student_university_number").getValue().toString();

                    get_subscriber();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void get_subscriber() {
        course_number=new ArrayList<>();
        database.child(common.SubscriberKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                {
                    for (DataSnapshot snap:snapshot.getChildren())
                    {
                        String user_type=snap.child("user_type").getValue().toString();
                        String sub_number=snap.child("subscriber_number").getValue().toString();
                        if(user_type.equals("Student")&&sub_number.equals(student_number))
                        {
                            course_number.add(snap.child("course_number").getValue().toString());
                        }
                    }
                    get_course_data();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void get_course_data() {
        courses_arr=new ArrayList<>();
        database.child(common.SubjectsKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                {
                    for (DataSnapshot snap:snapshot.getChildren())
                    {
                        String c_num=snap.child("course_number").getValue().toString();
                        String name=snap.child("course_name").getValue().toString();
                        String id=snap.child("course_id").getValue().toString();
                        String division=snap.child("course_divison").getValue().toString();
                        boolean found=false;
                        for (CourseModel model : courses_arr) {
                            if (model.getCourse_number().equals(c_num)&& model.getCourse_name().equals(name))
                            {
                                found=true;
                            }
                        }
                        if (! found) {
                            for (String num : course_number) {
                                if (num.equals(c_num + " - " + name+" - "+division)) {

                                    courses_arr.add(new CourseModel(id, c_num, name, division));

                                }
                            }
                        }
                    }
                    course_adapter adapter=new course_adapter(courses_arr,HomeStudent.this);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void Firebase_tool()
    {
        database= FirebaseDatabase.getInstance().getReference();
        auth=FirebaseAuth.getInstance();
        user_id=auth.getCurrentUser().getUid().toString();
    }


}
