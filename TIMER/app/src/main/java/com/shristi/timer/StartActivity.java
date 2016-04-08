package com.shristi.timer;

import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class StartActivity extends AppCompatActivity {

    ImageButton btnStart;
    TextView tvSlideName, tvCountDown, tvTotalTime, tvProgress;
    Button btnExtra, btnNext;
    LinearLayout slidePresentation;

    Handler timerHandler = new Handler();

    //Per Slide Data
    int currentSlideIndex = 0;      // denotes the index of slide that is in progress
    int timeRemaining = 0;          // total time for slide

    int totalTimeRemaining = EnterData.totalTime;   // Total presentation time

    long slideStartTime = 0;    // stores start time of new slide

    //to track if a slide is finished or not
    int countDown;
    int countDownTotal;

    Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {
            long slideMs = System.currentTimeMillis() - slideStartTime;

            //ELapsed time in seconds
            int elapsedTime = (int) slideMs / 1000;

            //Adjust min or sec
            countDown = timeRemaining - elapsedTime;
            countDownTotal = totalTimeRemaining - elapsedTime;

            //Decrease the remaining times in Coundown and TotalTimeRemaining
            tvCountDown.setText("" + countDown);
            tvTotalTime.setText("" + countDownTotal);

            if (countDown <= 10) {
                tvCountDown.setTextColor(Color.RED);
            } else {
                tvCountDown.setTextColor(Color.GREEN);
            }
            if (countDownTotal <= 10) {
                tvTotalTime.setTextColor(Color.RED);
            } else {
                tvTotalTime.setTextColor(Color.GREEN);
            }

            if (countDown <= 0) {
                //Slide has finished increment currentSlideIndex for next slide
                currentSlideIndex++;

                if (currentSlideIndex >= EnterData.items.size()) {
                    //No more slides to display.. End the presentation
                    timerHandler.removeCallbacks(timerRunnable);
                    finish();
                } else {
                    //If more slides available
                    //Set and start new slide
                    totalTimeRemaining -= timeRemaining;

                    setAndStartNewSlide();
                }
            }

            timerHandler.postDelayed(this, 500);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        btnStart = (ImageButton) findViewById(R.id.btnStart);
        tvSlideName = (TextView) findViewById(R.id.tvSlideName);
        tvCountDown = (TextView) findViewById(R.id.tvCountDown);
        tvTotalTime = (TextView) findViewById(R.id.tvTotalTime);
        btnExtra = (Button) findViewById(R.id.btnExtra);
        btnNext = (Button) findViewById(R.id.btnNext);
        slidePresentation = (LinearLayout) findViewById(R.id.slidePresentation);
        tvProgress = (TextView) findViewById(R.id.tvProgress);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                btnStart.setVisibility(View.GONE);
                slidePresentation.setVisibility(View.VISIBLE);

                //Initialize for first slide and start timer
                setAndStartNewSlide();
            }
        });

        btnExtra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Add extra time
                int extraTime = 10;

                timeRemaining += extraTime;
                totalTimeRemaining += extraTime;
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Slide is finished earlier than expected
                currentSlideIndex++;

                if (currentSlideIndex >= EnterData.items.size()) {
                    //No more slides to display.. End the presentation
                    timerHandler.removeCallbacks(timerRunnable);

                    finish();
                } else {
                    //Calculate excess time
                    int excessTime = countDown;

                    totalTimeRemaining -= timeRemaining;
                    totalTimeRemaining += excessTime;

                    //If more slides available
                    //Set and start new slide

                    setAndStartNewSlide();
                }
            }
        });
    }

    public void setAndStartNewSlide() {
        //Set slideName
        tvSlideName.setText(EnterData.itemsSlideName.get(currentSlideIndex));
        //set slide remaining time
        timeRemaining = EnterData.itemsTimeRequired.get(currentSlideIndex);
        //set slide startTime
        slideStartTime = System.currentTimeMillis();

        tvProgress.setText("" + (currentSlideIndex + 1) + "/" + EnterData.itemsSlideName.size());

        toast("Slide: " + tvSlideName.getText());

        timerHandler.postDelayed(timerRunnable, 0);
    }

    @Override
    protected void onPause() {
        super.onPause();

        timerHandler.removeCallbacks(timerRunnable);
    }


    //* Helper Methods *//
    private void addTime() {

    }

    private void subtractTime() {

    }

    public void toast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

}
