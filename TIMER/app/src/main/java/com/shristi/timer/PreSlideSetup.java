package com.shristi.timer;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class PreSlideSetup extends AppCompatActivity {

    public final int ADD_DATA_REQUEST = 0;

    EditText etSlideName, etTimeRequired, etSlideNumber;
    Button btnNext, btnDone, btnProceed;
    LinearLayout layoutPreSlideSetup, layoutEnterData;

    TextView tvSlideIndicator;

    static int numberOfSlides = 1;
    //static int slideCount;

    static boolean limit = true;
    static boolean addMore = false;

    AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_slide_setup);

        //Initialize Buttons and EditTexts for adding data
        btnNext = (Button) findViewById(R.id.btnNext);
        btnDone = (Button) findViewById(R.id.btnDone);
        btnProceed = (Button) findViewById(R.id.btnProceed);

        layoutPreSlideSetup = (LinearLayout) findViewById(R.id.layoutPreSLideSetup);
        layoutEnterData = (LinearLayout) findViewById(R.id.layoutEnterData);

        etSlideName = (EditText) findViewById(R.id.etSlideName);
        etTimeRequired = (EditText) findViewById(R.id.etTimeRequired);
        etSlideNumber = (EditText) findViewById(R.id.etSlideNumber);

        tvSlideIndicator = (TextView) findViewById(R.id.tvSlideIndicator);

        layoutPreSlideSetup.setVisibility(View.VISIBLE);
        layoutEnterData.setVisibility(View.GONE);

        log(SetupDataActivity.slideCount + " : " + numberOfSlides);


        if (limit) {
            btnDone.setVisibility(View.INVISIBLE);
            btnNext.setVisibility(View.VISIBLE);
        } else {
            btnDone.setVisibility(View.VISIBLE);
            btnNext.setVisibility(View.VISIBLE);
        }


        AlertDialog.Builder addMoreDialog = new AlertDialog.Builder(this);
        addMoreDialog.setMessage("Do you want to add more slides?");
        addMoreDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                addMore = true;
            }
        });
        addMoreDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                addMore = false;
                finish();
            }
        });
        alertDialog = addMoreDialog.create();


        if (SetupDataActivity.slideCount > numberOfSlides) {

            limit = false;
            //show dialog
            // if yes addMore = true
            // if no nothing
            alertDialog.show();

        }


        if (numberOfSlides > 1) {
            layoutPreSlideSetup.setVisibility(View.GONE);
            layoutEnterData.setVisibility(View.VISIBLE);

            tvSlideIndicator.setText(SetupDataActivity.slideCount + "/" + numberOfSlides);
        }

        btnProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etSlideNumber.length() > 0) {
                    numberOfSlides = Integer.valueOf(etSlideNumber.getText().toString());
                    if (numberOfSlides > 0) {
                        toast("Enter the slides data: ");
                        layoutPreSlideSetup.setVisibility(View.GONE);
                        layoutEnterData.setVisibility(View.VISIBLE);

                        tvSlideIndicator.setText(SetupDataActivity.slideCount + "/" + numberOfSlides);

                    }
                } else {
                    toast("Please enter number of slides required !");
                }
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (SetupDataActivity.slideCount > numberOfSlides) {
                    //No need to enter more data
                    //ask if add more data
                    if (addMore) {
                        limit = false;
                    }


                }
                if (checkField()) {
                    //New intent
                    Intent returnIntent = new Intent();
                    //attach the data to be returned to the intent
                    returnIntent.putExtra("slideName", etSlideName.getText().toString());
                    returnIntent.putExtra("timeRequired", etTimeRequired.getText().toString());

                    if (SetupDataActivity.slideCount <= numberOfSlides || !limit) {
                        returnIntent.putExtra("enterNext", true);
                    }

                    //set result intent
                    setResult(Activity.RESULT_OK, returnIntent);
                    //terminate activity

                    SetupDataActivity.slideCount++;

                    finish();
                }
            }
        });

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SetupDataActivity.slideCount = 1;
                numberOfSlides = 0;

                finish();

            }
        });
    }

    public boolean checkField() {
        if (etSlideName.length() > 0 && etTimeRequired.length() > 0) {
            return true;
        }
        toast("Error: Empty Fields !! ");
        return false;
    }

    public void toast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    public void log(String msg) {
        Log.d("PreSlideSetup", msg);
    }
}
