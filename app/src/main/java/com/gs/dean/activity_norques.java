package com.gs.dean;

import android.app.Activity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.lang.annotation.Annotation;

public class activity_norques extends Activity {
    ScrollView sv;
    RadioGroup rg;
    RadioButton rb;
    TextView textView;
    Button bt;
    ImageView img;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_norques);
        bt = (Button)findViewById(R.id.Btntraloi);

        Animation ani = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.animation);

       ImageView img = (ImageView)findViewById(R.id.ImgQuestion);
       img.startAnimation(ani);
    }
}
