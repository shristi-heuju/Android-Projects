package com.shristi.timer;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddDataActivity extends AppCompatActivity {

    public final int ADD_DATA_REQUEST = 0;

    EditText etSlideName;
    EditText etTimeRequired;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_data);

        //Initialize Buttons and EditTexts for adding data
        Button btnNext = (Button) findViewById(R.id.btnNext);
        Button btnDone = (Button) findViewById(R.id.btnDone);

        etSlideName = (EditText) findViewById(R.id.etSlideName);
        etTimeRequired = (EditText) findViewById(R.id.etTimeRequired);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (checkField()) {
                    //New intent
                    Intent returnIntent = new Intent();
                    //attach the data to be returned to the intent
                    returnIntent.putExtra("slideName", etSlideName.getText().toString());
                    returnIntent.putExtra("timeRequired", etTimeRequired.getText().toString());
                    returnIntent.putExtra("enterNext", true);
                    //set result intent
                    setResult(Activity.RESULT_OK, returnIntent);
                    //terminate activity
                    finish();
                }
            }
        });

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkField()) {
                    //New intent
                    Intent returnIntent = new Intent();
                    //attach the data to be returned to the intent
                    returnIntent.putExtra("slideName", etSlideName.getText().toString());
                    returnIntent.putExtra("timeRequired", etTimeRequired.getText().toString());
                    returnIntent.putExtra("enterNext", false);
                    //set result intent
                    setResult(Activity.RESULT_OK, returnIntent);
                    //terminate activity
                    finish();
                }
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
}
