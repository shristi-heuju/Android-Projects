package com.shristi.timerv5;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.Toast;

public class StartActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    AudioManager am;
    static int ringerMode = AudioManager.RINGER_MODE_NORMAL;

    boolean doubleBackToExitPressedOnce = false;

    ImageButton btnStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //Drawer
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        //Set device on vibration mode
        am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        ringerMode = am.getRingerMode();

        am.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);

        //disable screen timeout
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);


        btnStart = (ImageButton) findViewById(R.id.btnStart);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SetupDataActivity.items.size() > 0) {

                    Intent intent = new Intent(StartActivity.this, PresentationActivity.class);
                    startActivity(intent);
                    //btnStart.setVisibility(View.GONE);
                    //slidePresentation.setVisibility(View.VISIBLE);
                    //layoutButtons.setVisibility(View.VISIBLE);

                    //Initialize for first slide and start timerv3
                    //setAndStartNewSlide();
                } else {
                    toast("Please setup data first");
                }
            }
        });
    }

    public void toast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    /*
        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.menu_main, menu);
            return true;
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            // Handle action bar item clicks here. The action bar will
            // automatically handle clicks on the Home/Up button, so long
            // as you specify a parent activity in AndroidManifest.xml.
            int id = item.getItemId();

            //noinspection SimplifiableIfStatement
            if (id == R.id.action_load_slides) {
                Intent intent = new Intent(StartActivity.this, SetupDataActivity.class);
                intent.putExtra("id", 1);
                startActivity(intent);
                return true;
            } else if (id == R.id.action_setup_slides) {
                Intent intent = new Intent(StartActivity.this, SetupDataActivity.class);
                intent.putExtra("id", 2);
                startActivity(intent);
                return true;
            } else if (id == R.id.action_setup_timer) {
                Intent intent = new Intent(StartActivity.this, SetupDataActivity.class);
                intent.putExtra("id", 3);
                startActivity(intent);
                return true;
            }

            return super.onOptionsItemSelected(item);
        }
    */

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            // super.onBackPressed();
        }

        if (doubleBackToExitPressedOnce) {
            //super.onBackPressed();
            int ringerMode = AudioManager.RINGER_MODE_NORMAL;
            am.setRingerMode(ringerMode);

            Intent intent = getIntent();
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            //startActivity(intent);
            //finish();

            System.exit(0);
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please press back again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);

    }

}
