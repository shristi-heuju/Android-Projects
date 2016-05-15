package com.shristi.timerv4;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class SetupSlideDialogFragment extends DialogFragment {

    Button cancel, ok;
    Communicator communicator;

    EditText etSlideNumber;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        communicator = (Communicator) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        setCancelable(false);
        View view = inflater.inflate(R.layout.setupslide_dialog, null);

        getDialog().setTitle("Setup slide data");

        cancel = (Button) view.findViewById(R.id.btnCancel);
        ok = (Button) view.findViewById(R.id.btnOk);

        etSlideNumber = (EditText) view.findViewById(R.id.etSlideNumber);

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
                //communicator.onDialogMessage("OK");
                if (etSlideNumber.length() > 0) {
                    communicator.onDialogMessage(etSlideNumber.getText().toString());
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
