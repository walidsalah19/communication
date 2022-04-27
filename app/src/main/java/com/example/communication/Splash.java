package com.example.communication;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;

import androidx.appcompat.app.AppCompatActivity;

import com.example.communication.EventBus.PassMassageActionClick;
import com.example.communication.Student.Setting.sharedprefrance;

import org.greenrobot.eventbus.EventBus;

import java.util.Locale;


public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        getSupportActionBar().hide();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                sharedprefrance s=new sharedprefrance(Splash.this);
                String currentLang= s.loadlanguage();
                if (TextUtils.isEmpty(currentLang))
                {

                }
                else  if (currentLang.equals("ar"))
                {
                    change_local_language("ar");
                }
                else if (currentLang.equals("en"))
                {
                    change_local_language("en");
                }
                startActivity(new Intent(Splash.this, Start.class));
            }
        },3000);
    }
    private void change_local_language(String lang)
    {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Resources resources = this.getResources();
        Configuration config = resources.getConfiguration();
        config.setLocale(locale);
        resources.updateConfiguration(config, resources.getDisplayMetrics());
    }
    }

