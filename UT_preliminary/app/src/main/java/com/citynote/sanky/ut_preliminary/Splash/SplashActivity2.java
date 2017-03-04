package com.citynote.sanky.ut_preliminary.Splash;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.citynote.sanky.ut_preliminary.Activity.MainActivity;
import com.citynote.sanky.ut_preliminary.R;

public class SplashActivity2 extends Activity implements Runnable {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash2);

        findViewById(android.R.id.content).postDelayed(this, 2000);
    }

    @Override
    public void run() {
        startActivity(new Intent(SplashActivity2.this, MainActivity.class));
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
