package com.shristi.databasetest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Database db = new Database(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void saveButton(View view) {
        EditText eText1 = (EditText) findViewById(R.id.etText1);
        EditText eText2 = (EditText) findViewById(R.id.etText2);

        String data1 = eText1.getText().toString();
        String data2 = eText2.getText().toString();

        Toast.makeText(this, data1 + " " + data2, Toast.LENGTH_SHORT).show();

        eText1.setText("");
        eText2.setText("");

        db.saveData(data1, data2);
    }

    public void retrieveButton(View view) {

        String dataName = db.retrieveData();
        EditText eText1 = (EditText) findViewById(R.id.etText1);

        eText1.setText(dataName);


    }
}
