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



public class FavoriteSpot extends Fragment {


        RecyclerView favoriteRecycle;
        ReviewCardAdapter adapter;
        GridLayoutManager manager;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_favorite_spot,container,false);

       favoriteRecycle = (RecyclerView) view.findViewById(R.id.favoriteRecycler);
       manager = new GridLayoutManager(getContext(), 2);
       adapter = new ReviewCardAdapter(getContext());
       favoriteRecycle.setLayoutManager(manager);
       favoriteRecycle.setAdapter(adapter);
       return view;
    }







}
