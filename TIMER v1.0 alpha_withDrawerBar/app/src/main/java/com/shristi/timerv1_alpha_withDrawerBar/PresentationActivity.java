package com.shristi.timerv1_alpha_withDrawerBar;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.shristi.timerv1_alpha_withDrawerBar.SetupDataActivity;
import com.shristi.timerv1_alpha_withDrawerBar.TimerSetup;

public class PresentationActivity extends AppCompatActivity {

    boolean firstRun11 = true;
    boolean firstRun12 = true;
    boolean firstRun21 = true;
    boolean firstRun22 = true;
    boolean haltSlideCountdown = false;
    boolean isPaused = false;
    boolean blinkYes = true;
    boolean blinkstopCountdown = false;

    int excessTime = 0;
    int elapsedTime = 0;
    //Per Slide Data
    int currentSlideIndex = -1;      // denotes the index of slide that is in progress
    int timeRemaining = 0;          // total time for slide
    int totalTimeRemaining = SetupDataActivity.totalTime;   // Total presentation time
    int totalTime = SetupDataActivity.totalTime;
    long slideStartTime = 0;    // stores start time of new slide
    long pauseTime, resumeTime;
    //to track if a slide is finished or not
    int countDown;
    int countDownTotal;
    // for blinking and stop
    int waitBlink1 = 0;
    int waitBlink2 = 0;
    int wait30s_blink = 6;
    int wait30s_stop = 14;

    TextView tvSlideName, tvCountDown, tvTotalTime, tvProgress;
    TextView tvCurrentSlideCountDownCaption, tvPresentationCountDownCaption;
    Button btnNext, btnPause, btnStop;
    LinearLayout slidePresentation, layoutButtons;
    Handler timerHandler = new Handler();
    AlertDialog alertDialog;
    Vibrator v; //get vibrator instance

    Runnable timerRunnable = new Runnable() {
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

            //Decrease the remaining times in Countdown and TotalTimeRemaining
            if (!haltSlideCountdown) {
                tvCountDown.setText("" + countDown / 60 + "m " + countDown % 60 + "s");
            } else {
                tvCountDown.setText("0m 0s");
            }

            tvCountDown.setTextColor(Color.BLACK);
            tvCountDown.setTypeface(null, Typeface.BOLD);
            tvTotalTime.setText("" + countDownTotal / 60 + "m " + countDownTotal % 60 + "s");
            tvTotalTime.setTextColor(Color.BLACK);
            tvTotalTime.setTypeface(null, Typeface.BOLD);

            /* ********** slide color for count down ********** */
            if (countDown <= (TimerSetup.seriouslyLagSlide * timeRemaining) / 100) {
                tvCountDown.setTextColor(Color.RED);

                if(waitBlink1<wait30s_blink && !blinkstopCountdown){
                    if (blinkYes){
                        tvCountDown.setVisibility(View.VISIBLE);
                        blinkYes = false;
                    }else {
                        tvCountDown.setVisibility(View.INVISIBLE);
                        blinkYes = true;
                    }
                    waitBlink1++;
                } else{
                    tvCountDown.setVisibility(View.VISIBLE);
                    waitBlink1++;
                    if (waitBlink1>wait30s_stop)
                        waitBlink1=0;
                }

                if (firstRun12) {
                    v.vibrate(1000);
                    firstRun12 = false;
                }
            } else if (countDown <= (TimerSetup.lagSlide * timeRemaining) / 100) {
                tvCountDown.setTextColor(Color.YELLOW);
                if (firstRun11) {
                    v.vibrate(1000);
                    firstRun11 = false;
                }
            } else {
                tvCountDown.setTextColor(Color.GREEN);
            }

            /* ********** Slide color for total time remaining ********** */
            if (countDownTotal <= (TimerSetup.seriouslyLagSlide * totalTime) / 100) {
                tvTotalTime.setTextColor(Color.RED);
                blinkstopCountdown = true;

                if(waitBlink2<wait30s_blink){
                    if (blinkYes){
                        tvTotalTime.setVisibility(View.VISIBLE);
                        blinkYes = false;
                    }else {
                        tvTotalTime.setVisibility(View.INVISIBLE);
                        blinkYes = true;
                    }
                    waitBlink2++;
                } else{
                    tvTotalTime.setVisibility(View.VISIBLE);
                    waitBlink2++;
                    if (waitBlink2>wait30s_stop)
                        waitBlink2=0;
                }
                if (firstRun22) {
                    v.vibrate(1000);
                    firstRun22 = false;
                }
            } else {
                tvTotalTime.setTextColor(Color.GREEN);
            }

            if (countDown <= 0 || totalTimeRemaining <= 0) {
                //Slide has finished increment currentSlideIndex for next slide
                if (currentSlideIndex + 1 >= SetupDataActivity.items.size()) {
                    //No more slides to display.End the presentation; To halt slide remaining time countdown when presentation ends
                    haltSlideCountdown = true;
                }
            }
            timerHandler.postDelayed(this, 500);
        }
    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_presentation);
       // ReplaceFont.replaceDefaultFont(this,"DEFAULT","calibre-regular.ttf");

        v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        tvSlideName = (TextView) findViewById(R.id.tvSlideName);
        tvCountDown = (TextView) findViewById(R.id.tvCountDown);
        tvTotalTime = (TextView) findViewById(R.id.tvTotalTime);
        slidePresentation = (LinearLayout) findViewById(R.id.slidePresentation);
        layoutButtons = (LinearLayout) findViewById(R.id.layoutButtons);
        tvProgress = (TextView) findViewById(R.id.tvProgress);
        tvCurrentSlideCountDownCaption = (TextView) findViewById(R.id.tvCurrentSlideCountDownCaption);
        tvPresentationCountDownCaption = (TextView) findViewById(R.id.tvPresentationCountDownCaption);
        btnNext = (Button) findViewById(R.id.btnNext);
        btnPause = (Button) findViewById(R.id.btnPause);
        btnStop = (Button) findViewById(R.id.btnStop);

        //disable screen timeout
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setAndStartNewSlide();

        //Alert dialog setup for stop button press; ask for restart or end
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Presentation Restart or End");
        builder.setPositiveButton("End", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();       //End
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

        // Three buttons, Next, Pause and Stop
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvTotalTime.setVisibility(View.VISIBLE);
                tvCountDown.setVisibility(View.VISIBLE);
                waitBlink1=0;

                //Slide is finished earlier than expected
                if (currentSlideIndex + 1 >= SetupDataActivity.items.size()) {
                    toast("No more slides to display"); //No more slides to display.. End the presentation
                } else {
                    excessTime = countDown;     //Calculate excess time
                    isPaused = false;
                    setAndStartNewSlide();      //If more slides available; Set and start new slide
                    //Add or subtract time in next slide if quick finish or late finish
                    timeRemaining += excessTime;
                }
            }
        });

        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvTotalTime.setVisibility(View.VISIBLE);
                tvCountDown.setVisibility(View.VISIBLE);
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
        //ask restart or end
        btnStop.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                alertDialog.show();
                blinkstopCountdown = false;
            }
        });
    }

    public void setAndStartNewSlide() {
        firstRun11 = true;
        firstRun12 = true;
        firstRun21 = true;
        firstRun22 = true;

        currentSlideIndex++;
        if (currentSlideIndex >= SetupDataActivity.items.size()) {
            return;
        }
        totalTimeRemaining -= elapsedTime;

        tvSlideName.setText(SetupDataActivity.itemsSlideName.get(currentSlideIndex));   //Set slideName
        timeRemaining = SetupDataActivity.itemsTimeRequired.get(currentSlideIndex);     //set slide remaining time
        slideStartTime = System.currentTimeMillis();        //set slide startTime

        tvProgress.setText("" + (currentSlideIndex + 1) + "/" + SetupDataActivity.itemsSlideName.size());
        toast("Slide: " + tvSlideName.getText());

        timerHandler.removeCallbacks(timerRunnable);
        timerHandler.postDelayed(timerRunnable, 500);
    }

    protected void onPause() {
        super.onPause();
        timerHandler.removeCallbacks(timerRunnable);
    }

    public void toast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

}
