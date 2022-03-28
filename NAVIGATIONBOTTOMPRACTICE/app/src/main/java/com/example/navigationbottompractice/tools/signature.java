package com.example.navigationbottompractice.tools;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.navigationbottompractice.R;
import androidx.appcompat.widget.Toolbar;

import java.util.Objects;

public class signature extends AppCompatActivity {
Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signature);
        toolbar = findViewById(R.id.main_tool_bar1);
        setTitle("SIGNATURE");
        setSupportActionBar(toolbar);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);

    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}