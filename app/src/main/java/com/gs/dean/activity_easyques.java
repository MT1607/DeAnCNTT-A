package com.gs.dean;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class activity_easyques extends Activity {
    ScrollView sv;
    RadioGroup rg;
    RadioButton rb;
    TextView textView;
    Button bt;
    ImageView img;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_easyques);
        bt = (Button)findViewById(R.id.Btntraloi);
    }
}
