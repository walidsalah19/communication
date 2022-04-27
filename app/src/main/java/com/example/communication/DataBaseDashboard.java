package com.example.communication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class DataBaseDashboard extends AppCompatActivity {

    @OnClick(R.id.students_card)
    void students_card_btn()
    {
        startActivity(new Intent(getApplicationContext(), AddStudent.class));
    }
    @OnClick(R.id.teachers_card)
    void teachers_card()
    {
        startActivity(new Intent(getApplicationContext(), AddTeacher.class));
    }

    @OnClick(R.id.courses_card)
    void courses_card()
    {
        startActivity(new Intent(getApplicationContext(), AddSubject.class));
    }

    @OnClick(R.id.regirestation_card)
    void regirestation_card()
    {
        startActivity(new Intent(getApplicationContext(), AddRegister.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_home_database_manage);

        getSupportActionBar().setTitle(Html.fromHtml("<font color='#ffffff'>Database Management </font>"));
        //getSupportActionBar().setTitle("Database Management");
        ButterKnife.bind(this);



    }


    }

