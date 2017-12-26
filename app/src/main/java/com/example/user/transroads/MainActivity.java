package com.example.user.transroads;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    AHBottomNavigation bottom;
    Toolbar toolbar;

    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        bottom = (AHBottomNavigation)findViewById(R.id.bottom_navigation);
        setSupportActionBar(toolbar);
       toolbar.setTitleTextColor(Color.WHITE);
      getSupportActionBar().setDisplayShowHomeEnabled(true);

        //get firebase auth instance
        firebaseAuth = FirebaseAuth.getInstance();

        //get current user
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    finish();
                }
            }
        };



        AHBottomNavigationItem item1 =
                new AHBottomNavigationItem("Home", R.drawable.ic_home_black);

        AHBottomNavigationItem item2 =
                new AHBottomNavigationItem("Map", R.drawable.map);

        AHBottomNavigationItem item3 =
                new AHBottomNavigationItem("Profile", R.drawable.profile);


        bottom.addItem(item1);
        bottom.addItem(item2);
        bottom.addItem(item3);


        bottom.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);

        bottom.setDefaultBackgroundColor(Color.parseColor("#ffffff"));
        bottom.setAccentColor(Color.parseColor("#336E9A"));
      //  bottom.setInactiveColor(Color.parseColor("#000000"));
        bottom.setCurrentItem(0);

        bottom.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {

                switch (position) {

                    case 0:

                        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

                        while (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                            getSupportFragmentManager().popBackStackImmediate();
                        }

                        HomeFragment frag1 = new HomeFragment();
                        ft.replace(R.id.replace, frag1);
                        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
                        ft.commit();

                        toolbar.setTitle("Transroads");

                        return true;

                    case 1:

                        FragmentTransaction ft2 = getSupportFragmentManager().beginTransaction();

                        while (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                            getSupportFragmentManager().popBackStackImmediate();
                        }

                        MapFragment frag3 = new MapFragment();
                        ft2.replace(R.id.replace, frag3);
                        ft2.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
                        ft2.commit();

                        toolbar.setTitle("Map");

                        return true;

                    case 2:

                        FragmentTransaction ft1 = getSupportFragmentManager().beginTransaction();

                        while (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                            getSupportFragmentManager().popBackStackImmediate();
                        }

                        ProfileFragment frag2 = new ProfileFragment();
                        ft1.replace(R.id.replace, frag2);
                        ft1.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
                        ft1.commit();

                        toolbar.setTitle("Profile");


                        return true;


                }

                return false;
            }
        });
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft1 = fm.beginTransaction();
        HomeFragment frag2 = new HomeFragment();
        ft1.replace(R.id.replace, frag2);
        //ft1.addToBackStack(null);
        ft1.commit();


    }

}
