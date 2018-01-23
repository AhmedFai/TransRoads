package com.example.user.transroads;

import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


public class HomeFragment extends Fragment {

    RecyclerView homeRecycler;
    homeCardAdapter adapter;
    GridLayoutManager manager;

    ImageView image;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.home_button, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_filter) {

            final Dialog dialog = new Dialog(getContext());
            dialog.setContentView(R.layout.home_filter_dialog);
            dialog.setCancelable(true);
            dialog.show();

            return true;
        }

        return super.onOptionsItemSelected(item);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        homeRecycler = (RecyclerView) view.findViewById(R.id.homeRecycler);
        manager = new GridLayoutManager(getContext(), 1);
        adapter = new homeCardAdapter(getContext());
        homeRecycler.setLayoutManager(manager);
        homeRecycler.setAdapter(adapter);
        image = (ImageView) view.findViewById(R.id.profileImage);

        return view;
    }

}
