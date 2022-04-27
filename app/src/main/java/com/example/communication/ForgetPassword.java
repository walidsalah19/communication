package com.example.communication;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;


public class ForgetPassword extends AppCompatActivity {
    private EditText email;
    private Button change;

    @BindView(R.id.back_login_btn)
    TextView back_login_btn;

    @BindView(R.id.rest_password_email_data)
    EditText edt_forget_email;
    String Email;

    @BindView(R.id.rest_password_btn)
    Button reset_password_btn;

    @OnClick(R.id.back_login_btn)
    void clicklogin_back() {
        startActivity(new Intent(this, Login.class));


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rest_password);
        ButterKnife.bind(this);
        getSupportActionBar().hide();
        change_method();
    }
    private void change_method() {
        change=findViewById(R.id.rest_password_btn);
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                change_password();
            }
        });
    }
    private void change_password() {
        email=findViewById(R.id.rest_password_email_data);
        if (email.getText().toString().equals(""))
        {
            email.setError(getString(R.string.email_error));
        }
        else
        {
            FirebaseAuth auth=FirebaseAuth.getInstance();
            auth.sendPasswordResetEmail(email.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful())
                    {
                        Toast.makeText(ForgetPassword.this, "تم إرسال الي البريد الإلكتروني", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }




}

