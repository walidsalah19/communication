package com.example.communication.Teacher;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;


import com.example.communication.EventBus.PassMassageActionClick;
import com.example.communication.Login;
import com.example.communication.Splash;
import com.example.communication.Student.Setting.SettingStudent;
import com.example.communication.Student.Setting.sharedprefrance;
import com.example.communication.Teacher.Chat.ChatDetialsTeacherFragment;
import com.example.communication.Teacher.Chat.ChatTeacherFragment;
import com.example.communication.Teacher.Courses.TeacherCourses;
import com.example.communication.Teacher.Profile.ProfileTeacher;
import com.gauravk.bubblenavigation.BubbleNavigationLinearView;
import com.gauravk.bubblenavigation.listener.BubbleNavigationChangeListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.example.communication.R;
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


public class Teacher extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private FirebaseAuth auth;

    @BindView(R.id.fab_teacher)
    FloatingActionButton fab;
    private ActionBarDrawerToggle toggle;
    private boolean mToolBarNavigationListenerIsRegistered = false;
    //BottomNavigationView bubbleNavigationLinearView;
    BubbleNavigationLinearView bubbleNavigationLinearView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_teacher);
        toolbar = (Toolbar) findViewById(R.id.toolbar_teacher);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);
        auth=FirebaseAuth.getInstance();
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout_teacher);
        bubbleNavigationLinearView = findViewById(R.id.t_bottom_navigation_view_linear);
        //bubbleNavigationLinearView.();
     /*  bubbleNavigationLinearView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.l_item_home)
                {
                    Fragment selectedScreen = TeacherCourses.createFor();
                    showFragment(selectedScreen);
                    bubbleNavigationLinearView.setVisibility(View.VISIBLE);
                }
                else if (item.getItemId() == R.id.l_item_profile)
                {
                    Fragment selectedScreen = ProfileTeacher.createFor();
                    showFragment(selectedScreen);
                    bubbleNavigationLinearView.setVisibility(View.VISIBLE);
                }
                else if (item.getItemId() == R.id.l_item_setting)
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

                    Fragment selectedScreen = TeacherCourses.createFor();
                    showFragment(selectedScreen);
                    bubbleNavigationLinearView.setVisibility(View.VISIBLE);

                    // enableViews(false, "");
                }
                else  if(position == 1){
                    Fragment selectedScreen = ProfileTeacher.createFor();
                    showFragment(selectedScreen);
                    bubbleNavigationLinearView.setVisibility(View.VISIBLE);

                    // enableViews(false, "");
                }
               /* else  if(position == 2){
                    Fragment selectedScreen = HomeTeacher.createFor();
                    showFragment(selectedScreen);
                    bubbleNavigationLinearView.setVisibility(View.VISIBLE);

                    // enableViews(false, "");
                }
               else  if(position == 3){
                    Fragment selectedScreen = ChatTeacherFragment.createFor();
                    showFragment(selectedScreen);
                    bubbleNavigationLinearView.setVisibility(View.VISIBLE);

                    // enableViews(false, "");*/

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
       /* bubbleNavigationLinearView.setBadgeValue(3, "");
        bubbleNavigationLinearView.setBadgeValue(4, "");*/


        //Default Home
        fab.setVisibility(View.GONE);
        toolbar.setTitle("Home");

        Fragment selectedScreen = TeacherCourses.createFor();
        showFragment(selectedScreen);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_teacher);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }



    private void showFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container_teacher, fragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        bubbleNavigationLinearView.setVisibility(View.VISIBLE);

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
           Fragment selectedScreen = ChatDetialsTeacherFragment.createFor();
           showFragment(selectedScreen);
           bubbleNavigationLinearView.setVisibility(View.INVISIBLE);


       }else if (event.getMsg().equals("backChat")) {
           getSupportActionBar().show();
           Fragment selectedScreen = ChatTeacherFragment.createFor();
           showFragment(selectedScreen);
           bubbleNavigationLinearView.setVisibility(View.VISIBLE);
       }else if (event.getMsg().equals("setting")) {
           getSupportActionBar().show();
           Fragment selectedScreen = SettingStudent.createFor();
           showFragment(selectedScreen);
           bubbleNavigationLinearView.setVisibility(View.VISIBLE);

       }
       else if(event.getMsg().equals("navigation"))
       {
          /* bubbleNavigationLinearView.setVisibility(View.INVISIBLE);
           bubbleNavigationLinearView.setVisibility(View.VISIBLE);*/
           startActivity(new Intent(Teacher.this,Teacher.class));

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
