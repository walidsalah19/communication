package com.example.communication.common;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.communication.Model.StudentModel;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class common {

    public static final String PREFS_NAME = "MyPrefsFile";
    public static final String PREF_USERNAME = "username";
    public static final String PREF_PASSWORD = "password";

    //On Activity Result Key
    public static final int GalleryPick = 71;
    public static final int PLACE_PICKER_REQUEST = 999;
    public static final int PICK_VIDEO_REQUEST = 1;
    public static final int PICK_PDF_CODE = 2342;



    public static final String StudentsKey = "Students";
    public static final String TeachersKey = "Teachers";
    public static final String SubjectsKey = "Subjects";
    public static final String SubscriberKey = "Subscriber";

    public static StudentModel studentModel;

    public static boolean isValidEmail(String mail) {
        String emailPattern =
                "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                        + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                        + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                        + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";
        boolean check = false;
        if (mail.matches(emailPattern) && mail.length() > 0) {
            check = true;
        } else {
            check = false;
        }
        return check;
    }

    public static boolean isValidPassword(final String password) {

        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();

    }

    public static boolean isValidPhone(String phone) {
        boolean check = false;
        if ((!Pattern.matches("^((?:[+?0?05?966]+)(?:\\s?\\d{2})(?:\\s?\\d{7}))$\n", phone))) {
            if ((phone.length() < 10 || phone.length() > 13)) {
                check = false;

            } else {
                check = true;

            }
        } else {
            check = false;
        }
        return check;
    }

    public static boolean isConnectedToInternet(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);

        if (connectivityManager != null) {
            NetworkInfo[] info = connectivityManager.getAllNetworkInfo();

            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED)
                        return true;
                }
            }
        }
        return false;
    }

    public static String encodeUserEmail(String userEmail) {
        return userEmail.replace(".", ",");
    }

    public static String decodeUserEmail(String userEmail) {
        return userEmail.replace(",", ".");
    }

}
