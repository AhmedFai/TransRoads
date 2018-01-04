package com.example.user.transroads;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;


public class ProfileFragment extends Fragment {

    private FirebaseAuth firebaseAuth;

    TabLayout tabLayout;
    public static MyViewPager pager;
    ProfilePager adapter;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseAuth = FirebaseAuth.getInstance();
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO Add your menu entries here
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.item_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.signOut) {

            new AlertDialog.Builder(getActivity())
                    .setMessage("Are you Sure?")
                    .setPositiveButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // add your logout code here
                        }
                    })
                    .setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // dismiss dialog here because user doesn't want to logout
                            FirebaseAuth.getInstance().signOut();
                            LoginManager.getInstance().logOut();
                            Intent intent = new Intent(getActivity(), LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }
                    })
                    .show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile,container,false);


        tabLayout = (TabLayout) view.findViewById(R.id.tabLayout);
        pager = (MyViewPager) view.findViewById(R.id.pager);
        pager.setSwipeable(true);


        tabLayout.addTab(tabLayout.newTab().setText("Favorite Spot"));
        tabLayout.addTab(tabLayout.newTab().setText("My Reviews"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        adapter = new ProfilePager(getChildFragmentManager(), tabLayout.getTabCount());
        pager.setAdapter(adapter);
        tabLayout.setupWithViewPager(pager);

       tabLayout.getTabAt(0).setText("Favorite Spot");
       tabLayout.getTabAt(1).setText("My Review");

        return view;
    }

    public class ProfilePager extends FragmentStatePagerAdapter {
        int tab;

        public ProfilePager(FragmentManager fm, int List) {
            super(fm);
            this.tab = List;
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return new FavoriteSpot();
            } else if (position == 1) {
                return new MyReview();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 2;
        }
    }
}
