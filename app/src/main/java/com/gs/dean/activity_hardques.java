package com.gs.dean;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class activity_hardques extends Activity {
    ScrollView sv;
    EditText editText;
    TextView textView;
    Button bt;
    ImageView img;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hardques);
        bt = (Button)findViewById(R.id.Btntraloi);
    }
}
