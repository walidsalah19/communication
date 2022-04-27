package com.example.communication.Student.Setting;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class sharedprefrance {
    SharedPreferences preferences;
    Context context;
    public sharedprefrance(Context context){
        this.context = context;
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void setlanguage(String language){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("language",language);
        editor.apply();
    }

    public String loadlanguage(){
        return preferences.getString("language","");
    }
}
