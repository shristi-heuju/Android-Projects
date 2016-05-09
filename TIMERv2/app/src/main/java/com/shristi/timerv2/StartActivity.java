package com.shristi.timerv2;

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
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class StartActivity extends AppCompatActivity {

    //public static int seriouslyLagOverallTime = 0;
    //public static int lagOverallTime = 0;

    boolean firstRun1 = true;
    boolean firstRun2 = true;

    int excessTime = 0;

    boolean haltSlideCountdown = false;
    boolean compensateTime = false;

    Animation anim;

    int progressHeight = 0;

    int countDownTotalElapsedTime = 0;
    int textSizeBig = 60;

    ImageButton btnStart;
    TextView tvSlideName, tvCountDown, tvTotalTime, tvProgress;

    TextView progress;

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
            tvCountDown.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSizeBig);
            //tvCountDown.setBackgroundColor(Color.BLACK);
            tvCountDown.setTextColor(Color.BLACK);
            //tvCountDown.setGravity(View.TEXT_ALIGNMENT_CENTER);
            //tvCountDown.setGravity(Gravity.CENTER_HORIZONTAL);
            tvCountDown.setTypeface(null, Typeface.BOLD);

            tvTotalTime.setText("" + countDownTotal / 60 + "m " + countDownTotal % 60 + "s");
            tvTotalTime.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSizeBig);
            //tvTotalTime.setBackgroundColor(Color.BLACK);
            tvTotalTime.setTextColor(Color.BLACK);
            //tvTotalTime.setGravity(View.TEXT_ALIGNMENT_CENTER);
            //tvTotalTime.setGravity(Gravity.CENTER_HORIZONTAL);
            tvTotalTime.setTypeface(null, Typeface.BOLD);

            //Slide color
            if (countDown <= TimerSetup.seriouslyLagSlide) {
                //tvCountDown.setBackgroundColor(Color.RED);
                if (firstRun2) {
                    v.vibrate(1000);
                    firstRun2 = false;
                }
                progress.setBackgroundColor(Color.rgb(255, 51, 0));


            } else if (countDown <= TimerSetup.lagSlide) {
                //tvCountDown.setBackgroundColor(Color.YELLOW);
                if (firstRun1) {
                    v.vibrate(1000);
                    firstRun1 = false;
                }
                progress.setBackgroundColor(Color.YELLOW);     //for yellow color
            } else {
                //tvCountDown.setBackgroundColor(Color.GREEN);
                //progress.setBackgroundColor(Color.GREEN);
                progress.setBackgroundColor(Color.rgb(0, 153, 0));  //for green color


            }
            //Overall color
            if (countDownTotal <= TimerSetup.seriouslyLagSlide) {
                //tvTotalTime.setBackgroundColor(Color.RED);
            } else if (countDownTotal <= TimerSetup.lagSlide) {
                //tvTotalTime.setBackgroundColor(Color.YELLOW);
            } else {
                //tvTotalTime.setBackgroundColor(Color.GREEN);
            }

            if (countDown <= 0) {
                //Slide has finished increment currentSlideIndex for next slide

                if (currentSlideIndex + 1 >= SetupDataActivity.items.size()) {
                    //No more slides to display.. End the presentation

                    //To halt slide remaining time countdown when presentation ends
                    //timeRemaining = elapsedTime;
                    haltSlideCountdown = true;

                    tvSlideName.setText("");

                    //timerHandler.removeCallbacks(timerRunnable);
                    //finish();
                } else {
                    //If more slides available
                    //Set and start new slide
                    //totalTimeRemaining -= timeRemaining;

                    //isPaused = true;

                    //Animation for the red blinking background
                    progress.startAnimation(anim);

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
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //disable screen timeout
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        btnStart = (ImageButton) findViewById(R.id.btnStart);
        tvSlideName = (TextView) findViewById(R.id.tvSlideName);
        tvCountDown = (TextView) findViewById(R.id.tvCountDown);
        tvTotalTime = (TextView) findViewById(R.id.tvTotalTime);
        //btnExtra = (Button) findViewById(R.id.btnExtra);
        slidePresentation = (LinearLayout) findViewById(R.id.slidePresentation);
        layoutButtons = (LinearLayout) findViewById(R.id.layoutButtons);
        tvProgress = (TextView) findViewById(R.id.tvProgress);

        progress = (TextView) findViewById(R.id.progress);

        btnNext = (Button) findViewById(R.id.btnNext);
        btnPause = (Button) findViewById(R.id.btnPause);
        btnStop = (Button) findViewById(R.id.btnStop);

        //Setup progress
        progress.setVisibility(View.VISIBLE);
        progress.setHeight(1);
        //progress.setBackgroundColor(Color.GREEN);

        anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(500); //Manage the time of the blink
        anim.setStartOffset(20);
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(Animation.INFINITE);

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
                if (SetupDataActivity.items.size() > 0) {
                    btnStart.setVisibility(View.GONE);
                    slidePresentation.setVisibility(View.VISIBLE);
                    layoutButtons.setVisibility(View.VISIBLE);

                    //Initialize for first slide and start timerv2
                    setAndStartNewSlide();
                } else {
                    toast("Please setup data first");
                }
            }
        });

        /*
        btnExtra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Add extra time
                int extraTime = 10;

                timeRemaining += extraTime;
                totalTimeRemaining += extraTime;
            }
        });
        */

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
                    excessTime = countDown;

                    //totalTimeRemaining -= timeRemaining;
                    totalTimeRemaining += excessTime * 2;
                    //update
                    // timeRemaining+=excessTime;

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
        progress.clearAnimation();

        firstRun1 = true;
        firstRun2 = true;

        currentSlideIndex++;

        if (currentSlideIndex >= SetupDataActivity.items.size()) {
            return;
        }

        totalTimeRemaining -= timeRemaining;

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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_load_slides) {
            Intent intent = new Intent(StartActivity.this, SetupDataActivity.class);
            intent.putExtra("id", 1);
            startActivity(intent);
            return true;
        } else if (id == R.id.action_setup_slides) {
            Intent intent = new Intent(StartActivity.this, SetupDataActivity.class);
            intent.putExtra("id", 2);
            startActivity(intent);
            return true;
        } else if (id == R.id.action_setup_timer) {
            Intent intent = new Intent(StartActivity.this, SetupDataActivity.class);
            intent.putExtra("id", 3);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();

        timerHandler.removeCallbacks(timerRunnable);
    }

    public void toast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

}
