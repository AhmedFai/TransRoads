package com.example.user.transroads;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


public class HomeFragment extends Fragment {

    RecyclerView homeRecycler;
    homeCardAdapter adapter;
    GridLayoutManager manager;

    ImageView image;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home,container,false);

        homeRecycler = (RecyclerView) view.findViewById(R.id.homeRecycler);
        manager = new GridLayoutManager(getContext(),1);
        adapter = new homeCardAdapter(getContext());
        homeRecycler.setLayoutManager(manager);
        homeRecycler.setAdapter(adapter);

        image = (ImageView) view.findViewById(R.id.profileImage);



        return view;
    }

}
