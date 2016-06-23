

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

    //public static int seriouslyLagOverallTime = 0;
    //public static int lagOverallTime = 0;

    boolean once = false;

    boolean firstRun11 = true;
    boolean firstRun12 = true;
    boolean firstRun21 = true;
    boolean firstRun22 = true;

    boolean isCountDownAnimating = false;
    boolean isCountDownTotalAnimating = false;

    int excessTime = 0;

    boolean haltSlideCountdown = false;
    boolean compensateTime = false;

    Animation anim;

    int progressHeight = 0;

    int countDownTotalElapsedTime = 0;
    int textSizeBig = 60;

    //ImageButton btnStart;
    TextView tvSlideName, tvCountDown, tvTotalTime, tvProgress;

    TextView progress;

    TextView tvSlideNameCaption, tvCurrentSlideCountDownCaption, tvPresentationCountDownCaption;

    //Button btnExtra;

    Button btnNext, btnPause, btnStop;

    LinearLayout slidePresentation, layoutButtons;

    Handler timerHandler = new Handler();

    int elapsedTime = 0;
    boolean isPaused = false;

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


            DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
            int heightPixels = metrics.heightPixels - 150;

            //progressHeight = elapsedTime * heightPixels / timeRemaining;
            //progress.setHeight(progressHeight);
            //Log.d("Height", "Height: " + progressHeight);

            //Adjust min or sec
            countDown = timeRemaining - elapsedTime;
            countDownTotal = totalTimeRemaining - elapsedTime;

            //Decrease the remaining times in Coundown and TotalTimeRemaining
            if (!haltSlideCountdown) {
                tvCountDown.setText("" + countDown / 60 + "m " + countDown % 60 + "s");
            } else {
                tvCountDown.setText("0m 0s");
            }
            //tvCountDown.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSizeBig);
            //tvCountDown.setBackgroundColor(Color.BLACK);
            tvCountDown.setTextColor(Color.BLACK);
            //tvCountDown.setGravity(View.TEXT_ALIGNMENT_CENTER);
            //tvCountDown.setGravity(Gravity.CENTER_HORIZONTAL);
            tvCountDown.setTypeface(null, Typeface.BOLD);

            tvTotalTime.setText("" + countDownTotal / 60 + "m " + countDownTotal % 60 + "s");
            //tvTotalTime.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSizeBig);
            //tvTotalTime.setBackgroundColor(Color.BLACK);
            tvTotalTime.setTextColor(Color.BLACK);
            //tvTotalTime.setGravity(View.TEXT_ALIGNMENT_CENTER);
            //tvTotalTime.setGravity(Gravity.CENTER_HORIZONTAL);
            tvTotalTime.setTypeface(null, Typeface.BOLD);

            //Slide color for count down
            if (countDown <= (TimerSetup.seriouslyLagSlide * timeRemaining) / 100) {
                //tvSlideName.setTextColor(Color.rgb(0,150,200));
                tvCountDown.setTextColor(Color.RED);
                //tvTotalTime.setTextColor(Color.RED);
                startAnimate();
                if (firstRun12) {
                    v.vibrate(1000);
                    firstRun12 = false;
                }


            } else if (countDown <= (TimerSetup.lagSlide * timeRemaining) / 100) {
                tvCountDown.setTextColor(Color.YELLOW);
                //tvTotalTime.setTextColor(Color.YELLOW);
                if (firstRun11) {
                    v.vibrate(1000);
                    firstRun11 = false;
                }


            } else {
                tvCountDown.setTextColor(Color.GREEN);
                //tvTotalTime.setTextColor(Color.GREEN);
            }

            //Slide color for total time remaining
            if (countDownTotal <= (TimerSetup.seriouslyLagSlide * totalTime) / 100) {
                //tvCountDown.setTextColor(Color.RED);
                tvTotalTime.setTextColor(Color.RED);
                //if (!isPaused) {
                //  tvTotalTime.startAnimation(anim);
                //}
                startAnimate();
                if (firstRun22) {
                    v.vibrate(1000);
                    firstRun22 = false;
                }


            } else if (countDownTotal <= (TimerSetup.lagSlide * totalTime) / 100) {
                //tvCountDown.setTextColor(Color.YELLOW);
                tvTotalTime.setTextColor(Color.YELLOW);
                if (firstRun21) {
                    v.vibrate(1000);
                    firstRun21 = false;
                }


            } else {
                //tvCountDown.setTextColor(Color.GREEN);
                tvTotalTime.setTextColor(Color.GREEN);
            }

            /*
            //Overall color
            if (countDownTotal <= TimerSetup.seriouslyLagSlide) {
                //tvTotalTime.setBackgroundColor(Color.RED);
            } else if (countDownTotal <= TimerSetup.lagSlide) {
                //tvTotalTime.setBackgroundColor(Color.YELLOW);
            } else {
                //tvTotalTime.setBackgroundColor(Color.GREEN);
            }*/

            if (countDown <= 0 || totalTimeRemaining <= 0) {
                //Slide has finished increment currentSlideIndex for next slide

                if (currentSlideIndex + 1 >= SetupDataActivity.items.size()) {
                    //No more slides to display.. End the presentation

                    //To halt slide remaining time countdown when presentation ends
                    haltSlideCountdown = true;
                }
                //progress.startAnimation(anim);
                //if (!isPaused) {
                //    startAnimate();
                //}

            }
            timerHandler.postDelayed(this, 500);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_presentation);


        v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        //disable screen timeout
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);


        // btnStart = (ImageButton) findViewById(R.id.btnStart);
        tvSlideName = (TextView) findViewById(R.id.tvSlideName);
        tvCountDown = (TextView) findViewById(R.id.tvCountDown);
        tvTotalTime = (TextView) findViewById(R.id.tvTotalTime);
        //btnExtra = (Button) findViewById(R.id.btnExtra);
        slidePresentation = (LinearLayout) findViewById(R.id.slidePresentation);
        layoutButtons = (LinearLayout) findViewById(R.id.layoutButtons);
        tvProgress = (TextView) findViewById(R.id.tvProgress);

        //tvSlideNameCaption = (TextView) findViewById(R.id.tvSlideNameCaption);
        tvCurrentSlideCountDownCaption = (TextView) findViewById(R.id.tvCurrentSlideCountDownCaption);
        tvPresentationCountDownCaption = (TextView) findViewById(R.id.tvPresentationCountDownCaption);

        //progress = (TextView) findViewById(R.id.progress);

        btnNext = (Button) findViewById(R.id.btnNext);
        btnPause = (Button) findViewById(R.id.btnPause);
        btnStop = (Button) findViewById(R.id.btnStop);
        //tvSlideName.setTextColor(Color.rgb(0, 150, 200));


        //Setup progress
        //progress.setVisibility(View.VISIBLE);
        //progress.setHeight(1);
        //progress.setBackgroundColor(Color.GREEN);

        anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(500);                      //Manage the time of the blink
        anim.setStartOffset(20);
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(Animation.INFINITE);

        setAndStartNewSlide();

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


        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Slide is finished earlier than expected
                //currentSlideIndex++;

                if (currentSlideIndex + 1 >= SetupDataActivity.items.size()) {
                    //No more slides to display.. End the presentation
                    //timerHandler.removeCallbacks(timerRunnable);

                    toast("No more slides to display");
                    //finish();
                } else {
                    //Calculate excess time
                    excessTime = countDown;

                    //If more slides available
                    //Set and start new slide

                    isPaused = false;

                    setAndStartNewSlide();

                    //Add or subtract time in next slide if quick finish or late finish
                    timeRemaining += excessTime;
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
                    stopAnimate();
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

        //progress.startAnimation(anim);
        //progress.clearAnimation();
        stopAnimate();

        firstRun11 = true;
        firstRun12 = true;
        firstRun21 = true;
        firstRun22 = true;

        currentSlideIndex++;

        if (currentSlideIndex >= SetupDataActivity.items.size()) {
            return;
        }

        totalTimeRemaining -= elapsedTime;

        //Set Vibration here for each slide change
        // v.vibrate(1000);

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

    public void startAnimate() {
        if (!isPaused) {
            if (countDown <= 0) {
                if (!isCountDownAnimating) {
                    tvCountDown.startAnimation(anim);
                    isCountDownAnimating = true;
                }
            }
            if (countDownTotal <= 0) {
                if (!isCountDownTotalAnimating) {
                    tvTotalTime.startAnimation(anim);
                    isCountDownTotalAnimating = true;
                }
            }
        }
    }

    public void stopAnimate() {
        tvTotalTime.clearAnimation();
        tvCountDown.clearAnimation();

        isCountDownAnimating = false;
        isCountDownTotalAnimating = false;
    }

    public void toast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

}
