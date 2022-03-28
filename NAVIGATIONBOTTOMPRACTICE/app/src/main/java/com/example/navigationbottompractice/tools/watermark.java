package com.example.navigationbottompractice.tools;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.Objects;
import androidx.appcompat.widget.Toolbar;

import com.example.navigationbottompractice.R;

public class watermark extends AppCompatActivity {
Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watermark);

        toolbar = findViewById(R.id.main_tool_bar1);
        setTitle("WATERMARK");
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