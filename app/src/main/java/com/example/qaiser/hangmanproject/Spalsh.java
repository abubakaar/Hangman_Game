package com.example.qaiser.hangmanproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import java.util.Timer;
import java.util.TimerTask;

public class Spalsh extends AppCompatActivity {
    int a;
//    LinearLayout down;
//    Animation uptodown ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.spalsh);
//        down=(LinearLayout)findViewById(R.id.down);
//        uptodown= AnimationUtils.loadAnimation(this, R.anim.uptodown);
//        down.setAnimation(uptodown);
        doSomethingRepeatedly();
    }
    private void doSomethingRepeatedly() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {

                try {

                    a = a + 1;
                    if (a == 2) {
                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(i);
                        finish();
                    }


                } catch (Exception e) {
                    // TODO: handle exception
                }

            }
        }, 0, 5000);
    }
}
