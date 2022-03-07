package com.example.navigationbottompractice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;

import com.example.navigationbottompractice.fragment.filesfragment;
import com.example.navigationbottompractice.fragment.homefragment;
import com.example.navigationbottompractice.fragment.camerafragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;

    Toolbar toolbar;
    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container,new filesfragment()).commit();

        toolbar = findViewById(R.id.main_tool_bar);
        setSupportActionBar(toolbar);

        bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            FragmentTransaction transaction1 = getSupportFragmentManager().beginTransaction();
            switch (item.getItemId()){
                case R.id.home:
                    transaction1.replace(R.id.container,new homefragment());
                    break;

                case R.id.files:
                    transaction1.replace(R.id.container,new filesfragment());
                    break;

                case R.id.camera:
                    transaction1.replace(R.id.container,new camerafragment());
                    break;
            }
            transaction1.commit();
            return true;
        });
    }


//method for toolbar(task bar)......................
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu_button,menu);
        return true;
    }
//method for toolbar(task bar)......................

}