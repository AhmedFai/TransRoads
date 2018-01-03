package com.example.user.transroads;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

public class SplashScreen extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 5000;
    private TextView Title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        //getSupportActionBar().hide();

        new Handler().postDelayed(new Runnable() {
            public void run() {
                Intent intent = new Intent(SplashScreen.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_TIME_OUT);

        Title = (TextView) findViewById(R.id.appTitle);
        Typeface face = Typeface.createFromAsset(getAssets(),"fonts/DIRTF.TTF");
        Title.setTypeface(face);
    }
}
