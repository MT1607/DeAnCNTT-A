package com.gs.dean;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
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
import com.squareup.picasso.Picasso;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collections;

public class activity_norques extends Activity {
    ScrollView sv;
    RadioGroup rg;
    RadioButton A,B,C,D;
    TextView socau,ketqua,thoigian;
    Button bt;
    int total = 0;
    int quesnumber = 1;
    int a = 0;
    int kq=0;
    ImageView img;
    Question question;
    ArrayList<Integer> number = new ArrayList<>();
    DatabaseReference databaseReference;
    String TAG="MainActivity";
    AudioManager audioManager;
    MediaPlayer mediaPlayer;

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
        ketqua = (TextView)findViewById(R.id.txtKetqua);
        socau = (TextView)findViewById(R.id.TxtCau);
        thoigian = (TextView)findViewById(R.id.time);
        img = (ImageView)findViewById(R.id.ImgQuestion);

        reverserTimer(90,thoigian);
        mediaPlayer = MediaPlayer.create(getApplicationContext(),R.raw.kahoot_music_3);
        mediaPlayer.start();
        mediaPlayer.setLooping(true);

        for (int i = 1; i < 6;i++){
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
                        }
                        break;
                    case R.id.RbtB:
                        if(question.getTrueAnswer().compareTo(question.getAnswerB())==0) {

                            kq = kq+1;
                        }
                        break;
                    case R.id.RbtC:
                        if(question.getTrueAnswer().compareTo(question.getAnswerC())==0) {

                            kq = kq+1;
                        }
                        break;
                    case R.id.RbtD:
                        if(question.getTrueAnswer().compareTo(question.getAnswerD())==0) {
                            kq = kq+1;
                        }
                        break;
                }
                if(quesnumber >= 5){
                    mediaPlayer.stop();
                    Intent intent = new Intent(activity_norques.this,activity_ketqua.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("KQ", kq);
                    bundle.putInt("Socau", quesnumber);
                    intent.putExtra("MyPackage",bundle);
                    startActivity(intent);
                }else{
                    quesnumber++;
                    normalquestion();
                    socau.setText(""+quesnumber);
                    ketqua.setText(""+kq);
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
    public void reverserTimer(int giay,TextView thoigian) {
        new CountDownTimer(giay * 1000 + 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int giay = (int) (millisUntilFinished / 1000);
                int phut = giay / 60;
                giay = giay % 60;
                thoigian.setText(phut + ":" + giay);

            }

            @Override
            public void onFinish() {
                thoigian.setText("Het Gio!");
                Intent intent = new Intent(activity_norques.this, activity_ketqua.class);
                Bundle bundle = new Bundle();
                bundle.putInt("KQ", kq);
                bundle.putInt("Socau", quesnumber);
                intent.putExtra("MyPackage", bundle);
                startActivity(intent);
            }
        }.start();
    }
}
