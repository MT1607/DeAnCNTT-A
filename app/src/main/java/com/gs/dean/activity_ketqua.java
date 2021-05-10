package com.gs.dean;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class activity_ketqua extends Activity {
    TextView KQ;
    Button trove;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ketqua);
        trove = (Button)findViewById(R.id.BtnBack);
        KQ = (TextView)findViewById(R.id.TxtKQ);

        Intent caller = getIntent();
        Bundle myPackage = caller.getBundleExtra("MyPackage");
        KQ.setText(myPackage.getInt("KQ")+"/"+myPackage.getInt("Socau"));
        trove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity_ketqua.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
