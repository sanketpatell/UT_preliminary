package com.citynote.sanky.ut_preliminary.Splash;


import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MotionEvent;
import android.widget.ImageView;

import com.citynote.sanky.ut_preliminary.Activity.MainActivity;
import com.citynote.sanky.ut_preliminary.R;

public class SplashActivity extends Activity {

    /**
     * The thread to process splash screen events
     */
    private Thread mSplashThread;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Splash screen view
        setContentView(R.layout.activity_splash);

        // Start animating the image
        final ImageView splashImageView = (ImageView) findViewById(R.id.SplashImageView);
        splashImageView.setBackgroundResource(R.drawable.flag);
        final AnimationDrawable frameAnimation = (AnimationDrawable)splashImageView.getBackground();
        splashImageView.post(new Runnable(){
            @Override
            public void run() {
                frameAnimation.start();
            }
        });


        final SplashActivity sPlashScreen = this;

        // The thread to wait for splash screen events
        mSplashThread =  new Thread(){
            @Override
            public void run(){
                try {
                    synchronized(this){
                        // Wait given period of time or exit on touch
                        wait(5000);
                    }
                }
                catch(InterruptedException ex){
                }



                // Run next activity
                Intent intent = new Intent();
                intent.setClass(sPlashScreen, MainActivity.class);
                startActivity(intent);
                //stop(); \
                finish();
            }
        };

        mSplashThread.start();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        super.onCreateOptionsMenu(menu);
        return false;
    }

    /**
     * Processes splash screen touch events
     */
    @Override
    public boolean onTouchEvent(MotionEvent evt)
    {
        if(evt.getAction() == MotionEvent.ACTION_DOWN)
        {
            synchronized(mSplashThread){
                mSplashThread.notifyAll();
            }
        }
        return true;
    }


}


/*

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.citynote.sanky.ut_preliminary.Activity.MainActivity;
import com.citynote.sanky.ut_preliminary.R;


public class SplashActivity extends Activity implements Runnable {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);

        findViewById(android.R.id.content).postDelayed(this, 1000);
    }

    @Override
    public void run() {




        startActivity(new Intent(SplashActivity.this, SplashActivity2.class));
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onStop() {
        super.onStop();

    }

}
*/
