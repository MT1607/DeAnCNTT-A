package com.gs.dean;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.gs.dean.model.Question;
import com.sasank.roundedhorizontalprogress.RoundedHorizontalProgressBar;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;

public class activity_hardques extends Activity {
    ScrollView sv;
    EditText editText;
    TextView exit ;
    Button bt;
    ImageView img,back;
    Question question;
    int total=0;
    int kq = 0,wrong = 0;
    int quesnumber = 1;
    int a = 0;
    int timeValue = 120;
    ArrayList<Integer> number = new ArrayList<>();
    DatabaseReference databaseReference;
    String TAG="MainActivity";
    AudioManager audioManager;
    MediaPlayer mediaPlayer;
    CountDownTimer countDownTimer;
    RoundedHorizontalProgressBar roundedHorizontalProgressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hardques);

        bt = (Button)findViewById(R.id.Btntraloi);
        editText = (EditText)findViewById(R.id.edit_answer);
        img = (ImageView)findViewById(R.id.ImgQuestion);
        back = findViewById(R.id.ic_back);
        exit = findViewById(R.id.ic_exit);

        roundedHorizontalProgressBar = findViewById(R.id.progress_bar_1);
        CountDownTimer countDownTimer = new CountDownTimer(120000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeValue = timeValue - 1;
                roundedHorizontalProgressBar.setProgress(timeValue);
            }

            @Override
            public void onFinish() {
                Dialog dialog = new Dialog(activity_hardques.this,R.style.Dialog);
                dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
                dialog.setContentView(R.layout.time_out_dialog);
                dialog.show();
            }
        }.start();

        for (int i = 1;i < 27;i++){
            number.add(i);
        }
        Collections.shuffle(number);

        hardques();

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (editText.getText().toString().equalsIgnoreCase(question.getTrueAnswer())){
                    kq = kq + 1;
                    editText.getText().clear();
                }else {
                    editText.getText().clear();
                }

                if (quesnumber >= 10){
                    Intent intent = new Intent(activity_hardques.this,activity_ketqua.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("KQ",kq);
                    bundle.putInt("Socau",quesnumber);
                    intent.putExtra("MyPackage",bundle);
                    startActivity(intent);
                }else{
                    quesnumber++;
                    hardques();
                }
            }

        });

    }
    void hardques(){
        total=number.get(a);
        a++;
        databaseReference = FirebaseDatabase.getInstance().getReference().child("question_database").child("question_hard").child(String.valueOf(total));
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                question = snapshot.getValue(Question.class);
                Picasso.get().load(question.url).into(img);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG,error.getMessage());
            }
        });
    }

}
