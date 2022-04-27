package com.example.communication.Teacher.teachersection;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.communication.EventBus.PassMassageActionClick;
import com.example.communication.Model.Section_model;
import com.example.communication.R;
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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.Unbinder;


public class HomeTeacher extends Fragment {

    private Unbinder unbinder;

   private String number,name;
   private RecyclerView recyclerView;
   private ArrayList<Section_model> section_arr;
   private DatabaseReference database;
    public static HomeTeacher createFor() {
        HomeTeacher fragment = new HomeTeacher();

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
        View v= inflater.inflate(R.layout.fragment_home_teacher2, container, false);
        firebase_tool();
        get_course_num();
        intialize_recyclerView(v);
        return v;
    }

    private void firebase_tool() {
        database= FirebaseDatabase.getInstance().getReference();
    }

    private void intialize_recyclerView(View v) {
        recyclerView=v.findViewById(R.id.teacher_recyclerview_section);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(getActivity(),2, GridLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(gridLayoutManager);
        section_arr=new ArrayList<>();
        get_sections();
    }
    private void get_sections()
    {
        database.child(common.SubjectsKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                {
                    for (DataSnapshot snap:snapshot.getChildren())
                    {
                        String c_num=snap.child("course_number").getValue().toString();
                        String c_name=snap.child("course_name").getValue().toString();
                        String division=snap.child("course_divison").getValue().toString();
                        if (name.equals(c_name)&& number.equals(c_num))
                        {
                           section_arr.add(new Section_model("Section",division,c_name,c_num));
                        }
                    }
                    section_adapter adapter=new section_adapter(section_arr,HomeTeacher.this);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void get_course_num()
    {
        Bundle b=getArguments();
        name=b.getString("cource_name");
        number=b.getString("cource_num");
    }



}
