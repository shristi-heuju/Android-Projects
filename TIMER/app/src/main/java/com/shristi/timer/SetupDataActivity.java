package com.shristi.timer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class SetupDataActivity extends AppCompatActivity {

    //Request code for startActivityForResult()
    public final int ADD_DATA_REQUEST = 0;
    public static int totalTime = 0;

    //Declare a slideList
    ListView slideList;
    TextView tvTotalTime;

    public static int slideCount = 1;

    //Declare EditText
    //EditText etTotalTime;

    //Declare and initialized ArrayList and ArrayAdapter required for displaying ListView item
    //items holds the data to be displayed in the list
    public static ArrayList<String> items = new ArrayList<String>();
    public static ArrayList<Integer> itemsTimeRequired = new ArrayList<>();
    public static ArrayList<String> itemsSlideName = new ArrayList<>();
    //adapter
    static ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_data);

        /**For quick testing**/
        //un-comment : don't need to add data, click done
        /*
        itemsTimeRequired.add(5);
        itemsTimeRequired.add(10);
        itemsTimeRequired.add(15);

        itemsSlideName.add("1st");
        itemsSlideName.add("2nd");
        itemsSlideName.add("3rd");

        items.add("1 - 5sec");
        items.add("2 - 10sec");
        items.add("3 - 15sec");

        totalTime = 30;
        /****/


        //Initialize Widgets
        slideList = (ListView) findViewById(R.id.slideList);
        tvTotalTime = (TextView) findViewById(R.id.tvTotalTime);

        Button btnDone = (Button) findViewById(R.id.btnDone);
        Button btnSetupData = (Button) findViewById(R.id.btnSetupData);

        //Create adapter with passing arraylist items
        adapter = new ArrayAdapter<String>(this, R.layout.list_item, items);
        //Set Adapter
        slideList.setAdapter(adapter);

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (items.size() > 0) {
                    Intent intent = new Intent(SetupDataActivity.this, StartActivity.class);
                    startActivity(intent);
                } else {
                    toast("No Slides to Present");
                }
            }
        });
        //Click Listener for button
        btnSetupData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Create intent to open AddDataActivity
                Intent intent = new Intent(SetupDataActivity.this, PreSlideSetup.class);
                //need to return with result from AddDataActivity
                startActivityForResult(intent, ADD_DATA_REQUEST);

            }
        });
    }


    //Automatically called (with passed data) when activity startActivityForResult() is terminated
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // Check which request we're responding to
        if (requestCode == ADD_DATA_REQUEST) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                //Extract the data
                String slideName = data.getStringExtra("slideName");
                String timeRequired = data.getStringExtra("timeRequired");
                boolean enterNext = data.getBooleanExtra("enterNext", false);

                //Add the extracted data to items array, to make ready to show in ListView
                items.add(slideName + " : " + timeRequired + " sec");

                //refresh adapter to display the changes
                adapter.notifyDataSetChanged();

                //Save slideName and timeRequired of each items in itemsTimeRemaining array
                //to access from StartActivity.java
                itemsSlideName.add(slideName);
                itemsTimeRequired.add(Integer.parseInt(timeRequired));

                totalTime += Integer.parseInt(timeRequired);

                tvTotalTime.setText("Total Time: " + totalTime);

                //If NextButton is clicked in AddDataActivity then immediately open the activity again to enter data
                if (enterNext) {
                    //Create intent to open AddDataActivity
                    Intent intent = new Intent(SetupDataActivity.this, PreSlideSetup.class);
                    //need to return with result from AddDataActivity
                    startActivityForResult(intent, ADD_DATA_REQUEST);
                }
            }
        }
    }

    public void toast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

}
