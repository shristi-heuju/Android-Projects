package com.shristi.profile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageButton;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //Animation for flashing text view
        TextView tvFlash = (TextView) findViewById(R.id.tvFlash);

        Animation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(50); //Manage the time of the blink
        anim.setStartOffset(20);
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(Animation.INFINITE);
        tvFlash.startAnimation(anim);

        setupButtonListeners(HomeActivity.this);

    }

    public void setupButtonListeners(final Context context) {

        ImageButton btnHome = (ImageButton) findViewById(R.id.btnHome);
        ImageButton btnNews = (ImageButton) findViewById(R.id.btnNews);
        ImageButton btnEvents = (ImageButton) findViewById(R.id.btnEvents);
        ImageButton btnMisc = (ImageButton) findViewById(R.id.btnMisc);
        ImageButton btnContact = (ImageButton) findViewById(R.id.btnContact);

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, HomeActivity.class);
                startActivity(intent);
            }
        });

        btnNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, NewsActivity.class);
                startActivity(intent);
            }
        });

        btnEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, EventsActivity.class);
                startActivity(intent);
            }
        });

        btnMisc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MiscActivity.class);
                startActivity(intent);
            }
        });

        btnContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ContactActivity.class);
                startActivity(intent);
            }
        });
    }
}

