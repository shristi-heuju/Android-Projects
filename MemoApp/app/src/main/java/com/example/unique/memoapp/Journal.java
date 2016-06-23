package com.example.unique.memoapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class Journal extends AppCompatActivity {
    TextView tvDisplayDate;
    Database db=new Database(this);
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.journal);


        //getting current date
        tvDisplayDate = (TextView) findViewById(R.id.dateView);
        final Calendar c = Calendar.getInstance();
        int yy = c.get(Calendar.YEAR);
        int mm = c.get(Calendar.MONTH);
        int dd = c.get(Calendar.DAY_OF_MONTH);

        // set current date into textview
        tvDisplayDate.setText(new StringBuilder()
                .append(yy).append(" ").append("/").append(mm + 1).append("/")
                .append(dd));


    }


    public void saveData(View view){
        EditText editTitles = (EditText) findViewById(R.id.editTitle);
        EditText editNotes = (EditText) findViewById(R.id.editNote);
        String data1 = editTitles.getText().toString();
        String data2 = editNotes.getText().toString();

        Toast.makeText(this,"Saved ",Toast.LENGTH_SHORT).show();

        editTitles.setText("");
        editNotes.setText("");

      db.insertData(data1,data2);



        if ( editTitles.length() != 0 && editNotes.length() != 0 ) {

            Intent newIntent = getIntent();
            newIntent.putExtra("title", (Parcelable) editTitles);
            newIntent.putExtra("note", (Parcelable) editNotes);

            this.setResult(RESULT_OK, newIntent);

            finish();
        }
    }
}


