package com.shristi.timer;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Time;

public class StartActivity extends AppCompatActivity {

    //public static int seriouslyLagOverallTime = 0;
    //public static int lagOverallTime = 0;

    int countDownTotalElapsedTime = 0;

    ImageButton btnStart;
    TextView tvSlideName, tvCountDown, tvTotalTime, tvProgress;
    Button btnExtra;

    Button btnNext, btnPause, btnStop;

    LinearLayout slidePresentation;

    Handler timerHandler = new Handler();

    int elapsedTime = 0;
    boolean isPaused = false;

    //Per Slide Data
    int currentSlideIndex = -1;      // denotes the index of slide that is in progress
    int timeRemaining = 0;          // total time for slide

    int totalTimeRemaining = SetupDataActivity.totalTime;   // Total presentation time

    long slideStartTime = 0;    // stores start time of new slide

    long pauseTime, resumeTime;

    //to track if a slide is finished or not
    int countDown;
    int countDownTotal;

    AlertDialog alertDialog;

    //get vibrator instance
    Vibrator v;

    Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {
            long slideMs = System.currentTimeMillis() - slideStartTime;

            //ELapsed time in seconds
            int time = (int) slideMs / 1000;

            if (!isPaused) {
                elapsedTime = time;
            }
            //Adjust min or sec
            countDown = timeRemaining - elapsedTime;
            countDownTotal = totalTimeRemaining - elapsedTime;

            //Decrease the remaining times in Coundown and TotalTimeRemaining
            tvCountDown.setText("" + countDown / 60 + "m " + countDown % 60 + "s");
            tvTotalTime.setText("" + countDownTotal / 60 + "m " + countDownTotal % 60 + "s");

            //Slide color
            if (countDown <= TimerSetup.seriouslyLagSlide) {
                tvCountDown.setBackgroundColor(Color.RED);
            } else if (countDown <= TimerSetup.lagSlide) {
                tvCountDown.setBackgroundColor(Color.YELLOW);
            } else {
                tvCountDown.setBackgroundColor(Color.GREEN);
            }
            //Overall color
            if (countDownTotal <= TimerSetup.seriouslyLagSlide) {
                tvTotalTime.setBackgroundColor(Color.RED);
            } else if (countDownTotal <= TimerSetup.lagSlide) {
                tvTotalTime.setBackgroundColor(Color.YELLOW);
            } else {
                tvTotalTime.setBackgroundColor(Color.GREEN);
            }

            if (countDown <= 0) {
                //Slide has finished increment currentSlideIndex for next slide

                if (currentSlideIndex + 1 >= SetupDataActivity.items.size()) {
                    //No more slides to display.. End the presentation
                    timerHandler.removeCallbacks(timerRunnable);
                    finish();
                } else {
                    //If more slides available
                    //Set and start new slide
                    //totalTimeRemaining -= timeRemaining;

                    isPaused = true;
                    //setAndStartNewSlide();
                }
            }

            timerHandler.postDelayed(this, 500);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        //disable screen timeout
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        btnStart = (ImageButton) findViewById(R.id.btnStart);
        tvSlideName = (TextView) findViewById(R.id.tvSlideName);
        tvCountDown = (TextView) findViewById(R.id.tvCountDown);
        tvTotalTime = (TextView) findViewById(R.id.tvTotalTime);
        btnExtra = (Button) findViewById(R.id.btnExtra);
        slidePresentation = (LinearLayout) findViewById(R.id.slidePresentation);
        tvProgress = (TextView) findViewById(R.id.tvProgress);

        btnNext = (Button) findViewById(R.id.btnNext);
        btnPause = (Button) findViewById(R.id.btnPause);
        btnStop = (Button) findViewById(R.id.btnStop);

        //Alertdialog setup for stop button press
        //ask for restart or end
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Presentation Restart or End");
        builder.setPositiveButton("End", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //End
                finish();
            }
        });
        builder.setNegativeButton("Restart", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //restart
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        });
        alertDialog = builder.create();

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
                //currentSlideIndex++;

                if (currentSlideIndex >= SetupDataActivity.items.size()) {
                    //No more slides to display.. End the presentation
                    timerHandler.removeCallbacks(timerRunnable);

                    finish();
                } else {
                    //Calculate excess time
                    int excessTime = countDown;

                    //totalTimeRemaining -= timeRemaining;
                    totalTimeRemaining += excessTime;

                    //If more slides available
                    //Set and start new slide

                    isPaused = false;

                    setAndStartNewSlide();
                }
            }
        });

        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //pause
                if (!isPaused) {
                    pauseTime = System.currentTimeMillis();

                    isPaused = true;
                    btnPause.setText("Resume");
                    toast("Paused");
                } else if (isPaused) {
                    resumeTime = System.currentTimeMillis();

                    slideStartTime = slideStartTime + (resumeTime - pauseTime);

                    isPaused = false;
                    btnPause.setText("Pause");
                    toast("Resumed");
                }

            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //ask restart or end
                alertDialog.show();
            }
        });
    }

    public void setAndStartNewSlide() {

        //isPaused = false;

        currentSlideIndex++;

        totalTimeRemaining -= timeRemaining;

        //Set Vibration here for each slide change
        v.vibrate(1000);

        //Set slideName
        tvSlideName.setText(SetupDataActivity.itemsSlideName.get(currentSlideIndex));
        //set slide remaining time
        timeRemaining = SetupDataActivity.itemsTimeRequired.get(currentSlideIndex);
        //set slide startTime
        slideStartTime = System.currentTimeMillis();

        tvProgress.setText("" + (currentSlideIndex + 1) + "/" + SetupDataActivity.itemsSlideName.size());

        toast("Slide: " + tvSlideName.getText());

        timerHandler.postDelayed(timerRunnable, 1000);
    }

    @Override
    protected void onPause() {
        super.onPause();

        timerHandler.removeCallbacks(timerRunnable);
    }


    /* Helper Methods */

    private void addTime() {

    }

    private void subtractTime() {

    }

    public void toast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

}
