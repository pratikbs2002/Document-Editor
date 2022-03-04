package com.example.navigationbottompractice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class spash_screen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spash_screen);

        Thread thread = new Thread(){
            public void run(){
                try {
                    sleep(3000);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
                finally {
                    Intent intent = new Intent(spash_screen.this,MainActivity.class);
                    startActivity(intent);
                }
            }
        };thread.start();
    }
}