package com.example.paynsplit;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity  {

    private static int splash_delay = 4000;
    Animation frombottom,fromtop,fadeout;
    ImageView applogo;
    TextView appname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fadeout= AnimationUtils.loadAnimation(this,R.anim.fadeout);
        applogo=findViewById(R.id.applogo);
        fromtop=AnimationUtils.loadAnimation(this,R.anim.fromtop);

        applogo.setAnimation(fromtop);
       frombottom = AnimationUtils.loadAnimation(this,R.anim.frombottom);

        appname=findViewById(R.id.appname);

       appname.setAnimation(frombottom);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent welcome = new Intent(MainActivity.this,welcome.class);
                startActivity(welcome);
                finish();
            }
        }, splash_delay);
    }

}


