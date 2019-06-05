package com.example.comradeappsoftware.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.comradeappsoftware.R;
import com.example.comradeappsoftware.activities.LoginMainActivity;

public class SplachActivity extends AppCompatActivity {

    private ImageView logo,deco;
    Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splach);

        //DECLARACION DE LAS IMAGENES
        logo = (ImageView) findViewById(R.id.img_logo);

        animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.push_down);
        logo.setAnimation(animation);



        Thread thread = new Thread(){
            @Override
            public void run() {
                try {
                    sleep(4000);
                    Intent intent = new Intent(getApplicationContext(), LoginMainActivity.class);
                    startActivity(intent);
                    finish();
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                super.run();
            }
        };
        thread.start();
    }
}
