package com.example.communication.Student;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.communication.EventBus.PassMassageActionClick;
import com.example.communication.Login;
import com.example.communication.R;
import com.example.communication.Student.ChatStudent.ChatDetialsStudentFragment;
import com.example.communication.Student.ChatStudent.ChatStudent;
import com.example.communication.Student.CoursesStudent.HomeStudent;
import com.example.communication.Student.ProfileStudent.ProfileStudent;
import com.example.communication.Student.Setting.SettingStudent;
import com.example.communication.Teacher.Courses.TeacherCourses;
import com.example.communication.Teacher.Profile.ProfileTeacher;
import com.gauravk.bubblenavigation.BubbleNavigationLinearView;
import com.gauravk.bubblenavigation.listener.BubbleNavigationChangeListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;


public class Student extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private DrawerLayout drawer;
    private FirebaseAuth auth;
    @BindView(R.id.fab_student)
    FloatingActionButton fab;
    private ActionBarDrawerToggle toggle;
    private boolean mToolBarNavigationListenerIsRegistered = false;
    BubbleNavigationLinearView bubbleNavigationLinearView;
     // BottomNavigationView bubbleNavigationLinearView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_student);
        auth=FirebaseAuth.getInstance();
        toolbar = (Toolbar) findViewById(R.id.toolbar_student);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout_student);
        bubbleNavigationLinearView = findViewById(R.id.bottom_navigation_view_linear);
       /* bubbleNavigationLinearView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id._item_home)
                {
                    Fragment selectedScreen = HomeStudent.createFor();
                    showFragment(selectedScreen);
                    bubbleNavigationLinearView.setVisibility(View.VISIBLE);
                }
                else if (item.getItemId() == R.id._item_profile)
                {
                    Fragment selectedScreen = ProfileStudent.createFor();
                    showFragment(selectedScreen);
                    bubbleNavigationLinearView.setVisibility(View.VISIBLE);
                }
                else if (item.getItemId() == R.id._item_setting)
                {
                    Fragment selectedScreen = SettingStudent.createFor();
                    showFragment(selectedScreen);
                    bubbleNavigationLinearView.setVisibility(View.VISIBLE);


                }
                return false;
            }
        });*/
       bubbleNavigationLinearView.setNavigationChangeListener(new BubbleNavigationChangeListener() {
            @Override
            public void onNavigationChanged(View view, int position) {
                if(position == 0){
                    Fragment selectedScreen = HomeStudent.createFor();
                    showFragment(selectedScreen);
                    bubbleNavigationLinearView.setVisibility(View.VISIBLE);

                    // enableViews(false, "");
                }else  if(position == 1){
                    Fragment selectedScreen = ProfileStudent.createFor();
                    showFragment(selectedScreen);
                    bubbleNavigationLinearView.setVisibility(View.VISIBLE);

                    // enableViews(false, "");
                }
                else  if(position == 2){
                    Fragment selectedScreen = SettingStudent.createFor();
                    showFragment(selectedScreen);
                    bubbleNavigationLinearView.setVisibility(View.VISIBLE);

                    // enableViews(false, "");
                }
            }
        });



       // bubbleNavigationLinearView.setTypeface(Typeface.createFromAsset(getAssets(), String.valueOf(R.font.arabic_1)));

        bubbleNavigationLinearView.setBadgeValue(0, "");
        bubbleNavigationLinearView.setBadgeValue(1, "40"); //invisible badge
        bubbleNavigationLinearView.setBadgeValue(2, "");


        //Default Home
        fab.setVisibility(View.GONE);
        toolbar.setTitle("Home");

        Fragment selectedScreen = HomeStudent.createFor();
        showFragment(selectedScreen);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_student);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }



    private void showFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container_student, fragment)
                .addToBackStack(null)
                .commitAllowingStateLoss();
    }

    @Override
    protected void onStart() {

        super.onStart();

        EventBus.getDefault().register(this);

    }

    @Override
    protected void onStop() {
        super.onStop();

        EventBus.getDefault().unregister(this);


    }


    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onPassMessageSelected(PassMassageActionClick event) {
       if (event.getMsg().equals("DisplayFloatingActionButton")) {

            fab.setVisibility(View.VISIBLE);

        }else if (event.getMsg().equals("HiddenFloatingActionButton")) {
           fab.setVisibility(View.INVISIBLE);


        }else if (event.getMsg().equals("openChat")) {

           getSupportActionBar().hide();
           Fragment selectedScreen = ChatDetialsStudentFragment.createFor();
           showFragment(selectedScreen);
           bubbleNavigationLinearView.setVisibility(View.INVISIBLE);


       }else if (event.getMsg().equals("backChat")) {
           getSupportActionBar().show();
           Fragment selectedScreen = ChatStudent.createFor();
           showFragment(selectedScreen);
           bubbleNavigationLinearView.setVisibility(View.VISIBLE);
       }else if (event.getMsg().equals("setting")) {
           getSupportActionBar().show();
           Fragment selectedScreen = SettingStudent.createFor();
           showFragment(selectedScreen);
           bubbleNavigationLinearView.setVisibility(View.VISIBLE);
       }
       else if (event.getMsg().equals("SignOut")) {
           auth.signOut();
           startActivity(new Intent(this, Login.class));


       }

    }


    private void enableViews(boolean enable, String FragmentBack) {


        if (enable) {
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            toggle.setDrawerIndicatorEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            if (!mToolBarNavigationListenerIsRegistered) {
                toggle.setToolbarNavigationClickListener(v -> {
                    if (FragmentBack.equals("ClubDetials")) {

                        enableViews(false, "");

                    } else {
                        getSupportFragmentManager().popBackStack();

                        enableViews(false, "");
                    }

                });

                mToolBarNavigationListenerIsRegistered = true;
            }

        } else {
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);

            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            toggle.setDrawerIndicatorEnabled(true);
            toggle.setToolbarNavigationClickListener(v -> {

            });
            mToolBarNavigationListenerIsRegistered = false;
        }


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }
}
