package com.example.communication;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.communication.Model.CourseModel;
import com.example.communication.Model.TeacherModel;
import com.example.communication.common.common;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.greenrobot.eventbus.EventBus;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;


public class AddSubject extends AppCompatActivity {
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseAuth auth;
    private SweetAlertDialog pDialogLoading;
    private SweetAlertDialog pDialogSuccess;


    @BindView(R.id.edt_course_number)
    EditText edt_course_number;
    String CourseNumber;

    @BindView(R.id.edt_course_name)
    EditText edt_course_name;
    String CourseName;

    @BindView(R.id.edt_course_divison_number)
    EditText edt_course_divison_number;
    String CourseDivison;


    @BindView(R.id.btn_add_course)
    Button btn_add_course;



    @OnClick(R.id.btn_add_course)
    void btn_add_course() {
        AddCourse();
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_add_course);

        getSupportActionBar().setTitle(Html.fromHtml("<font color='#ffffff'>Add Subject</font>"));

        ButterKnife.bind(this);
        //getSupportActionBar().hide();
        initFirebase();
    }
    private void initFirebase() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        auth = FirebaseAuth.getInstance();
    }



    private void AddCourse() {
        CourseNumber = edt_course_number.getText().toString();
        CourseName = edt_course_name.getText().toString();
        CourseDivison = edt_course_divison_number.getText().toString();


        if (!(TextUtils.isEmpty(CourseNumber))) {
            if (!(TextUtils.isEmpty(CourseName))) {
                if (!(TextUtils.isEmpty(CourseDivison))) {
                    pDialogLoading = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
                    pDialogLoading.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                    pDialogLoading.setTitleText(getString(R.string.adding_loading));
                    pDialogLoading.setCancelable(false);
                    pDialogLoading.show();

                    Savetodb(CourseNumber, CourseName,CourseDivison);

                }
                else {
                    edt_course_divison_number.setError(getString(R.string.please_fill_data));

                }
            }
            else {
                edt_course_name.setError(getString(R.string.please_fill_data));

            }
        } else {
            edt_course_number.setError(getString(R.string.please_fill_data));

        }
    }



    private void Savetodb(String Coursenumber, String Coursename , String Coursedivison) {

        String id = databaseReference.child(common.SubjectsKey).push().getKey();


        CourseModel model = new CourseModel(id, Coursenumber, Coursename, Coursedivison);
        pDialogLoading.dismiss();

        databaseReference.child(common.SubjectsKey).child(id).setValue(model).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                pDialogSuccess = new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE);
                pDialogSuccess.setTitleText(getString(R.string.suceessfully_added));
                pDialogSuccess.setConfirmText(getString(R.string.done_txt));
                pDialogSuccess.setConfirmClickListener(sweetAlertDialog -> {
                    startActivity(new Intent(this, DataBaseDashboard.class));
                    finish();

                });

                pDialogSuccess.setCancelable(true);
                pDialogSuccess.show();
            }
        });

    }

}

