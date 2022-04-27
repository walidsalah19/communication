package com.example.communication;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.communication.Model.CourseModel;
import com.example.communication.Model.SubscribeModel;
import com.example.communication.common.common;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;


public class AddRegister extends AppCompatActivity {
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseAuth auth;
    private SweetAlertDialog pDialogLoading;
    private SweetAlertDialog pDialogSuccess;


    @BindView(R.id.edt_subscriber_number)
    EditText edt_subscriber_number;
    String SubscriberNumber;


//    @BindView(R.id.edt_course_subscribe_no)
//    EditText edt_course_subscribe_no;
//    String CourseNumber;


    @BindView(R.id.btn_add_subscribe)
    Button btn_add_subscribe;


    @OnClick(R.id.btn_add_subscribe)
    void btn_add_course() {
        Subscribe();
    }

    @BindView(R.id.spinner_user_type)
    Spinner spinner_user_type;
    String UserType;

    @BindView(R.id.spinner_course_number)
    Spinner spinner_course_number;
    String CourseNumber;

    private String[] type_user = {"Teacher", "Student"};
    private ArrayAdapter typeUserAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_add_register);

        getSupportActionBar().setTitle(Html.fromHtml("<font color='#ffffff'>Add Subscribe</font>"));
        ButterKnife.bind(this);
        //getSupportActionBar().hide();

        initViews();
        initFirebase();

        getCourse();
    }


    private void initFirebase() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        auth = FirebaseAuth.getInstance();
    }

    private void initViews() {

        typeUserAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item_selected, type_user);
        typeUserAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);

        spinner_user_type.setAdapter(typeUserAdapter);
    }

    private void Subscribe() {
        SubscriberNumber = edt_subscriber_number.getText().toString();
        UserType = spinner_user_type.getSelectedItem().toString();
        CourseNumber = spinner_course_number.getSelectedItem().toString();


        if (!(TextUtils.isEmpty(SubscriberNumber))) {
            if (!(TextUtils.isEmpty(UserType))) {
                if (!(TextUtils.isEmpty(CourseNumber))) {
                    pDialogLoading = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
                    pDialogLoading.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                    pDialogLoading.setTitleText(getString(R.string.adding_loading));
                    pDialogLoading.setCancelable(false);
                    pDialogLoading.show();

                    Savetodb(SubscriberNumber, UserType,CourseNumber);

                }
                else {
                    Toast.makeText(this, "Please Select Course number", Toast.LENGTH_SHORT).show();

                }
            }
            else {
                Toast.makeText(this, "Please Select Data", Toast.LENGTH_SHORT).show();

            }
        } else {
            edt_subscriber_number.setError(getString(R.string.please_fill_data));

        }
    }



    private void Savetodb(String Subscribernumber, String Usertype , String Coursenumber) {

        String id = databaseReference.child(common.SubscriberKey).push().getKey();


        SubscribeModel model = new SubscribeModel(id, Subscribernumber, Usertype, Coursenumber);
        pDialogLoading.dismiss();

        databaseReference.child(common.SubscriberKey).child(id).setValue(model).addOnCompleteListener(task -> {
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
    private void getCourse() {

        databaseReference.child(common.SubjectsKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                final List<String> areas = new ArrayList<String>();

                for (DataSnapshot areaSnapshot : dataSnapshot.getChildren()) {
                    String CourseNumber = areaSnapshot.child("course_number").getValue(String.class);
                    String CourseName = areaSnapshot.child("course_name").getValue(String.class);
                    String divasion = areaSnapshot.child("course_divison").getValue(String.class);


                    areas.add(CourseNumber + " - "+CourseName+" - "+divasion);
                }

                ArrayAdapter<String> areasAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item_selected, areas);
                areasAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
                spinner_course_number.setAdapter(areasAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}

