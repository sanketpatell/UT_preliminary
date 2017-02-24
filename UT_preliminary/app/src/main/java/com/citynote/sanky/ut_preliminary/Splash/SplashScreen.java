package com.citynote.sanky.ut_preliminary.Splash;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.citynote.sanky.ut_preliminary.Activity.MainActivity;
import com.citynote.sanky.ut_preliminary.R;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Thread thread=new Thread()
        {
            public  void run()
            {
                try {
                    sleep(5000);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                finally {

                    Intent intent=new Intent(SplashScreen.this,MainActivity.class);
                    startActivity(intent);
                }

            }
        };
        thread.start();
    }
    @Override
    protected void onPause()
    {
        super.onPause();
        finish();
    }
}
/*
import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;

import com.citynote.sanky.ut_preliminary.Activity.Login;
import com.citynote.sanky.ut_preliminary.Activity.MainActivity;
import com.citynote.sanky.ut_preliminary.R;

import java.util.logging.Handler;

public class SplashScreen extends Activity {
    // Splash screen timer
    private static int SPLASH_TIME_OUT = 3000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

// METHOD 1

        */
/****** Create Thread that will sleep for 5 seconds *************//*

        Thread background = new Thread() {
            public void run() {

                try {
                    // Thread will sleep for 5 seconds
                    sleep(5*1000);

                    // After 5 seconds redirect to another intent
                    Intent i=new Intent(getBaseContext(),MainActivity.class);
                    startActivity(i);

                    //Remove activity
                    finish();

                } catch (Exception e) {

                }
            }
        };

        // start thread
        background.start();

//METHOD 2

        */
/*
        new Handler().postDelayed(new Runnable() {

            // Using handler with postDelayed called runnable run method

            @Override
            public void run() {
                Intent i = new Intent(MainSplashScreen.this, FirstScreen.class);
                startActivity(i);

                // close this activity
                finish();
            }
        }, 5*1000); // wait for 5 seconds
        *//*

    }

    @Override
    protected void onDestroy() {

        super.onDestroy();

    }
}*/
