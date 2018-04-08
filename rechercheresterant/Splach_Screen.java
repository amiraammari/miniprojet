package com.example.amira.rechercheresterant;


import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class Splach_Screen extends AppCompatActivity {
    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splach__screen);
        img=(ImageView) findViewById(R.id.imageView3);
        ((AnimationDrawable) img.getBackground()).start();
        Thread thread=new Thread(){
            @Override
            public void run() {
                try {
                    sleep(8000);
                    Intent intent = new Intent(getApplicationContext(), ListeRes.class);
                    startActivity(intent);
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
    }
}
