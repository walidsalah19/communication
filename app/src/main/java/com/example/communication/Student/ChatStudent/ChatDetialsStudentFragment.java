package com.example.communication.Student.ChatStudent;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.communication.EventBus.PassMassageActionClick;
import com.example.communication.Model.group_info;
import com.example.communication.Model.message_model;
import com.example.communication.R;
import com.example.communication.Teacher.Chat.ChatDetialsTeacherFragment;
import com.example.communication.Teacher.Chat.adapter.chat_recycler_adapter;
import com.example.communication.common.common;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class ChatDetialsStudentFragment extends Fragment {

    private Unbinder unbinder;
    private FirebaseAuth auth;
    private DatabaseReference database;
    private RecyclerView recycler;
    private ArrayList<message_model> message_arr;
    private String user_image,user_id,send_time,chat_type,chat_id;
    private FloatingActionButton send;
    private EditText message_text;
    @OnClick(R.id.arrow_back_more_user)
    void arrow_back_more_user() {
        EventBus.getDefault().postSticky(new PassMassageActionClick("backChat"));

    }


    public static ChatDetialsStudentFragment createFor() {
        ChatDetialsStudentFragment fragment = new ChatDetialsStudentFragment();

        return fragment;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().postSticky(new PassMassageActionClick("backChat"));
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_chat, container, false);
        get_arguments();
        firebase_tool();
        recycler_view_method(v);
        get_user_image();
        send_message(v);
        get_message_from_database();
        return v;    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Bundle args = getArguments();

        unbinder = ButterKnife.bind(this, view);
        EventBus.getDefault().postSticky(new PassMassageActionClick("HiddenFloatingActionButton"));


    }
    private void get_message_from_database() {

        if (chat_type.equals("group"))
        {
            get_from_group_chat();
        }
        else if(chat_type.equals("student"))
        {
            get_from_student_chat();
        }
    }

    private void get_from_group_chat() {
        database.child("group_chat").child(chat_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                {
                    message_arr.clear();
                    for (DataSnapshot snap:snapshot.getChildren())
                    {
                        String message=snap.child("message").getValue().toString();
                        String user_image=snap.child("user_image").getValue().toString();
                        String user_id=snap.child("user_id").getValue().toString();
                        String message_date=snap.child("message_date").getValue().toString();
                        message_arr.add(new message_model(message,user_image,user_id,message_date));

                    }
                    chat_recycler_adapter adapter=new chat_recycler_adapter(message_arr, ChatDetialsStudentFragment.this,user_id);
                    recycler.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    recycler.smoothScrollToPosition(recycler.getAdapter().getItemCount());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void get_from_student_chat()
    {
        database.child("student_chat").child(user_id).child(chat_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                {
                    message_arr.clear();
                    for (DataSnapshot snap:snapshot.getChildren())
                    {
                        String message=snap.child("message").getValue().toString();
                        String user_image=snap.child("user_image").getValue().toString();
                        String user_id=snap.child("user_id").getValue().toString();
                        String message_date=snap.child("message_date").getValue().toString();
                        message_arr.add(new message_model(message,user_image,user_id,message_date));

                    }
                    chat_recycler_adapter adapter=new chat_recycler_adapter(message_arr, ChatDetialsStudentFragment.this,user_id);
                    recycler.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    recycler.smoothScrollToPosition(recycler.getAdapter().getItemCount());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void send_message(View v) {
        message_text=v.findViewById(R.id.teacher_send_message_text);
        send=v.findViewById(R.id.teacher_send_message);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chech_message();
            }
        });
    }

    private void chech_message() {
        if (TextUtils.isEmpty(message_text.getText().toString()))
        {
            message_text.setError(getString(R.string.empty_message));
        }
        else
        {

            if (chat_type.equals("group"))
            {
                send_to_chat_group();
            }
            else if(chat_type.equals("student"))
            {
                send_to_student();
            }

        }
    }

    private void send_to_student() {
        get_date();
        message_model model=new message_model(message_text.getText().toString(),user_image,user_id,send_time);
        database.child("student_chat").child(user_id).child(chat_id).push().setValue(model).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful())
                {
                    database.child("student_chat").child(chat_id).child(user_id).push().setValue(model).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful())
                            {
                                message_text.setText("");

                            }
                        }
                    });

                }
            }
        });
    }

    private void send_to_chat_group() {
        get_date();
        message_model model=new message_model(message_text.getText().toString(),user_image,user_id,send_time);
        database.child("group_chat").child(chat_id).push().setValue(model).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful())
                {
                    message_text.setText("");
                }
            }
        });
    }
    private void get_date()
    {
        send_time=  new SimpleDateFormat("hh.mm").format(Calendar.getInstance().getTime());
    }

    private void get_user_image() {

        database.child(common.StudentsKey).child(user_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                {
                    user_image=snapshot.child("student_img").getValue().toString();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void recycler_view_method(View v) {
        recycler=v.findViewById(R.id.teacher_chat_recycler);
        recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        message_arr=new ArrayList<>();
    }

    private void firebase_tool() {
        auth=FirebaseAuth.getInstance();
        database= FirebaseDatabase.getInstance().getReference();
        user_id=auth.getCurrentUser().getUid().toString();
    }

    private void get_arguments() {
        chat_type= group_info.getChat_type();
        chat_id=group_info.getGroup_id();
    }

}
