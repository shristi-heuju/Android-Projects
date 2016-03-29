package com.shristi.interestcalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView tvInterest;
    Button btnCalculate;
    EditText etPrincipal, etTime, etRate;

    float interest;
    float principal;
    float time;
    float rate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvInterest = (TextView) findViewById(R.id.tvInterest);
        btnCalculate = (Button) findViewById(R.id.btnCalculate);
        etPrincipal = (EditText) findViewById(R.id.etPrincipal);
        etTime = (EditText) findViewById(R.id.etTime);
        etRate = (EditText) findViewById(R.id.etRate);

        btnCalculate.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                principal = Float.parseFloat(etPrincipal.getText().toString());
                time = Float.parseFloat(etTime.getText().toString());
                rate = Float.parseFloat(etRate.getText().toString());

                interest = (principal*time*rate)/100;

                tvInterest.setText(""+interest);

            }
        });
    }
}
