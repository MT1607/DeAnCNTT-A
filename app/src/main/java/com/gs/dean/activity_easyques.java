package com.gs.dean;

import android.app.Activity;
import android.app.Dialog;
import android.content.ClipData;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
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
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.Tag;
import com.gs.dean.model.Question;
import com.sasank.roundedhorizontalprogress.RoundedHorizontalProgressBar;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ListIterator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class activity_easyques extends Activity {
    ScrollView sv;
    RadioGroup rg;
    RadioButton A;
    RadioButton B;
    RadioButton C;
    RadioButton D;
    TextView exit;
    Button bt;
    ImageView img,back;
    int total = 0;
    int a=0;
    int questionnumber=1;
    int kq = 0,wrong = 0 ;
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
        setContentView(R.layout.activity_easyques);

        rg = (RadioGroup)findViewById(R.id.Rdgroup);
        bt = (Button)findViewById(R.id.Btntraloi);
        img = (ImageView)findViewById(R.id.ImgQuestion);
        A = (RadioButton)findViewById(R.id.RbtA);
        B = (RadioButton)findViewById(R.id.RbtB);
        C = (RadioButton)findViewById(R.id.RbtC);
        D = (RadioButton)findViewById(R.id.RbtD);
        exit = findViewById(R.id.ic_exit);
        back = findViewById(R.id.ic_back);

        roundedHorizontalProgressBar = findViewById(R.id.progress_bar_1);
        CountDownTimer countDownTimer = new CountDownTimer(90000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeValue = timeValue - 1;
                roundedHorizontalProgressBar.setProgress(timeValue);
            }

            @Override
            public void onFinish() {
                Dialog dialog = new Dialog(activity_easyques.this,R.style.Dialog);
                dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
                dialog.setContentView(R.layout.time_out_dialog);
                dialog.show();
            }
        }.start();

        for (int i = 1; i<25;i++){
            number.add(i);
        }
        Collections.shuffle(number);

        easyquestion();

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
                        }else {
                            wrong = wrong+1;
                        }
                        resetColor();
                        break;
                    case R.id.RbtC:
                        if(question.getTrueAnswer().compareTo(question.getAnswerC())==0) {

                            kq = kq+1;
                        }else {
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

                if(questionnumber >= 10){
                    Intent intent = new Intent(activity_easyques.this,activity_ketqua.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("Socau",questionnumber);
                    intent.putExtra("correct",kq);
                    intent.putExtra("wrong",wrong);
                    startActivity(intent);
                } else{
                    questionnumber++;
                    easyquestion();
                }

            }

        });

    }

    void easyquestion() {
       // total++;

        total = number.get(a);
        a++;
        databaseReference = FirebaseDatabase.getInstance().getReference().child("question_database").child("question_easy").child(String.valueOf(total));
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                question = snapshot.getValue(Question.class);
                Picasso.get().load(question.url).into(img);
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
