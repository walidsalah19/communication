package com.example.communication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class Start extends AppCompatActivity {

    @OnClick(R.id.login_btn)
    void login_btn()
    {
        startActivity(new Intent(getApplicationContext(), Login.class));
        finish();
    }

    @OnClick(R.id.database_btn)
    void database_btn()
    {
        startActivity(new Intent(getApplicationContext(), DataBaseDashboard.class));
        finish();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        getSupportActionBar().hide();
        ButterKnife.bind(this);


    }
    }

