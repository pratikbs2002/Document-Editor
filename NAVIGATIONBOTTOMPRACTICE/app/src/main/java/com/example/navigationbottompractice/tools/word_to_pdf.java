package com.example.navigationbottompractice.tools;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.widget.Toolbar;

import com.example.navigationbottompractice.R;

import java.util.Objects;

public class word_to_pdf extends AppCompatActivity {
    Button btnUploadWord2PDF;
    int UploadPdf = 1;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_to_pdf);
        btnUploadWord2PDF = findViewById(R.id.btnUploadWord2PDF);
        toolbar = findViewById(R.id.main_tool_bar1);
        setTitle("WORD TO PDF");
        setSupportActionBar(toolbar);

        btnUploadWord2PDF.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setType("application/msword");
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false);
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent,"Select Word File"), UploadPdf);
        });

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}