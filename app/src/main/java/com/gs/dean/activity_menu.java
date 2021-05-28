package com.gs.dean;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class activity_menu extends Activity {
    SoundPool soundPool;
    AudioManager audioManager;
    MediaPlayer mediaPlayer;
    Button B1,B2,B3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        B1 = (Button) findViewById(R.id.Btneasy);
        B2 = (Button) findViewById(R.id.Btnmedium);
        B3 = (Button) findViewById(R.id.Btnhard);


        B1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity_menu.this, activity_easyques.class);
                startActivity(intent);


            }
        });

        B2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity_menu.this, activity_norques.class);
                startActivity(intent);
            }
        });

        B3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity_menu.this, activity_hardques.class);
                startActivity(intent);
            }
        });
    }

}
