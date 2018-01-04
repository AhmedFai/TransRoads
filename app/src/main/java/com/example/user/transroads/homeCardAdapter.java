package com.example.user.transroads;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by faizan on 12/26/2017.
 */

public class homeCardAdapter extends RecyclerView.Adapter<homeCardAdapter.MyViewHolder> {

    Context context;

    public homeCardAdapter(Context context){

        this.context = context;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.homecard, parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView backimage, heart, profile;
        TextView title;

        public MyViewHolder(View itemView) {
            super(itemView);

            backimage = (ImageView) itemView.findViewById(R.id.backImage);
           // heart = (ImageView) itemView.findViewById(R.id.heartImage);
            profile = (ImageView) itemView.findViewById(R.id.profileImage);
            title = (TextView) itemView.findViewById(R.id.title);
        }
    }
}
