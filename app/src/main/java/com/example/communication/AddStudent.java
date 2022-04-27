package com.example.communication;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.communication.Model.StudentModel;
import com.example.communication.common.common;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.greenrobot.eventbus.EventBus;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;


public class AddStudent extends AppCompatActivity {
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseAuth auth;
    private SweetAlertDialog pDialogLoading;
    private SweetAlertDialog pDialogSuccess;
    private SweetAlertDialog pDialogRegister;
    private Uri ImageUri;


    @BindView(R.id.img_student)
    ImageView img_student;


    @BindView(R.id.student_name)
    EditText student_name;
    String StudentName;

    @BindView(R.id.universty_student_number)
    EditText universty_student_number;
    String UnveristyNumber;

    @BindView(R.id.email_student)
    EditText email_student;
    String StudentEmail;

    @BindView(R.id.level_student)
    EditText level_student;
    String StudentLevel;

    @BindView(R.id.specialist_student)
    EditText specialist_student;
    String StudentSpecailist;

    @BindView(R.id.student_term)
    EditText student_term;
    String StudentTerm;

    @BindView(R.id.student_password)
    EditText student_password;
    String StudentPassword;


    @BindView(R.id.student_join)
    Button student_join;



    @OnClick(R.id.student_join)
    void img_student_btn_click() {
        AddStudent();
    }

    @OnClick(R.id.img_student)
    void btn_trainer_manager_club_image() {
        chooseImage();
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_students);

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
            Glide.with(this).load(ImageUri).into(img_student);

            EventBus.getDefault().removeAllStickyEvents();
        }  else {
            Toast.makeText(this, "No Picture Chosen", Toast.LENGTH_SHORT).show();
        }
    }

    private void AddStudent() {
        StudentName = student_name.getText().toString();
        UnveristyNumber = universty_student_number.getText().toString();
        StudentEmail = email_student.getText().toString();
        StudentLevel = level_student.getText().toString();
        StudentTerm = student_term.getText().toString();
        StudentSpecailist = specialist_student.getText().toString();
        StudentPassword = student_password.getText().toString();

        if (!(TextUtils.isEmpty(StudentName))) {
            if (!(TextUtils.isEmpty(UnveristyNumber))) {
                if (!(TextUtils.isEmpty(StudentEmail))) {
                    if (common.isValidEmail(StudentEmail)) {

                    if (!(TextUtils.isEmpty(StudentLevel))) {
                        if (!(TextUtils.isEmpty(StudentSpecailist))) {
                            if (!(TextUtils.isEmpty(StudentTerm))) {
                                if (!(TextUtils.isEmpty(StudentPassword))) {
                                        if (ImageUri != null) {
                                            pDialogLoading = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
                                            pDialogLoading.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                                            pDialogLoading.setTitleText(getString(R.string.adding_loading));
                                            pDialogLoading.setCancelable(false);
                                            pDialogLoading.show();

                                            UploadStudentImage(StudentName, UnveristyNumber,StudentEmail, StudentLevel, StudentTerm, StudentSpecailist, StudentPassword, ImageUri);

                                        } else {
                                            Toast.makeText(this, "PLease Choose Photo", Toast.LENGTH_SHORT).show();
                                        }

                                } else {
                                    student_password.setError(getString(R.string.please_fill_data));
                                }

                            } else {
                                student_term.setError(getString(R.string.please_fill_data));

                            }
                        }
                        else {
                            specialist_student.setError(getString(R.string.please_fill_data));

                        }
                    }
                        else {
                            level_student.setError(getString(R.string.please_fill_data));

                        }
                    }
                else {
                    email_student.setError(getString(R.string.email_valaidation));

                }
            }
                else {
                    email_student.setError(getString(R.string.please_fill_data));

                }
            }
            else {
                universty_student_number.setError(getString(R.string.please_fill_data));

            }
        } else {
            student_name.setError(getString(R.string.please_fill_data));

        }
    }

    private void UploadStudentImage(String Studentname, String Unveristynumber,String Studentemail, String StudentLevel, String Studentterm, String Studentspecailist, String Studentpassword, Uri imageUri) {
        UploadTask uploadTask;
        final StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("StudentImage/" + imageUri.getLastPathSegment());
        uploadTask = storageReference.putFile(imageUri);

        Task<Uri> task = uploadTask.continueWithTask(task12 -> storageReference.getDownloadUrl()).addOnCompleteListener(task1 -> {
            if (task1.isSuccessful()) {
                Uri image = task1.getResult();
                String photoUrl = image.toString();

                createUser(Studentname, Unveristynumber,Studentemail, StudentLevel, Studentterm, Studentspecailist,Studentpassword, photoUrl);

            } else {
                Toast.makeText(this, task1.getException().getMessage(), Toast.LENGTH_SHORT).show();
                pDialogLoading.dismiss();
            }
        });
    }
    private void createUser(final String Studentname,final String uinversityNumber, final String Studentemail, final String StudentLevel,final String Studentterm, final String Studentspecailist,final String Studentpassword,final String photoUrl) {
        auth.createUserWithEmailAndPassword(Studentemail, Studentpassword)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        String id = task.getResult().getUser().getUid();
                        addTodb(Studentname, uinversityNumber, Studentemail, StudentLevel, Studentterm,Studentspecailist,Studentpassword,photoUrl, id);
                    } else {
                        pDialogLoading.dismiss();
                        Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
    private void addTodb(String Studentname, String Unveristynumber , String Studentemail, String StudentLevel, String Studentterm, String Studentspecailist,String Studentpassword, String photoUrl, String studentId) {


        StudentModel model = new StudentModel(studentId, Studentname, Unveristynumber, Studentemail, StudentLevel, Studentspecailist, Studentterm, Studentpassword, photoUrl);
        pDialogLoading.dismiss();

        databaseReference.child(common.StudentsKey).child(studentId).setValue(model).addOnCompleteListener(task -> {
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

