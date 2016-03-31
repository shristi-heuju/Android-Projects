package com.shristi.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView tvDisplay, tvDisplaySymbol;
    Button btnAc, btnDel, btnSeven, btnEight, btnNine, btnDiv, btnFour, btnFive, btnSix, btnMul, btnOne;
    Button btnTwo, btnThree, btnSub, btnZero, btnPoint, btnAdd, btnEqual;

    String value = "";
    float number1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvDisplay = (TextView) findViewById(R.id.tvDisplay);
        tvDisplaySymbol = (TextView) findViewById(R.id.tvDisplaySymbol);
        btnAc = (Button) findViewById(R.id.btnAc);
        btnDel = (Button) findViewById(R.id.btnDel);
        btnOne = (Button) findViewById(R.id.btnOne);
        btnTwo = (Button) findViewById(R.id.btnTwo);
        btnThree = (Button) findViewById(R.id.btnThree);
        btnFour = (Button) findViewById(R.id.btnFour);
        btnFive = (Button) findViewById(R.id.btnFive);
        btnSix = (Button) findViewById(R.id.btnSix);
        btnSeven = (Button) findViewById(R.id.btnSeven);
        btnEight = (Button) findViewById(R.id.btnEight);
        btnNine = (Button) findViewById(R.id.btnNine);
        btnDiv = (Button) findViewById(R.id.btnDiv);
        btnMul = (Button) findViewById(R.id.btnMul);
        btnSub = (Button) findViewById(R.id.btnSub);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnZero = (Button) findViewById(R.id.btnZero);
        btnPoint = (Button) findViewById(R.id.btnPoint);
        btnEqual = (Button) findViewById(R.id.btnEqual);

        btnAc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                value = "";
                tvDisplay.setText("0");
                tvDisplaySymbol.setText("");
            }
        });

        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (value.length() > 0) {
                    value = value.substring(0, value.length() - 1);
                }
                tvDisplay.setText(value);

            }
        });

        btnOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                value = value + "1";
                tvDisplay.setText(value);

            }
        });
        btnTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                value = value + "2";
                tvDisplay.setText(value);

            }
        });
        btnThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                value = value + "3";
                tvDisplay.setText(value);

            }
        });
        btnFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                value = value + "4";
                tvDisplay.setText(value);

            }
        });
        btnFive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                value = value + "5";
                tvDisplay.setText(value);

            }
        });
        btnSix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                value = value + "6";
                tvDisplay.setText(value);

            }
        });

        btnSeven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                value = value + "7";
                tvDisplay.setText(value);

            }
        });
        btnEight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                value = value + "8";
                tvDisplay.setText(value);

            }
        });
        btnNine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                value = value + "9";
                tvDisplay.setText(value);

            }
        });

        btnZero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                value = value + "0";
                tvDisplay.setText(value);

            }
        });
        btnPoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                value = value + ".";
                tvDisplay.setText(value);

            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (value.length() > 0) {
                    number1 = Float.parseFloat(value);
                    value = "";
                }
                tvDisplaySymbol.setText("+");

            }
        });

        btnSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (value.length() > 0) {
                    number1 = Float.parseFloat(value);
                    value = "";
                }
                tvDisplaySymbol.setText("-");

            }
        });
        btnMul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (value.length() > 0) {
                    number1 = Float.parseFloat(value);
                    value = "";
                }
                tvDisplaySymbol.setText("x");
            }
        });
        btnDiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (value.length() > 0) {
                    number1 = Float.parseFloat(value);
                    value = "";
                }
                tvDisplaySymbol.setText("/");
            }
        });
        btnEqual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                float result;

                String ch = tvDisplaySymbol.getText().toString();
                switch (ch) {
                    case "+":
                        result = number1 + Float.parseFloat(value);
                        tvDisplay.setText("" + result);
                        break;
                    case "-":
                        result = number1 - Float.parseFloat(value);
                        tvDisplay.setText("" + result);
                        break;
                    case "x":
                        result = number1 * Float.parseFloat(value);
                        tvDisplay.setText("" + result);
                        break;
                    case "/":
                        result = number1 / Float.parseFloat(value);
                        tvDisplay.setText("" + result);
                        break;
                }

                tvDisplaySymbol.setText("=");

            }
        });

    }
}
