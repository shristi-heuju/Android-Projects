package com.shristi.timer;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;


public class SetupSlideDailogFragment extends DialogFragment {

    Button cancel,ok;
    Communicator communicator;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        communicator= (Communicator) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        setCancelable(false);
        View view=inflater.inflate(R.layout.setupslide_dialog,null);
        cancel=(Button) view.findViewById(R.id.btnCancel);
        ok=(Button) view.findViewById(R.id.btnOk);


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                communicator.onDialogMessage("Cancel");
                dismiss();

            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                communicator.onDialogMessage("OK");
                dismiss();
            }

        });
        return view;
    }
    interface Communicator{
        public void onDialogMessage(String message);
    }
}
