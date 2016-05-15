package com.shristi.timerv5;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class TimerSetup extends AppCompatActivity {

    EditText etSeriouslyLagSlide, etLagSlide;
    //EditText etSeriouslyLagOverall, etLagOverall;

    Button btnBack;

    public static int seriouslyLagSlide = 20, lagSlide = 40;
    public static int seriouslyLagOverall = 2, lagOverall = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer_setup);

        btnBack = (Button) findViewById(R.id.btnBack);

        etSeriouslyLagSlide = (EditText) findViewById(R.id.etSeriouslyLagSlide);
        etLagSlide = (EditText) findViewById(R.id.etLagSlide);

        //etSeriouslyLagOverall = (EditText) findViewById(R.id.etSeriouslyLagOverall);
        //etLagOverall = (EditText) findViewById(R.id.etLagOverall);

        //Set data of edittext
        etSeriouslyLagSlide.setText("" + seriouslyLagSlide);
        etLagSlide.setText("" + lagSlide);

        //etSeriouslyLagOverall.setText("" + seriouslyLagOverall);
        //etLagOverall.setText("" + lagOverall);

        //on back pressed set the new data entered
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //check for empty fields and set data
                /*&& etSeriouslyLagOverall.length() > 0 && etLagOverall.length() > 0)*/
                if (etSeriouslyLagSlide.length() > 0 && etLagSlide.length() > 0) {
                    seriouslyLagSlide = Integer.parseInt(etSeriouslyLagSlide.getText().toString());
                    lagSlide = Integer.parseInt(etLagSlide.getText().toString());

                    //seriouslyLagOverall = Integer.parseInt(etSeriouslyLagOverall.getText().toString());
                    //lagOverall = Integer.parseInt(etLagOverall.getText().toString());

                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Error! Empty Fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
