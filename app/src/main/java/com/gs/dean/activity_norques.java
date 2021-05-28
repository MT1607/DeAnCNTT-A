package com.gs.dean;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collections;

public class activity_norques extends Activity {
    ScrollView sv;
    RadioGroup rg;
    RadioButton A,B,C,D;
    TextView exit;
    Button bt;
    int total = 0;
    int quesnumber = 1;
    int a = 0;
    int kq=0,wrong = 0;
    ImageView img,back;
    Question question;
    ArrayList<Integer> number = new ArrayList<>();
    DatabaseReference databaseReference;
    String TAG="MainActivity";
    AudioManager audioManager;
    MediaPlayer mediaPlayer;
    CountDownTimer countDownTimer;
    int timeValue = 90;
    RoundedHorizontalProgressBar roundedHorizontalProgressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_norques);
        rg = (RadioGroup)findViewById(R.id.Rdgroup);
        bt = (Button)findViewById(R.id.Btntraloi);
        A = (RadioButton)findViewById(R.id.RbtA);
        B = (RadioButton)findViewById(R.id.RbtB);
        C = (RadioButton)findViewById(R.id.RbtC);
        D = (RadioButton)findViewById(R.id.RbtD);
        img = (ImageView)findViewById(R.id.ImgQuestion);
        back = findViewById(R.id.ic_back);
        exit = findViewById(R.id.ic_exit);

        roundedHorizontalProgressBar = findViewById(R.id.progress_bar_1);
        CountDownTimer countDownTimer = new CountDownTimer(90000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeValue = timeValue - 1;
                roundedHorizontalProgressBar.setProgress(timeValue);
            }

            @Override
            public void onFinish() {
                Dialog dialog = new Dialog(activity_norques.this,R.style.Dialog);
                dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
                dialog.setContentView(R.layout.time_out_dialog);
                dialog.show();
            }
        }.start();

        for (int i = 1; i < 26;i++){
            number.add(i);
        }
        Collections.shuffle(number);

        normalquestion();

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int idCheck = rg.getCheckedRadioButtonId();
                switch (idCheck){
                    case R.id.RbtA:
                        if(question.getTrueAnswer().compareTo(question.getAnswerA())==0) {

                            kq = kq+1;
                        }else{
                            wrong = wrong+1;
                        }
                        resetColor();
                        break;
                    case R.id.RbtB:
                        if(question.getTrueAnswer().compareTo(question.getAnswerB())==0) {

                            kq = kq+1;
                        }else{
                            wrong = wrong+1;
                        }
                        resetColor();
                        break;
                    case R.id.RbtC:
                        if(question.getTrueAnswer().compareTo(question.getAnswerC())==0) {

                            kq = kq+1;
                        }else{
                            wrong = wrong+1;
                        }
                        resetColor();
                        break;
                    case R.id.RbtD:
                        if(question.getTrueAnswer().compareTo(question.getAnswerD())==0) {
                            kq = kq+1;
                        }else{
                            wrong = wrong+1;
                        }
                        resetColor();
                        break;
                }
                if(quesnumber >= 10){

                    Intent intent = new Intent(activity_norques.this,activity_ketqua.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("Socau",quesnumber);
                    intent.putExtra("correct",kq);
                    intent.putExtra("wrong",wrong);
                    startActivity(intent);
                }else{
                    quesnumber++;
                    normalquestion();
                }
            }
        });


    }

    private void normalquestion() {
        total = number.get(a);
        a++;
        databaseReference = FirebaseDatabase.getInstance().getReference().child("question_database").child("question_normal").child(String.valueOf(total));
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                question = snapshot.getValue(Question.class);
                Picasso.get().load(question.url).into(img);
                Animation ani = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.animation);
                img.startAnimation(ani);
                A.setText(question.getAnswerA());
                B.setText(question.getAnswerB());
                C.setText(question.getAnswerC());
                D.setText(question.getAnswerD());
                rg.clearCheck();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG,error.getMessage());
            }
        });
    }


    public void resetColor(){
        A.setBackgroundColor(getResources().getColor(R.color.blue));
        B.setBackgroundColor(getResources().getColor(R.color.blue));
        C.setBackgroundColor(getResources().getColor(R.color.blue));
        D.setBackgroundColor(getResources().getColor(R.color.blue));
    }

    public void EventClickA(View view) {
        if(question.getTrueAnswer().equals(question.getAnswerA())){
            A.setBackgroundColor(getResources().getColor(R.color.green));
            mediaPlayer = MediaPlayer.create(getApplicationContext(),R.raw.correctwin);
            mediaPlayer.start();
        }else {
            A.setBackgroundColor(getResources().getColor(R.color.red));
            mediaPlayer = MediaPlayer.create(getApplicationContext(),R.raw.errortone);
            mediaPlayer.start();
        }
    }

    public void EventClickB(View view) {
        if(question.getTrueAnswer().equals(question.getAnswerB())){
            B.setBackgroundColor(getResources().getColor(R.color.green));
            mediaPlayer = MediaPlayer.create(getApplicationContext(),R.raw.correctwin);
            mediaPlayer.start();
        }else {
            B.setBackgroundColor(getResources().getColor(R.color.red));
            mediaPlayer = MediaPlayer.create(getApplicationContext(),R.raw.errortone);
            mediaPlayer.start();
        }
    }

    public void EventClickC(View view) {
        if(question.getTrueAnswer().equals(question.getAnswerC())){
            C.setBackgroundColor(getResources().getColor(R.color.green));
            mediaPlayer = MediaPlayer.create(getApplicationContext(),R.raw.correctwin);
            mediaPlayer.start();
        }else {
            C.setBackgroundColor(getResources().getColor(R.color.red));
            mediaPlayer = MediaPlayer.create(getApplicationContext(),R.raw.errortone);
            mediaPlayer.start();
        }
    }

    public void EventClickD(View view) {
        if(question.getTrueAnswer().equals(question.getAnswerD())){
            D.setBackgroundColor(getResources().getColor(R.color.green));
            mediaPlayer = MediaPlayer.create(getApplicationContext(),R.raw.correctwin);
            mediaPlayer.start();
        }else {
            D.setBackgroundColor(getResources().getColor(R.color.red));
            mediaPlayer = MediaPlayer.create(getApplicationContext(),R.raw.errortone);
            mediaPlayer.start();
        }
    }
}
