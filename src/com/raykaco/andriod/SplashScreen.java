package com.raykaco.andriod;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import com.raykaco.android.customcontrol.CustomProgress;


public class SplashScreen extends Activity {

    @Override
    protected void onResume() {
        super.onResume();
        G.currentActivity = this;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        CustomProgress cp = (CustomProgress) findViewById(R.id.Custom1);
        cp.setColor("#ffffff");

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {

                finish();

                Intent intent = new Intent(G.currentActivity, ActivityRaykaCo.class);
                G.currentActivity.startActivity(intent);

            }

        }, 5000);
    }
}
