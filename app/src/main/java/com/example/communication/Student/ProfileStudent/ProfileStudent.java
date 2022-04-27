package com.example.communication.Student.ProfileStudent;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.communication.EventBus.PassMassageActionClick;
import com.example.communication.Model.StudentModel;
import com.example.communication.Model.TeacherModel;
import com.example.communication.R;
import com.example.communication.common.common;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;


public class ProfileStudent extends Fragment {
    private EditText name,term,number,department,email,level;
    private CircleImageView image;
    private FirebaseAuth auth;
    private DatabaseReference database;
    private String user_id,password,s_image;
    private Button update;
    private Unbinder unbinder;

@OnClick(R.id.signOut)
void signOut(){
    EventBus.getDefault().postSticky(new PassMassageActionClick("SignOut"));

}
    public static ProfileStudent createFor() {
        ProfileStudent fragment = new ProfileStudent();

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
        View v= inflater.inflate(R.layout.fragment_student_profile, container, false);
        firebase_tool();
        intialize_tool(v);
        get_data();
        update_method(v);
        return v;
    }
    private boolean status=true;
    private void update_method(View v) {

        update =v.findViewById(R.id.student_edit_profile);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (status) {
                    name.setEnabled(true);
                    term.setEnabled(true);
                    number.setEnabled(true);
                    department.setEnabled(true);
                    email.setEnabled(true);
                    update.setText(getString(R.string.update_profile));
                    level.setEnabled(true);
                    status=false;
                }
                else
                {
                    status=true;
                    update_in_database();
                }

            }
        });
    }

    private void update_in_database() {
        StudentModel model=new StudentModel(user_id,name.getText().toString(),number.getText().toString(),email.getText().toString(),level.getText().toString(),
                department.getText().toString(),term.getText().toString(),password,s_image);
        database.child(user_id).setValue(model).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(getActivity(), getString(R.string.update_profile), Toast.LENGTH_SHORT).show();
                    name.setEnabled(false);
                    term.setEnabled(false);
                    number.setEnabled(false);
                    department.setEnabled(false);
                    email.setEnabled(false);
                    level.setEnabled(false);
                    update.setText(getString(R.string.edit_profile));
                }
                else
                {
                    Toast.makeText(getActivity(), getString(R.string.error_occur), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void get_data() {
        database.child(user_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                {
                    password=snapshot.child("student_password").getValue().toString();
                    name.setText(snapshot.child("student_fullname").getValue().toString());
                    term.setText(snapshot.child("student_semeter").getValue().toString());
                    number.setText(snapshot.child("student_university_number").getValue().toString());
                    department.setText(snapshot.child("student_sepcialist").getValue().toString());
                    email.setText(snapshot.child("studentEmail").getValue().toString());
                    level.setText(snapshot.child("student_level").getValue().toString());
                    s_image=snapshot.child("student_img").getValue().toString();
                    Glide.with(getActivity()).load(s_image).into(image);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void intialize_tool(View v) {
        name=v.findViewById(R.id.Student_profile_fullname);
        term=v.findViewById(R.id.Student_profile_term);
        number=v.findViewById(R.id.Student_profile_universal_number);
        department=v.findViewById(R.id.Student_profile_department);
        email=v.findViewById(R.id.Student_profile_email);
        image=v.findViewById(R.id.Student_profile_imege);
        level=v.findViewById(R.id.Student_profile_level);


    }
    private void firebase_tool() {
        database= FirebaseDatabase.getInstance().getReference(common.StudentsKey);
        auth=FirebaseAuth.getInstance();
        user_id=auth.getCurrentUser().getUid().toString();
    }

}
