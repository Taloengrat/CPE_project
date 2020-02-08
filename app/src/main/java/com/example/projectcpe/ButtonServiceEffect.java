package com.example.projectcpe;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.IBinder;
import android.view.View;

import androidx.annotation.Nullable;

public class ButtonServiceEffect {

    MediaPlayer player;
    Activity activity;

    public ButtonServiceEffect(Activity activity){
        this.activity = activity;
    }




    public void startEffect(){
        player = MediaPlayer.create(this.activity,R.raw.button1);


        Handler handler = new Handler();

        player.start();

        Runnable Delay = new Runnable() {
            @Override
            public void run() {

                player.stop();
            }
        };

        handler.postDelayed(Delay, 1000);

    }


    public void stopEffect(){

        player.stop();
    }



}
