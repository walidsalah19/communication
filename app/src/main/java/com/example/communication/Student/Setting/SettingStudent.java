package com.example.communication.Student.Setting;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.example.communication.EventBus.PassMassageActionClick;
import com.example.communication.R;
import com.example.communication.Splash;

import org.greenrobot.eventbus.EventBus;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import java.util.Locale;

import butterknife.ButterKnife;
import butterknife.Unbinder;


public class SettingStudent extends Fragment {

    private Unbinder unbinder;

    private RadioGroup language,dark_mode,notification;

    public static SettingStudent createFor() {
        SettingStudent fragment = new SettingStudent();

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
        View v= inflater.inflate(R.layout.fragment_setting, container, false);
       // change_language(v);
      //  dark_mode_method(v);
        return v;
    }

    private void dark_mode_method(View v) {
        dark_mode=v.findViewById(R.id.radiogroup_dark_mode);
        dark_mode.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                if (i==R.id.light)
                {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                }
                else if(i==R.id.dark)
                {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
            }
        });
    }

    private void change_language(View v)
    {
        language=v.findViewById(R.id.radiogroup_language);
        sharedprefrance s=new sharedprefrance(getActivity());
        String currentLang= s.loadlanguage();
        if (TextUtils.isEmpty(currentLang))
        {

        }
        else if (currentLang.equals("ar"))
        {
            language.check(R.id.arabic);
        }
        else if (currentLang.equals("en"))
        {
            language.check(R.id.english);
        }
        language.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                if (i==R.id.arabic)
                {
                   change_local_language("ar");

                }
                else if(i==R.id.english)
                {
                    change_local_language("en");
                }
            }
        });
    }
    private void change_local_language(String lang)
    {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Resources resources = getActivity().getResources();
        Configuration config = resources.getConfiguration();
        config.setLocale(locale);
        resources.updateConfiguration(config, resources.getDisplayMetrics());
        EventBus.getDefault().postSticky(new PassMassageActionClick("navigation"));
        sharedprefrance s=new sharedprefrance(getActivity());
        s.setlanguage(lang);
    }
}
