package com.raykaco.andriod;

import io.vov.vitamio.LibsChecker;
import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import com.raykaco.andriod.internet.CheckInternet;
import com.raykaco.andriod.internet.Connectivity;


public class ShowVideo extends Enhance {

    private VideoView mVideoView;
    String            Url;

    LinearLayout      layLoading;


    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_video);

        LinearLayout LayRefresh = (LinearLayout) findViewById(R.id.lay_check_refresh);
        LinearLayout LayMobile = (LinearLayout) findViewById(R.id.lay_check_mobile);
        LinearLayout LayWifi = (LinearLayout) findViewById(R.id.lay_check_wifi);

        layLoading = (LinearLayout) findViewById(R.id.lay_loading);
        final CheckInternet CheckInternet = new CheckInternet();
        final Connectivity Check = new Connectivity();
        if (Check.isConnected(G.context)) {

            conected();
            layLoading.setVisibility(View.VISIBLE);
            layMain.setVisibility(View.GONE);
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {

                @Override
                public void run() {
                    layMain.setVisibility(View.VISIBLE);
                    layLoading.setVisibility(View.GONE);
                }
            }, 4000);

            if ( !LibsChecker.checkVitamioLibs(this))
                return;

            Bundle extras = getIntent().getExtras();
            Url = "";
            if (extras != null) {
                Url = extras.getString("URL");
            }

            mVideoView = (VideoView) findViewById(R.id.buffer);
            mVideoView.setVideoPath(Url);
            mVideoView.setMediaController(new MediaController(this));
            mVideoView.requestFocus();
            mVideoView.setBufferSize(300);
            mVideoView.setVideoQuality(MediaPlayer.VIDEOQUALITY_HIGH);
            mVideoView.getHolder().setFormat(PixelFormat.RGBX_8888);
            //  mVideoView.setMediaBufferingIndicator(layLoading);
            mVideoView.start();
            mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {

                    mediaPlayer.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {

                        @Override
                        public void onBufferingUpdate(MediaPlayer mp, int percent) {
                            Log.i("DATA", "jaber");
                            layMain.setVisibility(View.VISIBLE);
                            layLoading.setVisibility(View.GONE);
                        }
                    });
                    Log.i("DATA", "jaber");
                    layMain.setVisibility(View.VISIBLE);
                    layLoading.setVisibility(View.GONE);
                    mediaPlayer.setPlaybackSpeed(1.0f);
                }
            });

        } else {
            disconected();

        }
        LayWifi.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                //Toast.makeText(G.context, "text", Toast.LENGTH_SHORT).show();
                CheckInternet.goToSettingWiFiNet();
            }
        });
        LayMobile.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                CheckInternet.goToSettingMobileNet();
            }
        });

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}