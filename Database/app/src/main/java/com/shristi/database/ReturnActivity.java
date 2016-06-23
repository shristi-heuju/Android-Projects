package com.shristi.database;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

public class ReturnActivity extends AppCompatActivity {

    Database db = new Database(this);

    TextView tvText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_return);

        tvText = (TextView) findViewById(R.id.tvText);
        String text="";

        ArrayList<String> arrayList = db.getAllItems();
        for (int i = 0; i < arrayList.size(); i++) {
            text=text+arrayList.get(i)+"\n";
        }
        tvText.setText(text);
    }
}
