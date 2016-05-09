package com.shristi.timer;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.Toast;


public class TimePickerDialogFragment extends DialogFragment {

    Communicator communicator;
    Button cancel, ok;

    NumberPicker npMin, npSec;
    int min = 0, sec = 0;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        communicator = (Communicator) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        setCancelable(false);
        View view = inflater.inflate(R.layout.timepicker_dialog, null);

        getDialog().setTitle("Pick Time");

        cancel = (Button) view.findViewById(R.id.btnCancel);
        ok = (Button) view.findViewById(R.id.btnOk);

        /**
         * Setting up Number Pickers
         * */
        npMin = (NumberPicker) view.findViewById(R.id.npMin);
        npSec = (NumberPicker) view.findViewById(R.id.npSec);

        //Storing 00 - 59 in array
        String[] values = new String[60];
        for (int i = 0; i < values.length; i++) {
            String text = (i < 10 ? "0" : "") + i;
            values[i] = text;
        }

        npMin.setMaxValue(59);
        npMin.setMinValue(0);
        npMin.setDisplayedValues(values);

        npSec.setMaxValue(59);
        npSec.setMinValue(0);
        npSec.setDisplayedValues(values);

        npMin.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                min = newVal;
            }
        });
        npSec.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                sec = newVal;
            }
        });


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //communicator.onDialogMessage("Cancel");
                dismiss();
            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //int min = tpTimeRequired.getCurrentHour();
                //int sec = tpTimeRequired.getCurrentMinute();

                //int min = Integer.parseInt(npMin.getDisplayedValues().toString());
                //int sec = Integer.parseInt(npSec.getDisplayedValues().toString());

                int time = min * 60 + sec;   //this is in seconds

                if (time > 0) {
                    communicator.onDialogMessage(String.valueOf(time));
                    dismiss();
                } else {
                    toast("Error! Empty Field");
                }
            }
        });
        return view;
    }

    public void toast(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    interface Communicator {
        public void onDialogMessage(String message);
    }
}
