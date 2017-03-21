package com.citynote.sanky.ut_preliminary.Splash;


/*
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MotionEvent;
import android.widget.ImageView;

import com.citynote.sanky.ut_preliminary.Activity.Login;
import com.citynote.sanky.ut_preliminary.Activity.MainActivity;
import com.citynote.sanky.ut_preliminary.Activity.Signup;
import com.citynote.sanky.ut_preliminary.R;

public class SplashActivity extends Activity {
    //SharedPreferences sharedpreferences = getSharedPreferences(Login.MyPREFERENCES, Context.MODE_PRIVATE);



   SharedPreferences sharedpreferences;

    SharedPreferences.Editor spt;

//   String Status = sharedpreferences.getString("status", null);

     //The thread to process splash screen events

    private Thread mSplashThread;

    */
/** Called when the activity is first created. *//*

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Splash screen view
        setContentView(R.layout.activity_splash);
        sharedpreferences = getSharedPreferences("MyPREFERENCES", 0);

        spt = sharedpreferences.edit();




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
                }finally {
                    if (sharedpreferences.getString("success", "-1").equals("succesfully"))
                    {
                        Intent i = new Intent(SplashActivity.this,MainActivity.class);
                        startActivity(i);
                    }
                    else{

                        // Run next activity
                        Intent intent = new Intent(SplashActivity.this, Login.class);
                        startActivity(intent);
                        //stop(); \
                        finish();
                    }

                }


         }
        };

        mSplashThread.start();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        super.onCreateOptionsMenu(menu);
        return false;
    }

    */
/**
     * Processes splash screen touch events
     *//*

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
*/



import android.app.Activity;
        import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.citynote.sanky.ut_preliminary.Activity.Login;
import com.citynote.sanky.ut_preliminary.Activity.MainActivity;
        import com.citynote.sanky.ut_preliminary.R;


public class SplashActivity extends Activity implements Runnable {
    SharedPreferences sharedpreferences;

    SharedPreferences.Editor spt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);
        sharedpreferences = getSharedPreferences("uohmac", 0);
        spt = sharedpreferences.edit();


        findViewById(android.R.id.content).postDelayed(this, 1000);
    }

    @Override
    public void run() {

            if (sharedpreferences.getString("success", "-1").equals("succesfully"))
            {
                Intent i = new Intent(SplashActivity.this,MainActivity.class);
                startActivity(i);
                finish();
            }
            else{

                // Run next activity
                Intent intent = new Intent(SplashActivity.this, Login.class);
                startActivity(intent);
                //stop(); \
                finish();
            }




/*
        startActivity(new Intent(SplashActivity.this, MainActivity.class));
        finish();*/
    }

}
