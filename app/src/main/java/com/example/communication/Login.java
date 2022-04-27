package com.example.communication;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;


import com.example.communication.Student.Student;
import com.example.communication.Teacher.Teacher;
import com.example.communication.common.common;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;


public class Login extends AppCompatActivity {

    private DatabaseReference database;
    private FirebaseAuth auth;
    private SweetAlertDialog pDialogLoading;
    private SweetAlertDialog pDialogRegister;

    @BindView(R.id.login_email_data)
    EditText login_email_edt;
    String Email;

    @BindView(R.id.login_password_data)
    EditText login_password_input;
    String Password;


    @OnClick(R.id.forget_password_link)
    void reset_btn_click() {
        Intent intent = new Intent(this, ForgetPassword.class);
        startActivity(intent);
    }

    @OnClick(R.id.login_btn)
    void login_btn_click() {
      login();
    }

    private void login() {
        Email=login_email_edt.getText().toString();
        Password =login_password_input.getText().toString();
        if(TextUtils.isEmpty(Email)){
           login_email_edt.setError(getString(R.string.email_error));
        }else if(TextUtils.isEmpty(Password)){
            login_password_input.setError(getString(R.string.password_error));
        }
        else
        {
            login_firebase();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        ButterKnife.bind(this);
        initFirebase();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user=auth.getCurrentUser();
        if (user !=null)
        {
            get_registeration(user.getUid().toString());
        }
    }

    private void get_registeration(String id) {
        pDialogLoading = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialogLoading.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialogLoading.setTitleText(getString(R.string.sign_in));
        pDialogLoading.setCancelable(false);
        pDialogLoading.show();
        database.child(common.StudentsKey).child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                {
                    pDialogLoading.dismiss();
                    startActivity(new Intent(Login.this,Student.class));
                    finish();
                }
                else
                {
                    pDialogLoading.dismiss();
                    startActivity(new Intent(Login.this,Teacher.class));
                    finish();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void initFirebase() {
        database = FirebaseDatabase.getInstance().getReference();
        auth=FirebaseAuth.getInstance();
    }
    private void login_firebase() {
        auth.signInWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful())
                {
                    check_user_typy(task.getResult().getUser().getUid().toString());
                }
                else
                {
                    Toast.makeText(Login.this,getString(R.string.error_occur),Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void check_user_typy(String toString) {
             database.child(common.StudentsKey).child(toString).addValueEventListener(new ValueEventListener() {
                 @Override
                 public void onDataChange(@NonNull DataSnapshot snapshot) {
                      if (snapshot.exists())
                      {
                          startActivity(new Intent(Login.this,Student.class));
                          finish();
                      }
                      else
                      {
                          startActivity(new Intent(Login.this,Teacher.class));
                          finish();
                      }
                 }

                 @Override
                 public void onCancelled(@NonNull DatabaseError error) {

                 }
             });
    }

}

