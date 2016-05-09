package com.customtimepicker.shristi.customtimepicker;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class TimePickerActivity extends AppCompatActivity implements TimePickerDialogFragment.Communicator {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_picker);

        Button btnStart = (Button) findViewById(R.id.btnStart);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.app.FragmentManager manager = getFragmentManager();
                TimePickerDialogFragment dialog = new TimePickerDialogFragment();
                dialog.show(manager, "TimePicker");

            }
        });
    }

    @Override
    public void onDialogMessage(String message) {

        TextView tvResult = (TextView) findViewById(R.id.tvResult);
        int time = Integer.parseInt(message);

        int min = time / 60;
        int sec = time % 60;

        tvResult.setText(String.format("%02d:%02d", min, sec));
    }

    public void toast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

}
