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
import com.example.communication.Model.StudentModel;
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


public class AddTeacher extends AppCompatActivity {
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseAuth auth;
    private SweetAlertDialog pDialogLoading;
    private SweetAlertDialog pDialogSuccess;
    private SweetAlertDialog pDialogRegister;
    private Uri ImageUri;
    private Uri FileUri;


    @BindView(R.id.img_teacher)
    ImageView img_teacher;


    @BindView(R.id.edt_name_teacher)
    EditText edt_name_teacher;
    String Teachername;

    @BindView(R.id.edt_univeristy_number_teacher)
    EditText edt_univeristy_number_teacher;
    String TeacherUnveristyNumber;

    @BindView(R.id.edt_email_teacher)
    EditText edt_email_teacher;
    String TeacherEmail;

    @BindView(R.id.edt_smester_teacher)
    EditText edt_smester_teacher;
    String TeacherSemester;

    @BindView(R.id.edt_spechalist_teacher)
    EditText edt_spechalist_teacher;
    String TeacherSpecailist;



    @BindView(R.id.edt_password_teacher)
    EditText edt_password_teacher;
    String TeacherPassword;


    @BindView(R.id.teacher_add_btn)
    Button teacher_add_btn;



    @OnClick(R.id.teacher_add_btn)
    void teacher_add_btn() {
        AddTeacher();
    }

    @OnClick(R.id.img_teacher)
    void img_teacher() {
        chooseImage();
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_teachers);

        getSupportActionBar().setTitle(Html.fromHtml("<font color='#ffffff'>Add Teacher </font>"));

        ButterKnife.bind(this);
        //getSupportActionBar().hide();
        initFirebase();
    }
    private void initFirebase() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        auth = FirebaseAuth.getInstance();
    }

    private void chooseImage() {
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(Intent.createChooser(galleryIntent, "Select Picture "), common.GalleryPick);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == common.GalleryPick && resultCode == RESULT_OK && data != null) {
            ImageUri = data.getData();
            Glide.with(this).load(ImageUri).into(img_teacher);

            EventBus.getDefault().removeAllStickyEvents();
        }  else {
            Toast.makeText(this, "No Picture Chosen", Toast.LENGTH_SHORT).show();
        }
    }

    private void AddTeacher() {
        Teachername = edt_name_teacher.getText().toString();
        TeacherUnveristyNumber = edt_univeristy_number_teacher.getText().toString();
        TeacherEmail = edt_email_teacher.getText().toString();
        TeacherSemester = edt_smester_teacher.getText().toString();
        TeacherSpecailist = edt_spechalist_teacher.getText().toString();
        TeacherPassword = edt_password_teacher.getText().toString();

        if (!(TextUtils.isEmpty(Teachername))) {
            if (!(TextUtils.isEmpty(TeacherUnveristyNumber))) {
                if (!(TextUtils.isEmpty(TeacherEmail))) {
                    if (common.isValidEmail(TeacherEmail)) {

                    if (!(TextUtils.isEmpty(TeacherSpecailist))) {
                        if (!(TextUtils.isEmpty(TeacherSemester))) {
                                if (!(TextUtils.isEmpty(TeacherPassword))) {
                                        if (ImageUri != null) {
                                            pDialogLoading = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
                                            pDialogLoading.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                                            pDialogLoading.setTitleText(getString(R.string.adding_loading));
                                            pDialogLoading.setCancelable(false);
                                            pDialogLoading.show();

                                            UploadTeacherImage(Teachername, TeacherUnveristyNumber,TeacherEmail, TeacherSpecailist, TeacherSemester, TeacherPassword, ImageUri);

                                        } else {
                                            Toast.makeText(this, "PLease Choose Photo", Toast.LENGTH_SHORT).show();
                                        }

                                } else {
                                    edt_password_teacher.setError(getString(R.string.please_fill_data));
                                }

                            } else {
                            edt_smester_teacher.setError(getString(R.string.please_fill_data));

                        }
                    }
                        else {
                            edt_spechalist_teacher.setError(getString(R.string.please_fill_data));

                        }
                    }
                else {
                    edt_email_teacher.setError(getString(R.string.email_valaidation));

                }
            }
                else {
                    edt_email_teacher.setError(getString(R.string.please_fill_data));

                }
            }
            else {
                edt_univeristy_number_teacher.setError(getString(R.string.please_fill_data));

            }
        } else {
            edt_name_teacher.setError(getString(R.string.please_fill_data));

        }
    }

    private void UploadTeacherImage(String TeacherName, String TeacherunveristyNumber,String Teacheremail, String Teacherspecailist, String Teachersemester, String TeacherPassword,Uri imageUri) {
        UploadTask uploadTask;
        final StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("TeacherImage/" + imageUri.getLastPathSegment());
        uploadTask = storageReference.putFile(imageUri);

        Task<Uri> task = uploadTask.continueWithTask(task12 -> storageReference.getDownloadUrl()).addOnCompleteListener(task1 -> {
            if (task1.isSuccessful()) {
                Uri image = task1.getResult();
                String photoUrl = image.toString();

                createUser(TeacherName, TeacherunveristyNumber,Teacheremail, Teacherspecailist, Teachersemester, TeacherPassword, photoUrl);

            } else {
                Toast.makeText(this, task1.getException().getMessage(), Toast.LENGTH_SHORT).show();
                pDialogLoading.dismiss();
            }
        });
    }
    private void createUser(final String TeacherName,final String TeacherunveristyNumber, final String Teacheremail, final String Teacherspecailist,final String Teachersemester, final String TeacherPassword,final String photoUrl) {
        auth.createUserWithEmailAndPassword(Teacheremail, TeacherPassword)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        String id = task.getResult().getUser().getUid();
                        addTodb(TeacherName, TeacherunveristyNumber, Teacheremail, Teacherspecailist,Teachersemester,TeacherPassword,photoUrl, id);
                    } else {
                        pDialogLoading.dismiss();
                        Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
    private void addTodb(String TeacherName, String TeacherunveristyNumber , String Teacheremail, String Teacherspecailist, String Teachersemester, String TeacherPassword, String photoUrl, String id) {


        TeacherModel model = new TeacherModel(id, TeacherName, TeacherunveristyNumber, Teacheremail, Teacherspecailist, Teachersemester, TeacherPassword, photoUrl);
        pDialogLoading.dismiss();

        databaseReference.child(common.TeachersKey).child(id).setValue(model).addOnCompleteListener(task -> {
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

