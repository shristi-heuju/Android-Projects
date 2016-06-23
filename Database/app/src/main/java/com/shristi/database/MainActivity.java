package com.shristi.database;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.text.style.TtsSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by shristi on 5/28/2016.
 */
public class MainActivity extends AppCompatActivity {

    private Database db=new Database(this);

    ArrayList<String> data = new ArrayList<String>();

    EditText etText;
    Button btnDone, btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        etText = (EditText) findViewById(R.id.etText);
        btnDone = (Button) findViewById(R.id.btnDone);
        btnSave = (Button) findViewById(R.id.btnSave);

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String string = etText.getText().toString();
                data.add(string);
                etText.setText("");
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.addAllItems(data);

                Intent intent=new Intent(MainActivity.this,ReturnActivity.class);
                startActivity(intent);
            }
        });

    }

    public void toast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}